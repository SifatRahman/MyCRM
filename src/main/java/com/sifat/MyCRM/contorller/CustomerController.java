package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.SanctionPersonHelperDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualBasicInDTO;
import com.sifat.MyCRM.service.CustomerService;
import com.sifat.MyCRM.service.SanctionXmlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;
    private final SanctionXmlService sanctionXmlService;

    @GetMapping("/test")
    public String testApi (){
        return customerService.Hello();
    }

    @PostMapping("/validate/customer-aml")
    public String validateCustomerAML (@RequestBody @Valid CustomerAMLIndividualBasicInDTO inputDTO){
        return customerService.validateUserAML(inputDTO);
    }

    @PostMapping("/xml/upload")
    public ResponseEntity<USSanctionListDataOutDTO> upload(
            @RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDataOutDTO result = sanctionXmlService.parseXmlFile(file.getInputStream());
        return ResponseEntity.ok(result);
    }



}
