package com.prince.ToolEntrySystem.entity;

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
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String toolName;

    @Column(nullable = false)
    private String toolType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer quantity;

}
