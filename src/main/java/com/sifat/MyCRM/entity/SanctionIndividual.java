package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "sanction_individual",
        indexes = {
                @Index(name = "idx_individual_data_id", columnList = "data_id"),
                @Index(name = "idx_individual_first_name", columnList = "first_name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividual {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @Column(name = "data_id", unique = true, nullable = false)
    private String dataId;

    @Column(name = "version_no", unique = true, nullable = false)
    private String versionNo;

    @Column(name = "first_name", length = 512)
    private String firstName;

    @Column(name = "second_name", length = 512)
    private String secondName;

    @Column(name = "third_name", length = 512)
    private String thirdName;

    @Column(name = "fourth_name", length = 512)
    private String fourthName;

    @Column(name = "un_list_type", length = 512)
    private String unListType;

    @Column(name = "reference_number", length = 512)
    private String referenceNumber;

    @Column(name = "listed_on")
    private String listedOn;

    @Column(name = "name_original_script")
    private String nameOriginalScript;

    @Column(name = "comments1", columnDefinition = "TEXT")
    private String comments1;

    @Column(name = "has_interpol_link")
    private String hasInterpolLink;

    @Column(name = "interpol_link", length = 1200)
    private String interpolLink;

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualDesignation> designations = new ArrayList<>();


    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualNationality> nationalities = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualListType> listTypes = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualLastDayUpdated> lastDayUpdated = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualLastReviewedOn> lastReviewedOns = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionAlias> individualAlias = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionAddress> individualAddress = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualTitle> titles = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualDateOfBirth> individualDateOfBirth = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualPlaceOfBirth> individual_place_of_birth = new ArrayList<>();

    @OneToMany(
            mappedBy = "individualId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SanctionIndividualDocument> individual_document = new ArrayList<>();


}
