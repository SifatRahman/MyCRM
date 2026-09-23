package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sanction_entity_list_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionEntityListType {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private SanctionEntity entity;

    @Column(name = "list_type")
    private String list_type;
}