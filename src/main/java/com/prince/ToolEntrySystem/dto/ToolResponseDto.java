package com.prince.ToolEntrySystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolResponseDto {
    private Long id;

    private String toolName;
    private String toolType;
    private String status;

    private Integer quantity;
    private Long facilityId;

}
