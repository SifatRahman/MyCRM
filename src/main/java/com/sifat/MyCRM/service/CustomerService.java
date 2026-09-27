package com.sifat.MyCRM.service;

import com.sifat.MyCRM.dto.output.CustomerViewDTO;
import com.sifat.MyCRM.entity.Customer;
import com.sifat.MyCRM.entity.CustomerSanctionIndividualComparisonHistory;
import com.sifat.MyCRM.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class CustomerService extends BaseService{
    private CustomerRepository customerRepository;


    public String Hello(){
        var msg = showBaseName();
        return msg;
    }

    public CustomerViewDTO getCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException(
                "Customer not found with id: " + customerId
        ));

        CustomerSanctionIndividualComparisonHistory comparisonHistory = null;
        if(!customer.getSanctionIndividualComparisonHistory().isEmpty()){
            comparisonHistory = customer.getSanctionIndividualComparisonHistory()
                            .stream()
                            .max(Comparator.comparing(
                                    CustomerSanctionIndividualComparisonHistory::getTimestamp
                            ))
                            .orElse(null);
        }

        return CustomerViewDTO.builder()
                .id(customer.getId())

                .sanction_individual_comparison_status(comparisonHistory!=null?comparisonHistory.getMatchStatus():null)

                .full_name(customer.getFull_name())
                .full_name_2(customer.getFull_name_2())
                .family_name(customer.getFamily_name())
                .short_name(customer.getShort_name())

                .mnemonic(customer.getMnemonic())
                .gender(customer.getGender())

                .account_officer(customer.getAccount_officer())
                .sector(customer.getSector())
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
}
