package com.prince.ToolEntrySystem.entity;

import com.prince.ToolEntrySystem.enums.SiteLocation;
import com.prince.ToolEntrySystem.enums.SiteType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String facilityCode; // Unique code for facility, e.g., "FAB10A"

    @Column(nullable = false)
    private String facilityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "site_location", nullable = false)
    private SiteLocation siteLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SiteType siteType; // e.g., "FAB", "Assembly", "Test"

    @Column(length = 1000)
    private String description;

    // Optional notes
    @ElementCollection
    @CollectionTable(name = "facility_systems", joinColumns = @JoinColumn(name = "facility_id"))
    @Column(name = "system_name")
    private List<String> facilitySystems = new ArrayList<>();

    @Column(nullable = false)
    private boolean active = true;


    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tool> tools = new ArrayList<>();

}