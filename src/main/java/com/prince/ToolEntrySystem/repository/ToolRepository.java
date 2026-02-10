package com.prince.ToolEntrySystem.repository;

import com.prince.ToolEntrySystem.dto.ToolResponseDto;
import com.prince.ToolEntrySystem.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long>, JpaSpecificationExecutor<Tool>{
    List<Tool> findByFacilityId(Long facilityId);
    
}
