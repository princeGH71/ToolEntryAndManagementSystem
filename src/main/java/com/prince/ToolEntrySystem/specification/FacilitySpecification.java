package com.prince.ToolEntrySystem.specification;

import com.prince.ToolEntrySystem.entity.Facility;
import com.prince.ToolEntrySystem.enums.SiteLocation;
import com.prince.ToolEntrySystem.enums.SiteType;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.jpa.domain.Specification;

public class FacilitySpecification {


//    filter facility by site Location
    public static Specification<Facility> hasSiteLocation(SiteLocation siteLocation){
        return ((root, query, criteriaBuilder) -> {
           if(siteLocation == null){
               return null;
           }
           return criteriaBuilder.equal(criteriaBuilder.upper(root.get("siteLocation").as(String.class)), siteLocation.name().toUpperCase());
        });
    }

//    filter Facility by site Type
    public static Specification<Facility> hasSiteType(SiteType siteType){
        return (root, query, criteriaBuilder) ->
                siteType == null ? null : criteriaBuilder.equal(root.get("siteType"), siteType);
    }

//    filter by facility code, partial match (case Insensitive)
    public static Specification<Facility> hasFacilityCode(String facilityCode){
        return ((root, query, criteriaBuilder) -> facilityCode == null || facilityCode.isBlank() ? null :
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("facilityCode")), "%" + facilityCode.toLowerCase() + "%")
                );
    }

//    filter by isActive
    public static Specification<Facility> isActive(Boolean isActive){
        return ((root, query, criteriaBuilder) -> isActive == null ? null :
                criteriaBuilder.equal(root.get("isActive"), isActive)
                );
    }
}
