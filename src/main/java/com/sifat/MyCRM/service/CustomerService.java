package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.input.CustomerAMLIndividualBasicInDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService{


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }


    public String validateUserAML(CustomerAMLIndividualBasicInDTO inDTO){
        var msg = showBaseName();
        return msg + "from validation api";
    }
}
