package com.example.Aadhaar_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponseDTO {

    private String transactionId;
    private Long aadhaarNumber;
    private Boolean isVerfied;
    private String message;
    private String status;
}
