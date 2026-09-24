package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
public class TextNormalizer {

    public String normalize(String value) {

        if (value == null) {
            return "";
        }

        return Normalizer.normalize(value, Normalizer.Form.NFKC)
                .toUpperCase(Locale.ROOT) //all languages being uppercase
                .replaceAll("[^\\p{L}\\p{N}]+", " ") //remove all non-alphanumeric characters of Unicode
                .replaceAll("\\s+", " ") //one or more spaces to one space
                .trim();
    }
}