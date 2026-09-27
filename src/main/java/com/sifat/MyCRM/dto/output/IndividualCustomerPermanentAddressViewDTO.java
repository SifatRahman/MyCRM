package com.sifat.MyCRM.dto.output;

import com.sifat.MyCRM.entity.IndividualCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerPermanentAddressViewDTO {
    private String customer_id;
    private String country;
    private String division_or_state;
    private String district;
    private String upazila;
    private String police_station;
    private String post_code;
    private String village_or_area;
    private String road_or_block;
    private String house_or_flat_no;
    private String mobile_no;
    private String phone_number_off_1;
    private String email_address;
}
