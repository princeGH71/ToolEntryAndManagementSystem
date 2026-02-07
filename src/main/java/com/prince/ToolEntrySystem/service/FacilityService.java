package com.prince.ToolEntrySystem.service;

import com.prince.ToolEntrySystem.dto.FacilityRequestDto;
import com.prince.ToolEntrySystem.dto.FacilityResponseDto;
import com.prince.ToolEntrySystem.dto.QueryParamsDto;
import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.enums.SiteLocation;
import com.prince.ToolEntrySystem.enums.SiteType;
import com.prince.ToolEntrySystem.repository.FacilityRepository;
import com.prince.ToolEntrySystem.specification.FacilitySpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FacilityService {

    private final ModelMapper modelMapper;
    private final FacilityRepository facilityRepository;

    public FacilityResponseDto createFacility(FacilityRequestDto facilityRequestDto) {
        log.info("Incoming DTO Facility code: {}", facilityRequestDto.getFacilityCode());
        Facility facility = modelMapper.map(facilityRequestDto, Facility.class);
        log.info("mapped entity facility code : {}", facility.getFacilityCode());

        if(facilityRepository.existsByFacilityCode(facility.getFacilityCode())){
            throw new RuntimeException("Facility already exits " + facility.getFacilityCode());
        }
        Facility saved = facilityRepository.save(facility);

        return modelMapper.map(saved, FacilityResponseDto.class);
    }


    public List<FacilityResponseDto> createFacilityBulk(List<FacilityRequestDto> facilityRequestDtoList) {
        List<Facility> facilitiesToSave = new ArrayList<>();
        List<String> duplicateCodes = new ArrayList<>();
//        Khudse : multiple DB calls(for code existence) ki jgah sare facilitycodes pehle hi leliye, ab inse compare easy hojaega
        Set<String> existingFacilityCodes = facilityRepository.findAllFacilityCode();

        for(FacilityRequestDto dto: facilityRequestDtoList){
            if(existingFacilityCodes.contains(dto.getFacilityCode())){
                duplicateCodes.add(dto.getFacilityCode());
                continue;
            }
            Facility facility = modelMapper.map(dto, Facility.class);
            facilitiesToSave.add(facility);
        }

//      save all valid facilities
        List<Facility>savedFacilities = facilityRepository.saveAll(facilitiesToSave);

//        create response dto for each facility saved
        List<FacilityResponseDto> responseDtos = savedFacilities.stream()
                                                .map(f -> modelMapper.map(f, FacilityResponseDto.class))
                                                .toList();


        if(!duplicateCodes.isEmpty()){
            log.info("Skipped {} duplicate code entries: {}", duplicateCodes.size(), duplicateCodes);
        }

        return responseDtos;
    }

    public List<FacilityResponseDto> getAllFacilityDetail() {
        List<Facility> facilities = facilityRepository.findAll();
        log.info("Fetched all facilities");
        List<FacilityResponseDto> responseDtos = facilities.stream()
                .map(f -> modelMapper.map(f, FacilityResponseDto.class))
                .toList();
        log.info("Mapped all facilities to FacilityResponseDtos");
        return responseDtos;
    }

    public List<FacilityResponseDto> getFacilityByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<Facility> facilityPage = facilityRepository.findAll(pageable);

        return facilityPage.getContent()
                .stream()
                .map(facility -> modelMapper.map(facility, FacilityResponseDto.class))
                .toList();
    }

    public List<FacilityResponseDto> searchFacilities(QueryParamsDto queryParams) {
        SiteLocation siteLocationEnum = queryParams.getSiteLocation() == null 
                ? null : SiteLocation.valueOf(queryParams.getSiteLocation().toUpperCase());
        
        SiteType siteTypeEnum = queryParams.getSiteType() == null
                ? null : SiteType.valueOf(queryParams.getSiteType().toUpperCase());

        Specification<Facility> specification = Specification.allOf(
                FacilitySpecification.hasSiteLocation(siteLocationEnum),
                FacilitySpecification.hasSiteType(siteTypeEnum),
                FacilitySpecification.hasFacilityCode(queryParams.getFacilityCode()),
                FacilitySpecification.isActive(queryParams.getIsActive())
        );
        
        List<Facility> facilities = facilityRepository.findAll(specification);
        
        List<FacilityResponseDto> responseDtos = facilities.stream()
                .map(f -> modelMapper.map(f, FacilityResponseDto.class)).toList();
        
        return responseDtos;
    }
}
