package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sanction_individual_place_of_birth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualPlaceOfBirth {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    @JsonBackReference
    private SanctionIndividual individual;

    @Column(name = "country")
    private String country;

    @Column(name = "state_province")
    private String state_province;

    @Column(name = "city")
    private String city;
}