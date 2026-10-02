package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "entity_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityCustomer {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @OneToMany(mappedBy = "entityCustomer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerSanctionEntityComparisonHistory> sanctionEntityComparisonHistory;

    @Column(name = "full_name", nullable = false)
    private String fullName;

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

    @Column(name = "cb_sector_code")
    private String cbSectorCode;

    @Column(name = "return_submission_date")
    private LocalDate returnSubmissionDate;

    @Column(name = "sms_alert_service")
    private Boolean smsAlertService;

    @Column(name = "is_sanction_aml_verified",nullable = false)
    private Boolean isSanctionAMLVerified;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
