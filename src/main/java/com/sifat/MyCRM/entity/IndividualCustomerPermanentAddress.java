package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "individual_customer_permanent_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualCustomerPermanentAddress {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_customer_id",nullable = false)
    private IndividualCustomer individualCustomer;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "division_or_state")
    private String division_or_state;

    @Column(name = "district")
    private String district;

    @Column(name = "upazila")
    private String upazila;

    @Column(name = "police_station")
    private String police_station;

    @Column(name = "post_code")
    private String post_code;

    @Column(name = "village_or_area")
    private String village_or_area;

    @Column(name = "road_or_block")
    private String road_or_block;

    @Column(name = "house_or_flat_no")
    private String house_or_flat_no;

    @Column(name = "mobile_no")
    private String mobile_no;

    @Column(name = "phone_number_off_1")
    private String phone_number_off_1;

    @Column(name = "email_address")
    private String email_address;


}