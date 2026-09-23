package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_alias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionAlias {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id")
    @JsonBackReference
    private SanctionIndividual individual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id")
    private SanctionEntity entity;

    @Column(name = "customer_type", nullable = false)
    private String customerType;

    @Column(name = "quality")
    private String quality;

    @Column(name = "alias_name")
    private String alias_name;
}