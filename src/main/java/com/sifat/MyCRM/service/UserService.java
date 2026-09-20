package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService{


    private String Hello(){
        var msg = showBaseName();
        return msg;
    }
}
