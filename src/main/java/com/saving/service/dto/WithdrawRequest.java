package com.saving.service.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WithdrawRequest {

    private Long accountId;

    @Positive(message = "Withdraw amount must be greater tha zero")
    private Double amount;
}
