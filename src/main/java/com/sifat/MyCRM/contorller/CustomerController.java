package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualBasicInDTO;
import com.sifat.MyCRM.service.CustomerService;
import com.sifat.MyCRM.service.SanctionXmlService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;
    private final SanctionXmlService sanctionXmlService;

    @Tag(name = "CRM001")
    @GetMapping("/test")
    public String testApi (){
        return customerService.Hello();
    }

    @Tag(name = "CRM003")
    @PostMapping("/validate/customer-aml")
    public String validateCustomerAML (@RequestBody @Valid CustomerAMLIndividualBasicInDTO inputDTO){
        return customerService.validateUserAML(inputDTO);
    }

    @Tag(name = "CRM002")
    @PostMapping("/xml/upload")
    public ResponseEntity<USSanctionListDataOutDTO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDataOutDTO result = sanctionXmlService.parseXmlFile(file.getInputStream());
        return ResponseEntity.ok(result);
    }



}
