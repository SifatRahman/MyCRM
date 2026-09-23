package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_individual_date_birth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualDocument {


    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id",nullable = false)
    @JsonBackReference
    private SanctionIndividual individual;

    @Column(name = "type_of_document")
    private String type_of_document;

    @Column(name = "type_of_document2")
    private String type_of_document2;

    @Column(name = "number")
    private String number;

    @Column(name = "issuing_country")
    private String issuing_country;

    @Column(name = "date_of_issue")
    private String date_of_issue;

    @Column(name = "date_of_expiry")
    private String date_of_expiry;

    @Column(name = "city_of_issue")
    private String city_of_issue;

    @Column(name = "country_of_issue")
    private String country_of_issue;

    @Column(name = "note")
    private String note;
}
