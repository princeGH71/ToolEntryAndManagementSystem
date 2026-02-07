package com.prince.ToolEntrySystem.controller;

import com.prince.ToolEntrySystem.dto.FacilityRequestDto;
import com.prince.ToolEntrySystem.dto.FacilityResponseDto;
import com.prince.ToolEntrySystem.dto.QueryParamsDto;
import com.prince.ToolEntrySystem.service.FacilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facility")
@Slf4j
public class FacilityController {

    private final FacilityService facilityService;

    @PostMapping("/singleEntry")
    public ResponseEntity<FacilityResponseDto> createFacility(@Valid @RequestBody FacilityRequestDto facilityRequestDto){
        FacilityResponseDto facility = facilityService.createFacility(facilityRequestDto);
        log.info(" mapped Entity Facility Code: {}", facility.getFacilityCode());
        return new ResponseEntity<>(facility, HttpStatus.CREATED);
    }

    @PostMapping("/bulkEntry")
    public ResponseEntity<List<FacilityResponseDto>> createFacilityBulk(@Valid @RequestBody
                                                                            List<FacilityRequestDto> facilityRequestDtoList){
        List<FacilityResponseDto> responseDTOs= facilityService.createFacilityBulk(facilityRequestDtoList);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    @GetMapping("/getAllFacility")
    public ResponseEntity<List<FacilityResponseDto>> getAllFacility(){
        List<FacilityResponseDto> responseDtos =facilityService.getAllFacilityDetail();
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @GetMapping("/getFacilityByPage/{pageNumber}")
    public ResponseEntity<List<FacilityResponseDto>> getFacilityByPage(@PathVariable int pageNumber,
                                                                       @RequestParam(defaultValue = "5") int pageSize){
        List<FacilityResponseDto> facilities = facilityService.getFacilityByPage(pageNumber, pageSize);
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FacilityResponseDto>> searchFacilities(@ModelAttribute QueryParamsDto queryParamsDto){
        List<FacilityResponseDto> responseDtos = facilityService.searchFacilities(queryParamsDto);
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
