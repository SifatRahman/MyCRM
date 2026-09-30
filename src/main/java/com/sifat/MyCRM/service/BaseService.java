package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class BaseService {
    protected String showBaseName(){
        return "Hello from AML Project";
    }

    public String getUUID(){
        return UUID.randomUUID().toString();
    }

    protected boolean isBlankStringOrNull(String string) {
        return string == null
                || string.isBlank()
                || "null".equalsIgnoreCase(string.trim());
    }
}
