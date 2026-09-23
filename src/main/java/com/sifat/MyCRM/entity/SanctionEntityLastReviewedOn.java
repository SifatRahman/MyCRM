package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name = "sanction_entity_last_reviewed_on")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionEntityLastReviewedOn {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private SanctionEntity entity;

    @Column(name = "last_reviewed_on")
    private String lastReviewedOn;
}