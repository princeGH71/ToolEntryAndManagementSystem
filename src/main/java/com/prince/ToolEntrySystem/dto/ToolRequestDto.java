package com.prince.ToolEntrySystem.dto;

import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolRequestDto {

    @NotBlank(message = "Tool name is required")
    private String toolName;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Tool type is required")
    private ToolType toolType;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "status required")
    private ToolStatus status;

    @NotBlank(message = "Quantity required")
    private Integer quantity;

    @NotBlank(message = "Facility Id required")
    private Long facilityId;
}
