package com.saving.service.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DepositRequest {

    private Long accountId;

    @Positive(message = "Diposit amount must be greater than zero")
    private Double amount;
}
