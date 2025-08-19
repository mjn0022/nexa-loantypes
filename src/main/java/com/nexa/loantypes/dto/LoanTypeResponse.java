package com.nexa.loantypes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nexa.loantypes.enums.LoanStatus;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanTypeResponse {
    private UUID loanTypeId;
    private String type;
    private String description;
    private LoanStatus status;
}
