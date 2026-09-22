package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService{


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }


    public String viewSavedSanctionDataFromDB() {
        return "Viewing db data";
    }

    public String searchSanctionData(){
        return "searching ...";
    }
}
