package com.nexa.loantypes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoanTypeRequest {
    @NotBlank(message="loan type is required") @Size(max = 100)
    private String type;

    @NotBlank(message="loan description can not be empty") @Size(max = 255)
    private String description;
}
