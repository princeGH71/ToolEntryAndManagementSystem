package com.prince.ToolEntrySystem.repository;

import com.prince.ToolEntrySystem.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ToolRepository extends JpaRepository<Tool, Long>, JpaSpecificationExecutor<Tool>{
}
