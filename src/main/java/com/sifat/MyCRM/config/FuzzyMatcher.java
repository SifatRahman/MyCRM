package com.sifat.MyCRM.config;

import com.sifat.MyCRM.dto.input.CustomerAMLIndividualPermanentAddressInDTO;
import com.sifat.MyCRM.entity.*;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class FuzzyMatcher {

    private final JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

    private final TextNormalizer normalizer;

    public FuzzyMatcher(TextNormalizer normalizer) {
        this.normalizer = normalizer;
    }

    public double getJaroWinklerSimilarity(String customerName,
                                           String sanctionsName) {

        String a = normalizer.normalize(customerName);
        String b = normalizer.normalize(sanctionsName);

        if (a.isEmpty() || b.isEmpty()) {
            return 0.00;
        }

        return jaroWinkler.apply(a, b);
    }

    public double addressSimilarity(String customerAddress,
                                    String sanctionsAddress) {

        String a = normalizer.normalize(customerAddress);
        String b = normalizer.normalize(sanctionsAddress);

        if (a.isEmpty() || b.isEmpty()) {
            return 0.00;
        }

        return jaroWinkler.apply(a, b);
    }

    public double placeOfBirthSimilarity(String customerPob,
                                         String sanctionsPob) {

        String a = normalizer.normalize(customerPob);
        String b = normalizer.normalize(sanctionsPob);

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
            double currentSimilarity = customerNationality.equalsIgnoreCase(e.getNationality()) ? 1.00 : 0.00;
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

        for (SanctionIndividualDocument e : sanctionDocuments) {
            double currentSimilarity = 0.00;
            assert nidNo != null;
            if(!nidNo.isBlank()){
               currentSimilarity = nidNo.equalsIgnoreCase(e.getNumber()) ? 1.00 : 0.00;
           }
            if(!passNo.isBlank()){
                currentSimilarity = passNo.equalsIgnoreCase(e.getNumber()) ? 1.00 : 0.00;
            }

            maxSimilarity = Math.max(maxSimilarity, currentSimilarity);
        }

        return maxSimilarity;
    }

    public double getAddressSimilarity(CustomerAMLIndividualPermanentAddressInDTO customerAddress,
                                       List<SanctionAddress> sanctionAddresses) {

        if (customerAddress == null || sanctionAddresses == null || sanctionAddresses.isEmpty()) {
            return 0.00;
        }

        StringBuilder customerAddressBuild = new StringBuilder();
        customerAddressBuild
                .append(customerAddress.getRoad_or_block())
                .append(customerAddress.getVillage_or_area())
                .append(customerAddress.getPost_code())
                .append(customerAddress.getPolice_station())
                .append(customerAddress.getUpazila())
                .append(customerAddress.getDistrict())
                .append(customerAddress.getDivision_or_state())
                .append(customerAddress.getCountry());

        double maxSimilarity = 0.00;
        for (SanctionAddress e : sanctionAddresses) {
            String sanctionAddressBuilder = e.getStreet() +
                    e.getZip_code() +
                    e.getState_province() +
                    e.getCity() +
                    e.getCountry();

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
        customerAddressBuild.append(customerAddress.getDistrict())
                .append(customerAddress.getDivision_or_state())
                .append(customerAddress.getCountry());

        double maxSimilarity = 0.00;
        for (SanctionIndividualPlaceOfBirth e : sanctionPOBs) {
            String sanctionPOBBuilder = e.getState_province() +
                    e.getCity() +
                    e.getCountry() +
                    customerAddress.getDivision_or_state();

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

                double currentSimilarity = 0.00;
                switch (e.getType_of_date()) {

                    case "EXACT":

                        if (!e.getDate().isBlank() && LocalDate.parse(e.getDate()).equals(customerDob)) {
                            currentSimilarity = 1.00;
                        } else if (!e.getYear().isBlank() && Integer.parseInt(e.getYear()) == customerDob.getYear()) {
                            currentSimilarity = 0.70;
                        }
                        break;

                    case "BETWEEN":

                        int fromYear = Integer.parseInt(e.getFrom_year());
                        int toYear = Integer.parseInt(e.getTo_year());

                        if (fromYear <= customerDob.getYear()
                                && customerDob.getYear() <= toYear) {
                            currentSimilarity = 0.70;
                        }
                        break;


                    case "APPROXIMATELY":
                        int approximateYear = 0;
                        if(!e.getYear().isBlank()){
                            approximateYear = Integer.parseInt(e.getYear());
                        }
                        if (approximateYear == customerDob.getYear()) {
                            currentSimilarity = 0.20;
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

}
