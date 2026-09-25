package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sanction_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionAddress {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id")
    @JsonBackReference
    private SanctionIndividual individual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    @JsonBackReference
    private SanctionEntity entity;

    @Column(name = "customer_type", nullable = false)
    private String customerType;

    @Column(name = "street",length = 1200)
    private String street;

    @Column(name = "zip_code",length = 1200)
    private String zip_code;

    @Column(name = "state_province",length = 1200)
    private String state_province;

    @Column(name = "city",length = 1200)
    private String city;

    @Column(name = "country",length = 1200)
    private String country;

    @Column(name = "note",columnDefinition = "TEXT")
    private String note;
}
