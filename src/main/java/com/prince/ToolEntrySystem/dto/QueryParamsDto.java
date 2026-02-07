package com.prince.ToolEntrySystem.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QueryParamsDto {
    private String siteLocation;
    private String siteType;
    private String facilityCode;
    private Boolean isActive;
}
