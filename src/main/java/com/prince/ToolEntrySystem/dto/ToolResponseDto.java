package com.prince.ToolEntrySystem.dto;

import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ToolResponseDto {
    private Long id;

    private String toolName;
    private ToolType toolType;
    private ToolStatus status;

    private Integer quantity;
    private Long facilityId;

}
