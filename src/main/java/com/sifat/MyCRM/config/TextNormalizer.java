package com.sifat.MyCRM.config;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class TextNormalizer {

    public String normalize(String value) {

        if (value == null) {
            return "";
        }

        return Normalizer.normalize(value, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toUpperCase()
                .replaceAll("[^A-Z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}