package com.prince.ToolEntrySystem.specification;

import com.prince.ToolEntrySystem.entity.Tool;
import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ToolSpecification {
    
    public static Specification<Tool> hasToolType(ToolType toolType){
        return (root, query, criteriaBuilder) -> 
                toolType == null ? null : criteriaBuilder.equal(root.get("toolType"), toolType);
    }
    
    public static Specification<Tool> hasStatus(List<ToolStatus> statusList){
        return (root, query, criteriaBuilder) ->
                (statusList == null || statusList.isEmpty())
                ? null
                        : root.get("status").in(statusList);
    }
    
    public static Specification<Tool> hasToolName(String toolName){
        return (root, query, criteriaBuilder) -> {
            
            if (toolName == null || toolName.isBlank()) {
                return null;
            }

            String name = toolName.trim().toLowerCase();

            //        Case 1: Widcard search if contains "*"
            if (name.contains("*")) {
                String pattern = name.replace("*", "%");
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("toolName")), pattern);
            }
            
//            Case 2: Exact match, case insensitive
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("toolName")), name.toLowerCase());
        };
    }
    
    public static Specification<Tool> hasFacility(Long facilityId){
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("facility").get("id"), facilityId);
    }
    
    public static Specification<Tool> createdFrom(java.time.LocalDateTime from){
        return (root, query, criteriaBuilder) -> 
                from == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), from);
    }
    
    public static Specification<Tool> createdTo(java.time.LocalDateTime to){
        return (root, query, criteriaBuilder) ->
                to == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), to);
    }

    public static Specification<Tool> updatedFrom(java.time.LocalDateTime from){
        return (root, query, criteriaBuilder) ->
                from == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("createDate"), from);
    }

    public static Specification<Tool> updatedTo(java.time.LocalDateTime to){
        return (root, query, criteriaBuilder) ->
                to == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("createDate"), to);
    }
}
