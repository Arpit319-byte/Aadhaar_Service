package com.example.Aadhaar_Service.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpTransactionDTO {

    @NotNull(message = "Transaction ID is required")
    private String transactionId;

    @NotNull(message = "Aadhaar number is required")
    private Long aadhaarNumber;

    @NotNull(message = "OTP is required")
    private String opt;

    @NotNull(message = "Verification status is required")
    private Boolean isVerfied;

    @NotNull(message = "Expiration time is required")
    private LocalDateTime expireAt;
}
