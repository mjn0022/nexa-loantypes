package com.nexa.loantypes.entity;

import com.nexa.loantypes.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "loan_types", schema = "loan",
        uniqueConstraints = @UniqueConstraint(name = "uk_loan_types_type", columnNames = "loan_type"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LoanType {

    @Id
    @Column(name = "loan_type_id", nullable = false, updatable = false)
    private UUID loanTypeId;

    @PrePersist
    void prePersist() {
        if (loanTypeId == null) loanTypeId = UUID.randomUUID();
        if (status == null) status = LoanStatus.active;
    }

    @Column(name = "loan_type", nullable = false, length = 100)
    private String type;

    @Column(name = "loan_description", nullable = false, length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false, length = 8)
    private LoanStatus status;
}
