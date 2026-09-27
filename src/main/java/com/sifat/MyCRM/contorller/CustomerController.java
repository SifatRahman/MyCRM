package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.external.USSanctionListDBOutDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.input.CreateIndividualCustomerDTO;
import com.sifat.MyCRM.dto.input.IndividualCustomerCompareInDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerViewDTO;
import com.sifat.MyCRM.service.CustomerService;
import com.sifat.MyCRM.service.SanctionComparisonService;
import com.sifat.MyCRM.service.SanctionService;
import com.sifat.MyCRM.service.SanctionXmlService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final SanctionService sanctionService;
    private final SanctionComparisonService sanctionComparisonService;

    @Tag(name = "CRM001 : see service health")    //done
    @GetMapping("/test")
    public String testApi (){
        return customerService.Hello();
    }

    @Tag(name = "CRM002 : upload un-xml file to view")   //done
    @PostMapping("/upload/sanction/xml-file")
    public ResponseEntity<USSanctionListDataOutDTO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDataOutDTO result = sanctionXmlService.parseXmlFile(file.getInputStream());
        return ResponseEntity.ok(result);
    }

    @Tag(name = "CRM003 : save xml to DB") //done
    @PostMapping("/save/sanction-data")
    public ResponseEntity<USSanctionListDBOutDTO> savedSanctionData (@RequestParam("file") MultipartFile file) throws Exception {
        USSanctionListDBOutDTO result = sanctionService.savedSanctionData(file.getInputStream());
        return ResponseEntity.ok(result);
    }

    @Tag(name = "CRM004 : view single customer data")    //done
    @GetMapping("/view/{individual_customer_id}/individual_customer")
    public IndividualCustomerViewDTO getIndividualCustomer (@PathVariable("individual_customer_id") String individualCustomerId){
        return customerService.getIndividualCustomer(individualCustomerId);
    }

    @Tag(name = "CRM005 : compare individual customer data (GET SCORE)")    //done
    @PostMapping("/compare/individual/sanction-data")
    public ResponseEntity<List<IndividualCustomerCompareResultOutDTO>> compareIndividualData (@RequestBody @Valid IndividualCustomerCompareInDTO inDTO) throws Exception {
        List<IndividualCustomerCompareResultOutDTO> outDTO =  sanctionComparisonService.compareIndividualData(inDTO);
        return ResponseEntity.ok(outDTO);
    }

    @Tag(name = "CRM004 : individual customer creation")
    @GetMapping("/create/individual-customer")
    public ResponseEntity<IndividualCustomerViewDTO> createIndividualCustomer (@RequestBody @Valid CreateIndividualCustomerDTO createIndividualCustomerDTO) throws Exception {
        IndividualCustomerViewDTO individualCustomerViewDTO = customerService.createIndividualCustomer(createIndividualCustomerDTO);
        return ResponseEntity.ok(individualCustomerViewDTO);
    }
}
