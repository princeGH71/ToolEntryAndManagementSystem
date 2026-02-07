package com.prince.ToolEntrySystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityResponseDto {

    private Long id;                 // Facility ID
    private String facilityCode;     // Unique code, e.g., "FAB10A"
    private String facilityName;
    private String location;         // City/State
    private String country;
    private String siteType;         // e.g., "FAB", "Assembly", "Test"
    private List<String> facilitySystems; // Optional notes
    private Boolean isActive;        // Facility operational or not
}
