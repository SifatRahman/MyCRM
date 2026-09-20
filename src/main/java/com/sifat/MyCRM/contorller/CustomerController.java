package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.input.CustomerAMLIndividualBasicInDTO;
import com.sifat.MyCRM.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/test")
    public String testApi (){
        return customerService.Hello();
    }

    @PostMapping("/validate/customer-aml")
    public String validateCustomerAML (@RequestBody @Valid CustomerAMLIndividualBasicInDTO inputDTO){
        return customerService.validateUserAML(inputDTO);
    }



}
