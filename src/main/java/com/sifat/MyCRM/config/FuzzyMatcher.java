package com.sifat.MyCRM.config;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class FuzzyMatcher {

    private final JaroWinklerSimilarity jaroWinkler = new JaroWinklerSimilarity();

    private final TextNormalizer normalizer;

    public FuzzyMatcher(TextNormalizer normalizer) {
        this.normalizer = normalizer;
    }

    public double nameSimilarity(String customerName,
                                 String sanctionsName) {

        String a = normalizer.normalize(customerName);
        String b = normalizer.normalize(sanctionsName);

        if (a.isEmpty() || b.isEmpty()) {
            return 0.0;
        }

        return jaroWinkler.apply(a, b);
    }

    public double addressSimilarity(String customerAddress,
                                    String sanctionsAddress) {

        String a = normalizer.normalize(customerAddress);
        String b = normalizer.normalize(sanctionsAddress);

        if (a.isEmpty() || b.isEmpty()) {
            return 0.0;
        }

        return jaroWinkler.apply(a, b);
    }

    public double placeOfBirthSimilarity(String customerPob,
                                         String sanctionsPob) {

        String a = normalizer.normalize(customerPob);
        String b = normalizer.normalize(sanctionsPob);

        if (a.isEmpty() || b.isEmpty()) {
            return 0.0;
        }

        return jaroWinkler.apply(a, b);
    }

    public double dateSimilarity(LocalDate customerDob,
                                 LocalDate sanctionsDob) {
        if (customerDob == null || sanctionsDob == null) {
            return 0.0;
        }

        return customerDob.equals(sanctionsDob) ? 1.0 : 0.0;
    }
}
