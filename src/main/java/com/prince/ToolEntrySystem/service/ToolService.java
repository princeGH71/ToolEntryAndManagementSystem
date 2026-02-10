package com.prince.ToolEntrySystem.service;

import com.prince.ToolEntrySystem.dto.ToolPatchDto;
import com.prince.ToolEntrySystem.dto.ToolQueryParamsDto;
import com.prince.ToolEntrySystem.dto.ToolRequestDto;
import com.prince.ToolEntrySystem.dto.ToolResponseDto;
import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.entity.Tool;
import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import com.prince.ToolEntrySystem.repository.FacilityRepository;
import com.prince.ToolEntrySystem.repository.ToolRepository;
import com.prince.ToolEntrySystem.specification.ToolSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String deleteById(Long id) {
        Tool tool = toolRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tool does not exist"));
        
        toolRepository.deleteById(id);
        
        return "Tool id: " + id + " removed";
    }

    @Transactional
    public ToolResponseDto partialUpdate(Long id, ToolPatchDto toolPatchDto) {
        Tool tool = toolRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tool does not exist, create new."));
        
        if(toolPatchDto.getToolName() != null) tool.setToolName(toolPatchDto.getToolName());
        if(toolPatchDto.getToolType() != null) tool.setToolType(toolPatchDto.getToolType());
        if(toolPatchDto.getQuantity() != null) tool.setQuantity(toolPatchDto.getQuantity());
        if(toolPatchDto.getFacilityId() != null && !toolPatchDto.getFacilityId().equals(tool.getFacility())) {
            Facility facility = facilityRepository.findById(toolPatchDto.getFacilityId())
                    .orElseThrow(() -> new RuntimeException("Facility ID Does not Exist"));
            tool.setFacility(facility);            
        }
        
        Tool saved = toolRepository.save(tool);
        ToolResponseDto responseDto = modelMapper.map(tool, ToolResponseDto.class);
        responseDto.setFacilityId(saved.getFacility().getId());
        return responseDto;        
    }


    public List<ToolResponseDto> searchTools(ToolQueryParamsDto params) {

        Specification<Tool> spec = buildToolSpecification(params);

        List<Tool> tools = toolRepository.findAll(spec);

        return tools.stream()
                .map(tool -> {
                    ToolResponseDto dto = modelMapper.map(tool, ToolResponseDto.class);
                    dto.setFacilityId(tool.getFacility().getId());
                    return dto;
                })
                .toList();
    }
    
    
    public Page<ToolResponseDto> searchToolsWithPagination(ToolQueryParamsDto params) {
               
        Specification<Tool> specs = buildToolSpecification(params);
//        Sorting 
        Sort sort = params.getSortOrder().equalsIgnoreCase("desc")
                ? Sort.by(Sort.Order.desc(params.getSortBy()))
                : Sort.by(Sort.Order.asc(params.getSortBy()));

//        Pagination
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Tool> tools = toolRepository.findAll(specs, pageable);
        
        return tools.map(tool -> {
            ToolResponseDto dto = modelMapper.map(tool, ToolResponseDto.class);
            dto.setFacilityId(tool.getFacility().getId());
            return dto;
        });
    }
    
    public Specification<Tool> buildToolSpecification(ToolQueryParamsDto params){
        ToolType toolTypeEnum = params.getToolType() == null
                ? null
                : ToolType.valueOf(params.getToolType().toUpperCase());

//        ToolStatus toolStatusEnum = params.getStatus() == null
//                ? null
//                : ToolStatus.valueOf(params.getStatus().toUpperCase());

        Specification<Tool> specs = Specification.allOf(
                ToolSpecification.hasToolType(toolTypeEnum),
                ToolSpecification.hasStatus(params.getStatusList()),
                ToolSpecification.hasToolName(params.getToolName()),
                ToolSpecification.hasFacility(params.getFacilityId()),

                ToolSpecification.createdFrom(params.getCreatedFrom()),
                ToolSpecification.createdTo(params.getCreatedTo()),
                ToolSpecification.updatedFrom(params.getUpdatedFrom()),
                ToolSpecification.updatedTo(params.getUpdatedTo())
        );
        return specs;
    }

    public List<ToolResponseDto> getToolByFacilityId(Long facilityId) {
        List<Tool> tools = toolRepository.findByFacilityId(facilityId);
        return tools.stream()
                .map(t -> {
                    ToolResponseDto responseDto = modelMapper.map(t, ToolResponseDto.class);
                    responseDto.setFacilityId(t.getFacility().getId());
                    return responseDto;
                })
                .toList();                
    }
}
