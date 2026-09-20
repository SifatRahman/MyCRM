package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;

@Service
public class BaseService {
    protected String showBaseName(){
        return "Hello sifat";
    }
}
