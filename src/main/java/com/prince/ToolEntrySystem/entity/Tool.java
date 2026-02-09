package com.prince.ToolEntrySystem.entity;

import com.prince.ToolEntrySystem.enums.ToolStatus;
import com.prince.ToolEntrySystem.enums.ToolType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tools")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Tool extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toolName;

    @Column(nullable = false)
    private ToolType toolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(nullable = false)
    private ToolStatus status;

    @Column(nullable = false)
    private Integer quantity;

}
