package com.sifat.MyCRM.service;

import com.sifat.MyCRM.config.FuzzyMatcher;
import com.sifat.MyCRM.config.TextNormalizer;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualPermanentAddressInDTO;
import com.sifat.MyCRM.dto.input.CustomerBasicDetailDTO;
import com.sifat.MyCRM.dto.input.IndividualCustomerCompareInDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.repository.SanctionIndividualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SanctionComparisonService extends BaseService {

    private final FuzzyMatcher fuzzyMatcher;
    private final TextNormalizer textNormalizer;
    private final SanctionIndividualRepository sanctionIndividualRepository;


    public IndividualCustomerCompareResultOutDTO compareIndividualData(IndividualCustomerCompareInDTO inDTO) throws Exception {
        try {
            CustomerBasicDetailDTO basicDetails = inDTO.getBasic_details();
            CustomerAMLIndividualPermanentAddressInDTO permanentAddress = inDTO.getPermanent_address();







        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return null;
    }


        public List<MatchResult> screen(CustomerDTO customer) {

            List<SanctionsPerson> sanctionsPersons =
                    repository.findAll();

            List<MatchResult> results = new ArrayList<>();

            for (SanctionsPerson person : sanctionsPersons) {

                double nameScore =
                        fuzzyMatcher.nameSimilarity(
                                customer.name(),
                                person.getName()
                        );

                double dobScore =
                        fuzzyMatcher.dateSimilarity(
                                customer.dateOfBirth(),
                                person.getDateOfBirth()
                        );

                double addressScore =
                        fuzzyMatcher.addressSimilarity(
                                customer.address(),
                                person.getAddress()
                        );

                double pobScore =
                        fuzzyMatcher.placeOfBirthSimilarity(
                                customer.placeOfBirth(),
                                person.getPlaceOfBirth()
                        );

                double overallScore =
                        scoringEngine.calculate(
                                nameScore,
                                dobScore,
                                addressScore,
                                pobScore
                        );

                ScreeningStatus status =
                        determineStatus(
                                nameScore,
                                dobScore,
                                overallScore
                        );

                results.add(
                        new MatchResult(
                                person.getId(),
                                person.getName(),
                                nameScore,
                                dobScore,
                                addressScore,
                                pobScore,
                                overallScore,
                                status
                        )
                );
            }

            return results.stream()
                    .sorted(
                            Comparator.comparing(
                                    MatchResult::overallScore
                            ).reversed()
                    )
                    .toList();
        }

        private ScreeningStatus determineStatus(
                double nameScore,
                double dobScore,
                double overallScore) {

            if (overallScore >= 0.90 &&
                    nameScore >= 0.85) {

                return ScreeningStatus.REVIEW_REQUIRED;
            }

            if (overallScore >= 0.75 &&
                    nameScore >= 0.70) {

                return ScreeningStatus.POTENTIAL_MATCH;
            }

            return ScreeningStatus.CLEAR;
        }
    }
}
