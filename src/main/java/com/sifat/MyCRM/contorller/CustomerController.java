package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.input.CustomerAMLIndividualBasicInDTO;
import com.sifat.MyCRM.service.CustomerService;
import com.sifat.MyCRM.service.SanctionService;
import com.sifat.MyCRM.service.SanctionXmlService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;
    private final SanctionXmlService sanctionXmlService;
    private final SanctionService sanctionService;

    @Tag(name = "CRM001 : see service health")
    @GetMapping("/test")
    public String testApi (){
        return customerService.Hello();
    }

    @Tag(name = "CRM002 : upload un-xml file to view")
    @PostMapping("/upload/sanction/xml-file")
    public ResponseEntity<USSanctionListDataOutDTO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDataOutDTO result = sanctionXmlService.parseXmlFile(file.getInputStream());
        return ResponseEntity.ok(result);
    }

    @Tag(name = "CRM003 : save xml to DB")
    @PostMapping("/save/sanction-data")
    public ResponseEntity<USSanctionListDataOutDTO> savedSanctionData (@RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDataOutDTO result = sanctionService.savedSanctionData(file.getInputStream());
        return ResponseEntity.ok(result);
    }

    @Tag(name = "CRM004 : view data from DB")
    @GetMapping("/view/db/sanction-data")
    public String viewSavedSanctionDataFromDB (){
        return customerService.viewSavedSanctionDataFromDB();
    }

    @Tag(name = "CRM005 : search sanction data")
    @GetMapping("/search/sanction-data")
    public String searchSanctionData (){
        return customerService.searchSanctionData();
    }



}
