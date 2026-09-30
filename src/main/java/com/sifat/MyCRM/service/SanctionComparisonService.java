package com.sifat.MyCRM.service;

import com.sifat.MyCRM.config.AMLSanctionConfigurationService;
import com.sifat.MyCRM.config.FuzzyMatcher;
import com.sifat.MyCRM.config.SanctionsScoringEngine;
import com.sifat.MyCRM.dto.input.CreateSanctionConfigDataDTO;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualPermanentAddressInDTO;
import com.sifat.MyCRM.dto.input.CustomerBasicDetailDTO;
import com.sifat.MyCRM.dto.input.IndividualCustomerCompareInDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.entity.AMLSanctionConfiguration;
import com.sifat.MyCRM.entity.SanctionIndividual;
import com.sifat.MyCRM.exception.ResourceNotFoundException;
import com.sifat.MyCRM.repository.AMLSanctionConfigurationRepository;
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

                double addressScore = fuzzyMatcher.getAddressSimilarity(customerAddressDTO, sanctionIndividual.getIndividualAddress());
                double pobScore = fuzzyMatcher.getPlaceOfBirthSimilarity(customerAddressDTO, sanctionIndividual.getIndividual_place_of_birth());

                double overallScore = sanctionsScoringEngine.calculate(
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
                                determineStatus(overallScore)
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

    private String determineStatus(double overallScore) {
        AMLSanctionConfiguration config = configurationService.getConfiguration();

        if (overallScore >= config.getHighRiskStartScore()) {
            return SanctionMatchStatus.HIGH_POTENTIAL_MATCHED.name();
        }

        if (overallScore >= config.getPotentialMatchStartScore()) {
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
        asc.setAfterScoreNameWeight(inDTO.getAfter_score_name_weight());
        asc.setAfterScoreDobWeight(inDTO.getAfter_score_dob_weight());
        asc.setAfterScoreDocWeight(inDTO.getAfter_score_doc_weight());
        asc.setAfterScoreNationalityWeight(inDTO.getAfter_score_nationality_weight());
        asc.setAfterScoreAddressWeight(inDTO.getAfter_score_address_weight());
        asc.setAfterScorePobWeight(inDTO.getAfter_score_pob_weight());
        asc.setHighRiskStartScore(inDTO.getHigh_risk_start_score());
        asc.setPotentialMatchStartScore(inDTO.getPotential_match_start_score());
        asc.setSanctionClearTillScore(inDTO.getSanction_clear_till_score());

        amlSanctionConfigurationRepository.save(asc);
        configurationService.reload();

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}

