package com.sifat.MyCRM.contorller;

import com.sifat.MyCRM.dto.external.USSanctionListDBOutDTO;
import com.sifat.MyCRM.dto.external.USSanctionListDataOutDTO;
import com.sifat.MyCRM.dto.helper.ResponseModelDTO;
import com.sifat.MyCRM.dto.input.CreateIndividualCustomerDTO;
import com.sifat.MyCRM.dto.input.CreateSanctionConfigDataDTO;
import com.sifat.MyCRM.dto.input.IndividualCustomerCompareInDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerCompareResultOutDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerViewDTO;
import com.sifat.MyCRM.service.CustomerService;
import com.sifat.MyCRM.service.SanctionComparisonService;
import com.sifat.MyCRM.service.SanctionService;
import com.sifat.MyCRM.service.SanctionXmlService;
import com.sifat.MyCRM.utility.ResponseDataStatus;
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
    public ResponseEntity<ResponseModelDTO> savedSanctionData (@RequestParam("file") MultipartFile file) throws Exception {
        try {
            USSanctionListDBOutDTO result = sanctionService.savedSanctionData(file.getInputStream());
            ResponseModelDTO responseModelDTO = ResponseModelDTO.builder()
                    .status(ResponseDataStatus.success.name())
                    .message("All UN-Sanctioned XML data saved successfully")
                    .data(result)
                    .build();
            return ResponseEntity.ok(responseModelDTO);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
    }

    @Tag(name = "CRM004 : view single customer data")    //done
    @GetMapping("/view/{individual_customer_id}/individual-customer")
    public IndividualCustomerViewDTO getIndividualCustomer (@PathVariable("individual_customer_id") String individualCustomerId){
        return customerService.getIndividualCustomer(individualCustomerId);
    }

    @Tag(name = "CRM005 : compare dto-passed individual customer data (GET SCORE)")    //done
    @PostMapping("/compare/individual/sanction-data")
    public ResponseEntity<List<IndividualCustomerCompareResultOutDTO>> compareIndividualData (@RequestBody @Valid IndividualCustomerCompareInDTO inDTO) throws Exception {
        List<IndividualCustomerCompareResultOutDTO> outDTO =  sanctionComparisonService.compareIndividualData(inDTO);
        return ResponseEntity.ok(outDTO);
    }

    @Tag(name = "CRM006 : individual customer creation")
    @GetMapping("/create/individual-customer")
    public ResponseEntity<ResponseModelDTO> createIndividualCustomer (@RequestBody @Valid CreateIndividualCustomerDTO createIndividualCustomerDTO) throws Exception {
        try {
        String savedCustomerId = customerService.createIndividualCustomer(createIndividualCustomerDTO);
        ResponseModelDTO responseModelDTO = ResponseModelDTO.builder().build();
            responseModelDTO.setStatus(ResponseDataStatus.success.name());
            responseModelDTO.setMessage("Customer created successfully");
            responseModelDTO.setData(savedCustomerId);
            return ResponseEntity.ok(responseModelDTO);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
    }

    @Tag(name = "CRM007 : saved individual customer aml verification")
    @GetMapping("/verify/aml/{individual_customer_id}/saved/individual-customer")
    public ResponseEntity<ResponseModelDTO> verifySavedIndividualCustomerAML (@PathVariable("individual_customer_id") String individualCustomerId) throws Exception {
        try {
            List<IndividualCustomerCompareResultOutDTO> res = customerService.verifySavedIndividualCustomerAML(individualCustomerId);
            ResponseModelDTO responseModelDTO = ResponseModelDTO.builder().build();
            responseModelDTO.setStatus(ResponseDataStatus.success.name());
            responseModelDTO.setMessage("Customer verification info found successfully");
            responseModelDTO.setData(res);
            return ResponseEntity.ok(responseModelDTO);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Tag(name = "CRM008 : save aml sanction configuration data")    //done
    @PostMapping("/save/configuration/sanction-data")
    public ResponseEntity<ResponseModelDTO> saveSanctionConfigData (@RequestBody @Valid CreateSanctionConfigDataDTO inDTO) {
        sanctionComparisonService.saveSanctionConfigData(inDTO);
        ResponseModelDTO responseModelDTO = ResponseModelDTO.builder().build();
        responseModelDTO.setStatus(ResponseDataStatus.success.name());
        responseModelDTO.setMessage("Sanction configuration data updated successfully");
        responseModelDTO.setData(null);
        return ResponseEntity.ok(responseModelDTO);
    }


}
