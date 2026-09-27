package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.input.CreateCustomerDTO;
import com.sifat.MyCRM.dto.input.CreateCustomerPermanentAddressDTO;
import com.sifat.MyCRM.dto.input.CreateIndividualCustomerDTO;
import com.sifat.MyCRM.dto.input.CustomerBasicDetailDTO;
import com.sifat.MyCRM.dto.output.CustomerViewDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerViewDTO;
import com.sifat.MyCRM.entity.Customer;
import com.sifat.MyCRM.entity.CustomerPermanentAddress;
import com.sifat.MyCRM.entity.CustomerSanctionIndividualComparisonHistory;
import com.sifat.MyCRM.repository.CustomerPermanentAddressRepository;
import com.sifat.MyCRM.repository.CustomerRepository;
import com.sifat.MyCRM.repository.IndividualCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class CustomerService extends BaseService{
    private IndividualCustomerRepository individualCustomerRepository;
    private CustomerPermanentAddressRepository customerPermanentAddressRepository;


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }

    public IndividualCustomerViewDTO getCustomer(String customerId) {
        IndividualCustomerViewDTO individualCustomer = individualCustomerRepository.findById(customerId).orElseThrow(() -> new RuntimeException(
                "Customer not found with id: " + customerId
        ));

        CustomerSanctionIndividualComparisonHistory comparisonHistory = null;
        if(!individualCustomer.getSanctionIndividualComparisonHistory().isEmpty()){
            comparisonHistory = individualCustomer.getSanctionIndividualComparisonHistory()
                            .stream()
                            .max(Comparator.comparing(
                                    CustomerSanctionIndividualComparisonHistory::getTimestamp
                            ))
                            .orElse(null);
        }

        return IndividualCustomerViewDTO.builder()
                .id(individualCustomer.getId())

                .sanction_individual_comparison_status(comparisonHistory!=null?comparisonHistory.getMatchStatus():null)

                .full_name(individualCustomer.getFull_name())
                .full_name_2(individualCustomer.getFull_name_2())
                .family_name(individualCustomer.getFamily_name())
                .short_name(individualCustomer.getShort_name())

                .mnemonic(individualCustomer.getMnemonic())
                .gender(individualCustomer.getGender())

                .account_officer(customer.getAccount_officer())
                .sector(individualCustomer.getSector())
                .target(customer.getTarget())
                .customer_status(customer.getCustomer_status())

                .industry(customer.getIndustry())
                .language(customer.getLanguage())
                .residence(customer.getResidence())

                .date_of_birth(customer.getDate_of_birth())
                .nationality(customer.getNationality())

                .nid_no(customer.getNid_no())
                .passport_no(customer.getPassport_no())

                .father_name(customer.getFather_name())
                .mother_name(customer.getMother_name())

                .marital_status(customer.getMarital_status())
                .spouse(customer.getSpouse())

                .cb_sector_code(customer.getCb_sector_code())

                .return_submission_date(
                        customer.getReturn_submission_date()
                )

                .sms_alert_service(
                        customer.getSms_alert_service()
                )
                .build();
    }

    public String getCustomerAMLSanctionInfo(){
        return "searching ...";
    }

    @Transactional(rollbackFor = Exception.class)
    public IndividualCustomerViewDTO createCustomer(CreateIndividualCustomerDTO inDTO) throws Exception {
        try {
            if(inDTO.getNid_no().isBlank() && inDTO.getPassport_no().isBlank()){
                throw new Exception("Either Nid or passport number must be provided!");
            }
            var existsByNID = individualCustomerRepository.findById(inDTO.getNid_no()).orElse(null);
            var existsByPass = individualCustomerRepository.findById(inDTO.getPassport_no()).orElse(null);
            if(existsByNID!=null && existsByPass!=null){
                throw new Exception("Customer with this NID Number or Passport already exists!");
            }

            Customer customer = new Customer();
            customer.setId(getUUID());
            customer.setFull_name(inDTO.getFull_name());
            customer.setFull_name_2(inDTO.getFull_name_2());
            customer.setFamily_name(inDTO.getFamily_name());
            customer.setShort_name(inDTO.getShort_name());
            customer.setMnemonic(inDTO.getMnemonic());
            customer.setGender(inDTO.getGender());
            customer.setAccount_officer(inDTO.getAccount_officer());
            customer.setSector(inDTO.getSector());
            customer.setTarget(inDTO.getTarget());
            customer.setCustomer_status(inDTO.getCustomer_status());
            customer.setIndustry(inDTO.getIndustry());
            customer.setLanguage(inDTO.getLanguage());
            customer.setResidence(inDTO.getResidence());

            if(inDTO.getDate_of_birth().isAfter(LocalDate.now())){
                throw new Exception("Birth date can't be future!");
            }
            customer.setDate_of_birth(inDTO.getDate_of_birth());
            customer.setNationality(inDTO.getNationality());
            customer.setNid_no(inDTO.getNid_no());
            customer.setPassport_no(inDTO.getPassport_no());
            customer.setFather_name(inDTO.getFather_name());
            customer.setMother_name(inDTO.getMother_name());
            customer.setMarital_status(inDTO.getMarital_status());
            customer.setSpouse(inDTO.getSpouse());
            customer.setCb_sector_code(inDTO.getCb_sector_code());
            customer.setReturn_submission_date(inDTO.getReturn_submission_date());
            customer.setSms_alert_service(inDTO.getSms_alert_service());
            customer.setIsSanctionAMLVerified(false);
            customer.setTimestamp(LocalDateTime.now());


            //Saving permanent address
            CreateCustomerPermanentAddressDTO inCPA = inDTO.getCustomer_permanent_address();
            CustomerPermanentAddress cpa = new CustomerPermanentAddress();
            cpa.setId(getUUID());
            cpa.setCustomer(customer);
            cpa.setCountry(inCPA.getCountry());
            cpa.setDivision_or_state(inCPA.getDivision_or_state());
            cpa.setDistrict(inCPA.getDistrict());
            cpa.setUpazila(inCPA.getUpazila());
            cpa.setPolice_station(inCPA.getPolice_station());
            cpa.setPost_code(inCPA.getPost_code());
            cpa.setVillage_or_area(inCPA.getVillage_or_area());
            cpa.setRoad_or_block(inCPA.getRoad_or_block());
            cpa.setHouse_or_flat_no(inCPA.getHouse_or_flat_no());
            cpa.setMobile_no(inCPA.getMobile_no());
            cpa.setPhone_number_off_1(inCPA.getPhone_number_off_1());
            cpa.setEmail_address(inCPA.getEmail_address());




            //sanction verification
            verifyAMLSanctionInfoAndSaveHistory(customer,cpa);



            Customer savedCustomer = customerRepository.save(customer);
            CustomerPermanentAddress customerPermanentAddress = customerPermanentAddressRepository.save(cpa);


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }
}
