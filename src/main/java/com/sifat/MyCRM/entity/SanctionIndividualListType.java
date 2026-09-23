package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sanction_individual_list_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualListType {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    private SanctionIndividual individualId;

    @Column(name = "list_type")
    private String list_type;
}