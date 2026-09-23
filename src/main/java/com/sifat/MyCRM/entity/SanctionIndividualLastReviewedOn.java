package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_individual_last_reviewed_on")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualLastReviewedOn {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    @JsonBackReference
    private SanctionIndividual individual;

    @Column(name = "last_reviewed_on")
    private String lastReviewedOn;
}