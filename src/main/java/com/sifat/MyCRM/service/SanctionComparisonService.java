package com.sifat.MyCRM.service;

import com.sifat.MyCRM.config.FuzzyMatcher;
import com.sifat.MyCRM.config.SanctionsScoringEngine;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualPermanentAddressInDTO;
import com.sifat.MyCRM.dto.input.CustomerBasicDetailDTO;
import com.sifat.MyCRM.dto.input.IndividualCustomerCompareInDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.entity.SanctionIndividual;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import com.sifat.MyCRM.utility.SanctionMatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SanctionComparisonService extends BaseService {

    private final FuzzyMatcher fuzzyMatcher;
    private final SanctionsScoringEngine sanctionsScoringEngine;
    private final SanctionIndividualRepository sanctionIndividualRepository;


    public List<IndividualCustomerCompareResultOutDTO> compareIndividualData(IndividualCustomerCompareInDTO inDTO) throws Exception {
        try {
            CustomerBasicDetailDTO basicDetails = inDTO.getBasic_details();
            CustomerAMLIndividualPermanentAddressInDTO permanentAddress = inDTO.getPermanent_address();

            List<IndividualCustomerCompareResultOutDTO> matchingResult = getCustomerSanctionMatchingResult(basicDetails, permanentAddress);
            return matchingResult;


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public List<IndividualCustomerCompareResultOutDTO> getCustomerSanctionMatchingResult(CustomerBasicDetailDTO customerDetailDTO, CustomerAMLIndividualPermanentAddressInDTO customerAddressDTO) throws Exception {
        try {
            List<SanctionIndividual> sanctionedIndividuals = sanctionIndividualRepository.findAll();

            List<IndividualCustomerCompareResultOutDTO> results = new ArrayList<>();

            for (SanctionIndividual sanctionIndividual : sanctionedIndividuals) {

                double nameScore = fuzzyMatcher.getJaroWinklerSimilarity(customerDetailDTO.getFull_name(), getIndividualFullName(sanctionIndividual));
                double dobScore = fuzzyMatcher.getIndividualDateSimilarity(customerDetailDTO.getDate_of_birth(), sanctionIndividual.getIndividualDateOfBirth());
                double nationalityScore = fuzzyMatcher.getIndividualNationalitySimilarity(customerDetailDTO.getNationality(), sanctionIndividual.getNationalities());
                double docScore = fuzzyMatcher.getIndividualDocumentSimilarity(customerDetailDTO.getNid_no(),customerDetailDTO.getPassport_no(), sanctionIndividual.getIndividual_document());

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
                                overallScoreString+"%",
                                determineStatus(overallScore)
                        )
                );
            }

            return results.stream()
                    .sorted(
                            Comparator.comparing(
                                    IndividualCustomerCompareResultOutDTO::getOverall_score
                            ).reversed()
                    )
                    .toList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String determineStatus(double overallScore) {

//        if (overallScore >= 0.90 && nameScore >= 0.85) {
//            return SanctionMatchStatus.HIGH_POTENTIAL_MATCHED.name();
//        }
//
//        if (overallScore >= 0.75 && nameScore >= 0.70) {
//            return SanctionMatchStatus.POTENTIAL_MATCHED.name();
//        }

        if (overallScore >= 0.85) {
            return SanctionMatchStatus.HIGH_POTENTIAL_MATCHED.name();
        }

        if (overallScore <= 0.85 && overallScore >= 0.70) {
            return SanctionMatchStatus.POTENTIAL_MATCHED.name();
        }

        return SanctionMatchStatus.CLEAR.name();
    }

    private String getIndividualFullName(SanctionIndividual individual) {
        return individual.getFirstName() + " " + individual.getSecondName() + " "
                + individual.getThirdName() + " " + individual.getFourthName();
    }

}

