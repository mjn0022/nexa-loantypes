package com.nexa.loantypes.repository;

import com.nexa.loantypes.entity.LoanType;
import com.nexa.loantypes.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface LoanTypeRepository extends JpaRepository<LoanType, UUID> {
    Optional<LoanType> findByTypeIgnoreCase(String type);
    List<LoanType> findByTypeIgnoreCaseAndStatus(String type, LoanStatus status);
    List<LoanType> findByStatus(LoanStatus status);
}
