package com.saving.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class SavingRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Pattern(regexp = "[0-9]{10}",
            message = "Message numbers must be 10 digits"
    )
    private String mobile;

    @Min(value = 5000,
            message = "Minimum balance should be 5000" )
    private Double initialDeposit;

    public String getCustomerName() {
        return customerName;
    }
}
