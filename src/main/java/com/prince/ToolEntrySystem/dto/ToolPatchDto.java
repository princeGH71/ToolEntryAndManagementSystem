package com.prince.ToolEntrySystem.dto;

import com.prince.ToolEntrySystem.enums.ToolType;
import lombok.Data;


@Data
public class ToolPatchDto {
    private String toolName;       // optional
    private ToolType toolType;     // optional
    private Integer quantity;      // optional
    private Long facilityId;       // optional, can move to another facility
}