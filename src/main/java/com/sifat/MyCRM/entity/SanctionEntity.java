package com.sifat.MyCRM.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "sanction_entity",
        indexes = {
                @Index(name = "idx_entity_data_id", columnList = "data_id"),
                @Index(name = "idx_entity_first_name", columnList = "first_name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionEntity {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @Column(name = "data_id", unique = true, nullable = false)
    private String dataId;

    @Column(name = "version_no")
    private String versionNo;

    @Column(name = "first_name", length = 512)
    private String firstName;

    @Column(name = "un_list_type", length = 512)
    private String unListType;

    @Column(name = "reference_number", length = 512)
    private String referenceNumber;

    @Column(name = "listed_on")
    private String listedOn;

    @Column(name = "name_original_script", length = 1200)
    private String nameOriginalScript;

    @Column(name = "comments1", columnDefinition = "TEXT")
    private String comments1;

    @Column(name = "has_interpol_link")
    private String hasInterpolLink;

    @Column(name = "interpol_link", length = 1200)
    private String interpolLink;


    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SanctionEntityListType> listTypes = new ArrayList<>();

    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SanctionEntityLastDayUpdated> lastDayUpdated = new ArrayList<>();

    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SanctionEntityLastReviewedOn> lastReviewedOns = new ArrayList<>();

    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SanctionAlias> entityAlias = new ArrayList<>();

    @OneToMany(
            mappedBy = "entity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<SanctionAddress> entityAddress = new ArrayList<>();

}
