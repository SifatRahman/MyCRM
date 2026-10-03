package com.sifat.MyCRM.service;

import com.sifat.MyCRM.config.AMLSanctionConfigurationService;
import com.sifat.MyCRM.config.FuzzyMatcher;
import com.sifat.MyCRM.config.SanctionsScoringEngine;
import com.sifat.MyCRM.dto.input.*;
import com.sifat.MyCRM.dto.output.EntityCustomerCompareResultOutDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.entity.AMLSanctionConfiguration;
import com.sifat.MyCRM.entity.SanctionEntity;
import com.sifat.MyCRM.entity.SanctionIndividual;
import com.sifat.MyCRM.exception.ResourceNotFoundException;
import com.sifat.MyCRM.repository.AMLSanctionConfigurationRepository;
import com.sifat.MyCRM.repository.SanctionEntityRepository;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import com.sifat.MyCRM.utility.SanctionMatchStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanctionComparisonService extends BaseService {

    private final FuzzyMatcher fuzzyMatcher;
    private final SanctionsScoringEngine sanctionsScoringEngine;
    private final AMLSanctionConfigurationService configurationService;
    private final AMLSanctionConfigurationRepository amlSanctionConfigurationRepository;
    private final SanctionIndividualRepository sanctionIndividualRepository;
    private final SanctionEntityRepository sanctionEntityRepository;


    public List<IndividualCustomerCompareResultOutDTO> compareIndividualData(IndividualCustomerCompareInDTO inDTO) throws Exception {
        try {
            CustomerBasicDetailDTO customerDetailDTO = inDTO.getBasic_details();
            CustomerAMLIndividualPermanentAddressInDTO customerAddressDTO = inDTO.getPermanent_address();

            List<SanctionIndividual> sanctionedIndividuals = sanctionIndividualRepository.findAll();
            if(sanctionedIndividuals.isEmpty()){
                throw new ResourceNotFoundException("No sanctioned data found!");
            }

            List<IndividualCustomerCompareResultOutDTO> results = new ArrayList<>();

            for (SanctionIndividual sanctionIndividual : sanctionedIndividuals) {

                double nameScore = fuzzyMatcher.getJaroWinklerSimilarity(customerDetailDTO.getFull_name(), getIndividualFullName(sanctionIndividual));
                double dobScore = fuzzyMatcher.getIndividualDateSimilarity(customerDetailDTO.getDate_of_birth(), sanctionIndividual.getIndividualDateOfBirth());
                double nationalityScore = fuzzyMatcher.getIndividualNationalitySimilarity(customerDetailDTO.getNationality(), sanctionIndividual.getNationalities());
                double docScore = fuzzyMatcher.getIndividualDocumentSimilarity(customerDetailDTO.getNid_no(), customerDetailDTO.getPassport_no(), sanctionIndividual.getIndividual_document());

                double addressScore = fuzzyMatcher.getIndividualAddressSimilarity(customerAddressDTO, sanctionIndividual.getIndividualAddress());
                double pobScore = fuzzyMatcher.getPlaceOfBirthSimilarity(customerAddressDTO, sanctionIndividual.getIndividual_place_of_birth());

                double overallScore = sanctionsScoringEngine.calculateIndividualScore(
                        nameScore,
                        dobScore,
                        docScore,
                        nationalityScore,
                        addressScore,
                        pobScore
                );
                String overallScoreString = String.valueOf(BigDecimal.valueOf(overallScore)
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)));

                results.add(new IndividualCustomerCompareResultOutDTO(
                                sanctionIndividual.getId(),
                                getIndividualFullName(sanctionIndividual),
                                BigDecimal.valueOf(nameScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(dobScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(docScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(addressScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(pobScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(nationalityScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(overallScore).setScale(2, RoundingMode.HALF_UP),
                                overallScoreString + "%",
                                determineIndividualStatus(overallScore)
                        )
                );
            }

            List<IndividualCustomerCompareResultOutDTO> reversedList = results.stream()
                    .sorted(
                            Comparator.comparing(
                                    IndividualCustomerCompareResultOutDTO::getOverall_score
                            ).reversed()
                    )
                    .toList();
            return reversedList.stream().limit(5).toList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String determineIndividualStatus(double overallScore) {
        AMLSanctionConfiguration config = configurationService.getConfiguration();

        if (overallScore >= config.getIndividualHighRiskStartScore()) {
            return SanctionMatchStatus.HIGH_POTENTIAL_MATCHED.name();
        }

        if (overallScore >= config.getIndividualPotentialMatchStartScore()) {
            return SanctionMatchStatus.POTENTIAL_MATCHED.name();
        }

        return SanctionMatchStatus.CLEAR.name();
    }

    private String getIndividualFullName(SanctionIndividual individual) {
        return individual.getFirstName() + " " + individual.getSecondName() + " "
                + individual.getThirdName() + " " + individual.getFourthName();
    }

    public void saveSanctionConfigData(@Valid CreateSanctionConfigDataDTO inDTO) {
        try{

        AMLSanctionConfiguration asc = amlSanctionConfigurationRepository.findFirstBy().orElseThrow(
                () -> new ResourceNotFoundException("AML sanction configuration not found!")
        );
        var sumAfter = inDTO.getAfter_score_name_weight()+inDTO.getAfter_score_dob_weight()
                +inDTO.getAfter_score_doc_weight()+inDTO.getAfter_score_nationality_weight()
                +inDTO.getAfter_score_address_weight()+inDTO.getAfter_score_pob_weight();
        if(sumAfter!=1.00){
            throw new ResourceNotFoundException("Sum of after score weight must be 1.00!");
        }

        asc.setOnlyExactYearMatchDobScore(inDTO.getOnly_exact_year_matched_dob_score());
        asc.setYearInBetweenGivenTwoYearMatchDobScore(inDTO.getYear_in_between_given_two_year_matched_dob_score());
        asc.setOnlyYearMatchedWithApproximateYearDobScore(inDTO.getOnly_year_matched_with_approximate_year_dob_score());
        asc.setAfterScoreIndividualNameWeight(inDTO.getAfter_score_name_weight());
        asc.setAfterScoreIndividualDobWeight(inDTO.getAfter_score_dob_weight());
        asc.setAfterScoreIndividualDocWeight(inDTO.getAfter_score_doc_weight());
        asc.setAfterScoreIndividualNationalityWeight(inDTO.getAfter_score_nationality_weight());
        asc.setAfterScoreIndividualAddressWeight(inDTO.getAfter_score_address_weight());
        asc.setAfterScoreIndividualPobWeight(inDTO.getAfter_score_pob_weight());
        asc.setIndividualHighRiskStartScore(inDTO.getHigh_risk_start_score());
        asc.setIndividualPotentialMatchStartScore(inDTO.getPotential_match_start_score());
        asc.setIndividualSanctionClearTillScore(inDTO.getSanction_clear_till_score());

        amlSanctionConfigurationRepository.save(asc);
        configurationService.reload();

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public List<EntityCustomerCompareResultOutDTO> compareEntityData(EntityCustomerCompareInDTO inDTO) throws Exception {
        try {
            EntityCustomerBasicDetailDTO customerDetailDTO = inDTO.getBasic_details();
            CustomerAMLEntityPermanentAddressInDTO customerAddressDTO = inDTO.getPermanent_address();

            List<SanctionEntity> sanctionEntities = sanctionEntityRepository.findAll();
            if(sanctionEntities.isEmpty()){
                throw new ResourceNotFoundException("No sanctioned data found!");
            }
            List<EntityCustomerCompareResultOutDTO> results = new ArrayList<>();

            for (SanctionEntity sanctionEntity : sanctionEntities) {

                double nameScore = fuzzyMatcher.getJaroWinklerSimilarity(customerDetailDTO.getFull_name(), sanctionEntity.getFirstName());
                double addressScore = fuzzyMatcher.getEntityAddressSimilarity(customerAddressDTO, sanctionEntity.getEntityAddress());
                double overallScore = sanctionsScoringEngine.calculateEntityScore(
                        nameScore,
                        addressScore
                );
                String overallScoreString = String.valueOf(BigDecimal.valueOf(overallScore)
                        .setScale(2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)));

                results.add(new EntityCustomerCompareResultOutDTO(
                        sanctionEntity.getId(),
                                sanctionEntity.getFirstName(),
                                BigDecimal.valueOf(nameScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(addressScore).setScale(2, RoundingMode.HALF_UP),
                                BigDecimal.valueOf(overallScore).setScale(2, RoundingMode.HALF_UP),
                                overallScoreString + "%",
                        determineEntityStatus(overallScore)
                        )
                );
            }

            List<EntityCustomerCompareResultOutDTO> reversedList = results.stream()
                    .sorted(
                            Comparator.comparing(
                                    EntityCustomerCompareResultOutDTO::getOverall_score
                            ).reversed()
                    )
                    .toList();
            return reversedList.stream().limit(5).toList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String determineEntityStatus(double overallScore) {
        AMLSanctionConfiguration config = configurationService.getConfiguration();

        if (overallScore >= config.getEntityHighRiskStartScore()) {
            return SanctionMatchStatus.HIGH_POTENTIAL_MATCHED.name();
        }

        if (overallScore >= config.getEntityPotentialMatchStartScore()) {
            return SanctionMatchStatus.POTENTIAL_MATCHED.name();
        }

        return SanctionMatchStatus.CLEAR.name();
    }
}

