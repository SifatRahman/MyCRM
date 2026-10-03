package com.sifat.MyCRM.config;

import com.sifat.MyCRM.dto.input.CustomerAMLEntityPermanentAddressInDTO;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualPermanentAddressInDTO;
import com.sifat.MyCRM.entity.*;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class FuzzyMatcher {
    private final AMLSanctionConfigurationService configurationService;

    private final JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

    private final TextNormalizer normalizer;

    public FuzzyMatcher(AMLSanctionConfigurationService configurationService, TextNormalizer normalizer) {
        this.configurationService = configurationService;
        this.normalizer = normalizer;
    }

    public double getJaroWinklerSimilarity(String customerName,
                                           String sanctionsName) {

        String a = normalizer.normalize(stringToEmptyStringIfInvalid(customerName));
        String b = normalizer.normalize(stringToEmptyStringIfInvalid(sanctionsName));

        if (a.isEmpty() || b.isEmpty()) {
            return 0.00;
        }

        return jaroWinkler.apply(a, b);
    }

    public double getIndividualNationalitySimilarity(
            String customerNationality,
            List<SanctionIndividualNationality> sanctionNationalities) {

        if (customerNationality == null || sanctionNationalities == null || sanctionNationalities.isEmpty()) {
            return 0.00;
        }
        double maxSimilarity = 0.00;

        for (SanctionIndividualNationality e : sanctionNationalities) {
            double currentSimilarity = customerNationality.equalsIgnoreCase(stringToEmptyStringIfInvalid(e.getNationality())) ? 1.00 : 0.00;
            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }

        return maxSimilarity;
    }

    //has work
    public double getIndividualDocumentSimilarity(
            String nidNo,
            String passNo,
            List<SanctionIndividualDocument> sanctionDocuments) {

        if ((nidNo == null && passNo==null) || sanctionDocuments == null || sanctionDocuments.isEmpty()) {
            return 0.00;
        }
        double maxSimilarity = 0.00;
        nidNo=stringToEmptyStringIfInvalid(nidNo).trim();
        passNo=stringToEmptyStringIfInvalid(passNo).trim();
        for (SanctionIndividualDocument e : sanctionDocuments) {
            double currentSimilarity = 0.00;
            if(!nidNo.isBlank()){
               currentSimilarity = nidNo.equalsIgnoreCase(stringToEmptyStringIfInvalid(e.getNumber())) ? 1.00 : 0.00;
           }
            if(!passNo.isBlank()){
                currentSimilarity = passNo.equalsIgnoreCase(stringToEmptyStringIfInvalid(e.getNumber())) ? 1.00 : 0.00;
            }

            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }

        return maxSimilarity;
    }

    public double getIndividualAddressSimilarity(CustomerAMLIndividualPermanentAddressInDTO customerAddress,
                                       List<SanctionAddress> sanctionAddresses) {

        if (customerAddress == null || sanctionAddresses == null || sanctionAddresses.isEmpty()) {
            return 0.00;
        }

        StringBuilder customerAddressBuild = new StringBuilder();
        customerAddressBuild
                .append(stringToEmptyStringIfInvalid(customerAddress.getRoad_or_block()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getVillage_or_area()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getPost_code()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getPolice_station()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getUpazila()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getDistrict()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getDivision_or_state()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getCountry()));

        double maxSimilarity = 0.00;
        for (SanctionAddress e : sanctionAddresses) {
            String sanctionAddressBuilder = stringToEmptyStringIfInvalid(e.getStreet()) +
                    stringToEmptyStringIfInvalid(e.getZip_code()) +
                    stringToEmptyStringIfInvalid(e.getState_province()) +
                    stringToEmptyStringIfInvalid(e.getCity()) +
                    stringToEmptyStringIfInvalid(e.getCountry());

            double currentSimilarity = getJaroWinklerSimilarity(customerAddressBuild.toString(), sanctionAddressBuilder);
            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }
        return maxSimilarity;
    }

    public double getEntityAddressSimilarity(CustomerAMLEntityPermanentAddressInDTO customerAddress,
                                             List<SanctionAddress> sanctionAddresses) {

        if (customerAddress == null || sanctionAddresses == null || sanctionAddresses.isEmpty()) {
            return 0.00;
        }

        StringBuilder customerAddressBuild = new StringBuilder();
        customerAddressBuild
                .append(stringToEmptyStringIfInvalid(customerAddress.getRoad_or_block()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getVillage_or_area()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getPost_code()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getPolice_station()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getUpazila()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getDistrict()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getDivision_or_state()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getCountry()));

        double maxSimilarity = 0.00;
        for (SanctionAddress e : sanctionAddresses) {
            String sanctionAddressBuilder = stringToEmptyStringIfInvalid(e.getStreet()) +
                    stringToEmptyStringIfInvalid(e.getZip_code()) +
                    stringToEmptyStringIfInvalid(e.getState_province()) +
                    stringToEmptyStringIfInvalid(e.getCity()) +
                    stringToEmptyStringIfInvalid(e.getCountry());

            double currentSimilarity = getJaroWinklerSimilarity(customerAddressBuild.toString(), sanctionAddressBuilder);
            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }
        return maxSimilarity;
    }

    public double getPlaceOfBirthSimilarity(
            CustomerAMLIndividualPermanentAddressInDTO customerAddress,
            List<SanctionIndividualPlaceOfBirth> sanctionPOBs) {

        if (customerAddress == null || sanctionPOBs == null || sanctionPOBs.isEmpty()) {
            return 0.00;
        }

        StringBuilder customerAddressBuild = new StringBuilder();
        customerAddressBuild.append(stringToEmptyStringIfInvalid(customerAddress.getDistrict()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getDivision_or_state()))
                .append(stringToEmptyStringIfInvalid(customerAddress.getCountry()));

        double maxSimilarity = 0.00;
        for (SanctionIndividualPlaceOfBirth e : sanctionPOBs) {
            String sanctionPOBBuilder = stringToEmptyStringIfInvalid(e.getState_province()) +
                    stringToEmptyStringIfInvalid(e.getCity()) +
                    stringToEmptyStringIfInvalid(e.getCountry()) +
                    stringToEmptyStringIfInvalid(customerAddress.getDivision_or_state());

            double currentSimilarity = getJaroWinklerSimilarity(customerAddressBuild.toString(), sanctionPOBBuilder);
            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }
        return maxSimilarity;
    }

    public double getIndividualDateSimilarity(
            LocalDate customerDob,
            List<SanctionIndividualDateOfBirth> sanctionDobs) throws Exception {
        try {

            if (customerDob == null || sanctionDobs == null || sanctionDobs.isEmpty()) {
                return 0.00;
            }
            double maxSimilarity = 0.00;
            for (SanctionIndividualDateOfBirth e : sanctionDobs) {
                e=stringToEmptyStringIfDOBNull(e);

                double currentSimilarity = 0.00;
                switch (e.getType_of_date()) {

                    case "EXACT":

                        if (!e.getDate().isBlank() && LocalDate.parse(e.getDate()).equals(customerDob)) {
                            currentSimilarity = 1.00;
                        } else if (!e.getYear().isBlank() && Integer.parseInt(e.getYear()) == customerDob.getYear()) {
                            currentSimilarity = configurationService.getConfiguration().getOnlyExactYearMatchDobScore();
                        }
                        break;

                    case "BETWEEN":

                        int fromYear = Integer.parseInt(e.getFrom_year());
                        int toYear = Integer.parseInt(e.getTo_year());

                        if (fromYear <= customerDob.getYear()
                                && customerDob.getYear() <= toYear) {
                            currentSimilarity = configurationService.getConfiguration().getYearInBetweenGivenTwoYearMatchDobScore();
                        }
                        break;


                    case "APPROXIMATELY":
                        int approximateYear = 0;
                        if(!e.getYear().isBlank()){
                            approximateYear = Integer.parseInt(e.getYear());
                        }
                        if (approximateYear == customerDob.getYear()) {
                            currentSimilarity = configurationService.getConfiguration().getYearInBetweenGivenTwoYearMatchDobScore();
                        }
                        break;

                    default:
                        currentSimilarity = 0.00;
                }
                maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
            }

            return maxSimilarity;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public static String stringToEmptyStringIfInvalid(String value) {
        return value == null || value.isBlank() ? "" : value;
    }

    public static SanctionIndividualDateOfBirth stringToEmptyStringIfDOBNull(SanctionIndividualDateOfBirth dob) {
        dob.setType_of_date(stringToEmptyStringIfInvalid(dob.getType_of_date()));
        dob.setDate(stringToEmptyStringIfInvalid(dob.getDate()));
        dob.setFrom_year(stringToEmptyStringIfInvalid(dob.getFrom_year()));
        dob.setTo_year(stringToEmptyStringIfInvalid(dob.getTo_year()));
        dob.setYear(stringToEmptyStringIfInvalid(dob.getYear()));
        dob.setNote(stringToEmptyStringIfInvalid(dob.getNote()));
        return dob;
    }

}
