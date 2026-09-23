package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_individual_designation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualDesignation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    private SanctionIndividual individualId;

    @Column(name = "designation", nullable = false)
    private String designation;
}