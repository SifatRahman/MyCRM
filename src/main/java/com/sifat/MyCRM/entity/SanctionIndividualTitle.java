package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_individual_title")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualTitle {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    private SanctionIndividual individualId;

    @Column(name = "title")
    private String title;
}