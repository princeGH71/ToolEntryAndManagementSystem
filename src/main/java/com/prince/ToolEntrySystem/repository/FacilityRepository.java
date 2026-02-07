package com.prince.ToolEntrySystem.repository;

import com.prince.ToolEntrySystem.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long>, JpaSpecificationExecutor<Facility> {

    boolean existsByFacilityCode(String facilityCode);

    @Query("Select f.facilityCode From Facility f")
    Set<String> findAllFacilityCode();
}
