package com.prince.ToolEntrySystem.service;

import com.prince.ToolEntrySystem.dto.ToolRequestDto;
import com.prince.ToolEntrySystem.dto.ToolResponseDto;
import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.entity.Tool;
import com.prince.ToolEntrySystem.repository.FacilityRepository;
import com.prince.ToolEntrySystem.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToolService {
    
    private final ToolRepository toolRepository;
    private final FacilityRepository facilityRepository;
    private final ModelMapper modelMapper;
    public ToolResponseDto enterTool(ToolRequestDto toolRequestDto) {
        Facility facility = facilityRepository.findById(toolRequestDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility Id Doesn't Exist"));
        log.info("Facility corresponding to tool found");
                
        Tool tool = modelMapper.map(toolRequestDto, Tool.class);
        tool.setId(null);
        tool.setFacility(facility);
        log.info("toolRequest mapped tool and facility is set in tool| facility_code {}", facility.getFacilityCode());
        
        Tool savedTool = toolRepository.save(tool);
        log.info("tool saved in DB");
        ToolResponseDto toolResponseDto = modelMapper.map(savedTool, ToolResponseDto.class);
//        tool facility_id needs to be set here else it can't fetch and map automatically from Facility to FacilityId for Response
        toolResponseDto.setFacilityId(facility.getId());
        return toolResponseDto;
    }
}
