package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "individual_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualCustomer {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @OneToMany(mappedBy = "individualCustomer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerSanctionIndividualComparisonHistory> sanctionIndividualComparisonHistory;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "full_name2")
    private String fullName2;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "mnemonic")
    private String mnemonic;

    @Column(name = "gender")
    private String gender; //male, female, third gender

    @Column(name = "account_officer")
    private Integer accountOfficer;

    @Column(name = "sector")
    private Integer sector;

    @Column(name = "target")
    private Integer target;

    @Column(name = "customer_status")
    private Integer customerStatus;// 1->Individual, 2-> Corporate

    @Column(name = "industry")
    private String industry;

    @Column(name = "language")
    private Integer language; // 1->Eng, 2-> BD

    @Column(name = "residence")
    private String residence; //All BD

    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "nationality",nullable = false)
    private String nationality;

    @Column(name = "nid_no")
    private String nidNo; //nid_number

    @Column(name = "passport_no")
    private String passportNo; //passport_no

    @Column(name = "father_name",nullable = false)
    private String fatherName;

    @Column(name = "mother_name",nullable = false)
    private String motherName;

    @Column(name = "marital_status",nullable = false)
    private String maritalStatus;

    @Column(name = "spouse")
    private String spouse;

    @Column(name = "cb_sector_code")
    private String cbSectorCode;

    @Column(name = "return_submission_date")
    private LocalDate returnSubmissionDate;

    @Column(name = "sms_alert_service")
    private Boolean smsAlertService;

    @Column(name = "is_sanction_aml_verified")
    private Boolean isSanctionAMLVerified;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
