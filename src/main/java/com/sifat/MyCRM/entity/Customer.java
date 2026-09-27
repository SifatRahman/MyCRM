package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerSanctionIndividualComparisonHistory> sanctionIndividualComparisonHistory;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column(name = "full_name2")
    private String full_name_2;

    @Column(name = "family_name")
    private String family_name;

    @Column(name = "short_name")
    private String short_name;

    @Column(name = "mnemonic")
    private String mnemonic;

    @Column(name = "gender")
    private String gender; //male, female, third gender

    @Column(name = "account_officer")
    private Integer account_officer;

    @Column(name = "sector")
    private Integer sector;

    @Column(name = "target")
    private Integer target;

    @Column(name = "customer_status")
    private Integer customer_status;// 1->Individual, 2-> Corporate

    @Column(name = "industry")
    private String industry;

    @Column(name = "language")
    private Integer language; // 1->Eng, 2-> BD

    @Column(name = "residence")
    private String residence; //All BD

    @Column(name = "date_of_birth",nullable = false)
    private LocalDate date_of_birth;

    @Column(name = "nationality",nullable = false)
    private String nationality;

    @Column(name = "nid_no",nullable = false)
    private String nid_no; //nid_number

    @Column(name = "passport_no",nullable = false)
    private String passport_no; //passport_no

    @Column(name = "father_name",nullable = false)
    private String father_name;

    @Column(name = "mother_name",nullable = false)
    private String mother_name;

    @Column(name = "marital_status",nullable = false)
    private String marital_status;

    @Column(name = "spouse")
    private String spouse;

    @Column(name = "cb_sector_code")
    private String cb_sector_code;

    @Column(name = "return_submission_date")
    private LocalDate return_submission_date;

    @Column(name = "sms_alert_service")
    private Boolean sms_alert_service;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
