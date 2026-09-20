package com.sifat.MyCRM.service;

import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService{


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }
}
