package com.nexa.loantypes.dto;

import com.nexa.loantypes.enums.LoanStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLoanTypeRequest {
    @NotNull
    private UUID loanTypeId;

    @Size(max = 255)          // optional; if provided, will be validated for length
    private String description;

    private LoanStatus status; // optional; enum restricts allowed values
}
