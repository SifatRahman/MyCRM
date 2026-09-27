package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.input.*;
import com.sifat.MyCRM.dto.output.IndividualCustomerPermanentAddressViewDTO;
import com.sifat.MyCRM.dto.output.IndividualCustomerViewDTO;
import com.sifat.MyCRM.entity.CustomerSanctionIndividualComparisonHistory;
import com.sifat.MyCRM.entity.IndividualCustomer;
import com.sifat.MyCRM.entity.IndividualCustomerPermanentAddress;
import com.sifat.MyCRM.repository.IndividualCustomerPermanentAddressRepository;
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
    private final IndividualCustomerRepository individualCustomerRepository;
    private final IndividualCustomerPermanentAddressRepository individualCustomerPermanentAddressRepository;


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }

    public IndividualCustomerViewDTO getIndividualCustomer(String individualCustomerId) {
        IndividualCustomer individualCustomer = individualCustomerRepository.findById(individualCustomerId).orElseThrow(() -> new RuntimeException(
                "Individual customer not found with id: " + individualCustomerId
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
        IndividualCustomerPermanentAddress permanentAddress = individualCustomerPermanentAddressRepository.findById(individualCustomer.getId())
                .orElseThrow(() -> new RuntimeException("Individual customer permanent address not found!"));

        IndividualCustomerPermanentAddressViewDTO individualCustomerPermanentAddress = IndividualCustomerPermanentAddressViewDTO.builder()
                .customer_id(permanentAddress.getIndividualCustomer().getId())
                .country(permanentAddress.getCountry())
                .division_or_state(permanentAddress.getDivision_or_state())
                .district(permanentAddress.getDistrict())
                .upazila(permanentAddress.getUpazila())
                .police_station(permanentAddress.getPolice_station())
                .post_code(permanentAddress.getPost_code())
                .village_or_area(permanentAddress.getVillage_or_area())
                .road_or_block(permanentAddress.getRoad_or_block())
                .house_or_flat_no(permanentAddress.getHouse_or_flat_no())
                .mobile_no(permanentAddress.getMobile_no())
                .phone_number_off_1(permanentAddress.getPhone_number_off_1())
                .email_address(permanentAddress.getEmail_address())
                .build();

        return IndividualCustomerViewDTO.builder()
                .id(individualCustomer.getId())

                .sanction_individual_comparison_status(comparisonHistory!=null?comparisonHistory.getMatchStatus():null)

                .full_name(individualCustomer.getFullName())
                .full_name_2(individualCustomer.getFullName2())
                .family_name(individualCustomer.getFamilyName())
                .short_name(individualCustomer.getShortName())

                .mnemonic(individualCustomer.getMnemonic())
                .gender(individualCustomer.getGender())

                .account_officer(individualCustomer.getAccountOfficer())
                .sector(individualCustomer.getSector())
                .target(individualCustomer.getTarget())
                .customer_status(individualCustomer.getCustomerStatus())

                .industry(individualCustomer.getIndustry())
                .language(individualCustomer.getLanguage())
                .residence(individualCustomer.getResidence())

                .date_of_birth(individualCustomer.getDateOfBirth())
                .nationality(individualCustomer.getNationality())

                .nid_no(individualCustomer.getNidNo())
                .passport_no(individualCustomer.getPassportNo())

                .father_name(individualCustomer.getFatherName())
                .mother_name(individualCustomer.getMotherName())

                .marital_status(individualCustomer.getMaritalStatus())
                .spouse(individualCustomer.getSpouse())

                .cb_sector_code(individualCustomer.getCbSectorCode())

                .return_submission_date(
                        individualCustomer.getReturnSubmissionDate()
                )

                .sms_alert_service(
                        individualCustomer.getSmsAlertService()
                )
                .customer_permanent_address(individualCustomerPermanentAddress)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public IndividualCustomerViewDTO createIndividualCustomer(CreateIndividualCustomerDTO inDTO) throws Exception {
        try {
            if(inDTO.getNid_no().isBlank() && inDTO.getPassport_no().isBlank()){
                throw new Exception("Either Nid or passport number must be provided!");
            }
            var existsByNID = individualCustomerRepository.findById(inDTO.getNid_no()).orElse(null);
            var existsByPass = individualCustomerRepository.findById(inDTO.getPassport_no()).orElse(null);
            if(existsByNID!=null && existsByPass!=null){
                throw new Exception("Customer with this NID Number or Passport already exists!");
            }

            IndividualCustomer customer = new IndividualCustomer();
            customer.setId(getUUID());
            customer.setFullName(inDTO.getFull_name());
            customer.setFullName2(inDTO.getFull_name_2());
            customer.setFamilyName(inDTO.getFamily_name());
            customer.setShortName(inDTO.getShort_name());
            customer.setMnemonic(inDTO.getMnemonic());
            customer.setGender(inDTO.getGender());
            customer.setAccountOfficer(inDTO.getAccount_officer());
            customer.setSector(inDTO.getSector());
            customer.setTarget(inDTO.getTarget());
            customer.setCustomerStatus(inDTO.getCustomer_status());
            customer.setIndustry(inDTO.getIndustry());
            customer.setLanguage(inDTO.getLanguage());
            customer.setResidence(inDTO.getResidence());

            if(inDTO.getDate_of_birth().isAfter(LocalDate.now())){
                throw new Exception("Birth date can't be future!");
            }
            customer.setDateOfBirth(inDTO.getDate_of_birth());
            customer.setNationality(inDTO.getNationality());
            customer.setNidNo(inDTO.getNid_no());
            customer.setPassportNo(inDTO.getPassport_no());
            customer.setFatherName(inDTO.getFather_name());
            customer.setMotherName(inDTO.getMother_name());
            customer.setMaritalStatus(inDTO.getMarital_status());
            customer.setSpouse(inDTO.getSpouse());
            customer.setCbSectorCode(inDTO.getCb_sector_code());
            customer.setReturnSubmissionDate(inDTO.getReturn_submission_date());
            customer.setSmsAlertService(inDTO.getSms_alert_service());
            customer.setIsSanctionAMLVerified(false);
            customer.setTimestamp(LocalDateTime.now());


            //Saving permanent address
            CreateIndividualCustomerPermanentAddressDTO inCPA = inDTO.getIndividual_customer_permanent_address();
            IndividualCustomerPermanentAddress cpa = new IndividualCustomerPermanentAddress();
            cpa.setId(getUUID());
            cpa.setIndividualCustomer(customer);
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


            IndividualCustomer savedIndividualCustomer = individualCustomerRepository.save(customer);
            IndividualCustomerPermanentAddress customerPermanentAddress = individualCustomerPermanentAddressRepository.save(cpa);


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return null;
    }

    private void verifyAMLSanctionInfoAndSaveHistory(IndividualCustomer ic, IndividualCustomerPermanentAddress cpa) {
    }
}
