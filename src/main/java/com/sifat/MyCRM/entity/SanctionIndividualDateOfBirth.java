package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_individual_date_birth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualDateOfBirth {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_id", nullable = false)
    private SanctionIndividual individualId;

    @Column(name = "type_of_date")
    private String type_of_date; //EXACT, BETWEEN

    @Column(name = "date")
    private String date;

    @Column(name = "from_year")
    private String from_year;

    @Column(name = "to_year")
    private String to_year;

    @Column(name = "year")
    private String year;

    @Column(name = "note")
    private String note;
}