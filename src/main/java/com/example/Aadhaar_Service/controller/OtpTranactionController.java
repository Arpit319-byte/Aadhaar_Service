package com.example.Aadhaar_Service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aadhaar_Service.Service.OtpTransactionService;
import com.example.Aadhaar_Service.dto.OtpRequestDTO;
import com.example.Aadhaar_Service.dto.OtpResponseDTO;
import com.example.Aadhaar_Service.dto.OtpTransactionDTO;
import com.example.Aadhaar_Service.dto.OtpVerificationDTO;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/otp")
@Slf4j
public class OtpTranactionController {

    private final OtpTransactionService otpTransactionService;

    public OtpTranactionController(OtpTransactionService otpTransactionService) {
        this.otpTransactionService = otpTransactionService;
        log.info("OtpTransactionController initialized with constructor-based dependency injection");
    }

    // Generate OTP for Aadhaar number
    @PostMapping("/generate")
    public ResponseEntity<OtpResponseDTO> generateOtp(@Valid @RequestBody OtpRequestDTO otpRequestDTO) {
        log.info("Generating OTP for Aadhaar number: " + otpRequestDTO.getAadhaarNumber());
        OtpResponseDTO otpResponseDTO = otpTransactionService.generateOtp(otpRequestDTO);
        return ResponseEntity.ok(otpResponseDTO);
    }

    // Verify OTP with transaction ID
    @PostMapping("/verify")
    public ResponseEntity<OtpResponseDTO> verifyOtp(@Valid @RequestBody OtpVerificationDTO otpVerificationDTO) {
        log.info("Verifying OTP for transaction ID: " + otpVerificationDTO.getTransactionId());
        OtpResponseDTO otpResponseDTO = otpTransactionService.verifyOtp(otpVerificationDTO);
        return ResponseEntity.ok(otpResponseDTO);
    }

    // Get OTP status by transaction ID
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<OtpTransactionDTO> getOtpStatus(@PathVariable String transactionId) {
        log.info("Getting OTP status for transaction ID: " + transactionId);
        OtpTransactionDTO otpTransactionDTO = otpTransactionService.getOtpStatus(transactionId);
        
        if (otpTransactionDTO != null) {
            return ResponseEntity.ok(otpTransactionDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
