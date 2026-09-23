package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class BaseService {
    protected String showBaseName(){
        return "Hello sifat";
    }

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
