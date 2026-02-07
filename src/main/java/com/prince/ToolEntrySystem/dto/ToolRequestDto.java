package com.prince.ToolEntrySystem.dto;

import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ToolRequestDto {

    @NotBlank(message = "Tool name is required")
    private String toolName;

    @Enumerated(EnumType.STRING)
    private ToolType toolType;

    @Enumerated(EnumType.STRING)
    private ToolStatus status;

    @NotNull(message = "Quantity required")
    private Integer quantity;

    @NotNull(message = "Facility Id required")
    private Long facilityId;
}
