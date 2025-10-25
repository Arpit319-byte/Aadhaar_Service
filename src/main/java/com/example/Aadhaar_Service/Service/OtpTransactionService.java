package com.example.Aadhaar_Service.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.Aadhaar_Service.Entity.OtpTransaction;
import com.example.Aadhaar_Service.Entity.User;
import com.example.Aadhaar_Service.dto.OtpRequestDTO;
import com.example.Aadhaar_Service.dto.OtpResponseDTO;
import com.example.Aadhaar_Service.dto.OtpTransactionDTO;
import com.example.Aadhaar_Service.dto.OtpVerificationDTO;
import com.example.Aadhaar_Service.repository.OtpTransactionRepository;
import com.example.Aadhaar_Service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtpTransactionService {

    private final OtpTransactionRepository otpTransactionRepository;
    private final UserRepository userRepository;

    public OtpTransactionService(OtpTransactionRepository otpTransactionRepository, UserRepository userRepository) {
        this.otpTransactionRepository = otpTransactionRepository;
        this.userRepository = userRepository;
        log.info("OtpTransactionService initialized with constructor-based dependency injection");
    }

    // Generate OTP for Aadhaar number
    public OtpResponseDTO generateOtp(OtpRequestDTO otpRequestDTO) {
        log.info("Generating OTP for Aadhaar number: " + otpRequestDTO.getAadhaarNumber());
        
        // Find user by Aadhaar number
        User user = userRepository.findByAddhaarNumber(otpRequestDTO.getAadhaarNumber());
        if (user == null) {
            log.warn("User not found for Aadhaar number: " + otpRequestDTO.getAadhaarNumber());
            return OtpResponseDTO.builder()
                .status("FAILED")
                .message("Aadhaar number not found")
                .build();
        }

        // Generate transaction ID and OTP
        String transactionId = generateTransactionId();
        String otp = generateOtp();
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(5); // 5 minutes expiry

        // Create OTP transaction
        OtpTransaction otpTransaction = new OtpTransaction();
        otpTransaction.setTransactionId(transactionId);
        otpTransaction.setAadhaarNumber(Long.parseLong(otpRequestDTO.getAadhaarNumber()));
        otpTransaction.setOpt(otp);
        otpTransaction.setIsVerfied(false);
        otpTransaction.setExpireAt(expireAt);

        // Save to database
        otpTransactionRepository.save(otpTransaction);

        // TODO: Send SMS to user's phone number
        // smsService.sendOtp(user.getPhoneNumber(), otp);

        log.info("OTP generated successfully for transaction ID: " + transactionId);
        
        return OtpResponseDTO.builder()
            .transactionId(transactionId)
            .aadhaarNumber(Long.parseLong(otpRequestDTO.getAadhaarNumber()))
            .isVerfied(false)
            .message("OTP sent to registered phone number")
            .status("SUCCESS")
            .build();
    }

    // Verify OTP with transaction ID
    public OtpResponseDTO verifyOtp(OtpVerificationDTO otpVerificationDTO) {
        log.info("Verifying OTP for transaction ID: " + otpVerificationDTO.getTransactionId());
        
        // Find transaction by ID
        Optional<OtpTransaction> transactionOpt = otpTransactionRepository.findByTransactionId(otpVerificationDTO.getTransactionId());
        
        if (transactionOpt.isEmpty()) {
            log.warn("Transaction not found: " + otpVerificationDTO.getTransactionId());
            return OtpResponseDTO.builder()
                .status("FAILED")
                .message("Invalid transaction ID")
                .build();
        }

        OtpTransaction transaction = transactionOpt.get();

        // Check if OTP is expired
        if (transaction.getExpireAt().isBefore(LocalDateTime.now())) {
            log.warn("OTP expired for transaction: " + otpVerificationDTO.getTransactionId());
            return OtpResponseDTO.builder()
                .status("FAILED")
                .message("OTP has expired")
                .build();
        }

        // Check if already verified
        if (transaction.getIsVerfied()) {
            log.warn("OTP already verified for transaction: " + otpVerificationDTO.getTransactionId());
            return OtpResponseDTO.builder()
                .status("FAILED")
                .message("OTP already verified")
                .build();
        }

        // Verify OTP
        if (transaction.getOpt().equals(otpVerificationDTO.getOtp())) {
            // Update verification status
            transaction.setIsVerfied(true);
            otpTransactionRepository.save(transaction);
            
            log.info("OTP verified successfully for transaction: " + otpVerificationDTO.getTransactionId());
            return OtpResponseDTO.builder()
                .transactionId(transaction.getTransactionId())
                .aadhaarNumber(transaction.getAadhaarNumber())
                .isVerfied(true)
                .message("OTP verified successfully")
                .status("SUCCESS")
                .build();
        } else {
            log.warn("Invalid OTP for transaction: " + otpVerificationDTO.getTransactionId());
            return OtpResponseDTO.builder()
                .status("FAILED")
                .message("Invalid OTP")
                .build();
        }
    }

    // Get OTP status by transaction ID
    public OtpTransactionDTO getOtpStatus(String transactionId) {
        log.info("Getting OTP status for transaction ID: " + transactionId);
        
        Optional<OtpTransaction> transactionOpt = otpTransactionRepository.findByTransactionId(transactionId);
        
        if (transactionOpt.isEmpty()) {
            log.warn("Transaction not found: " + transactionId);
            return null;
        }

        OtpTransaction transaction = transactionOpt.get();
        
        return OtpTransactionDTO.builder()
            .transactionId(transaction.getTransactionId())
            .aadhaarNumber(transaction.getAadhaarNumber())
            .opt(transaction.getOpt())
            .isVerfied(transaction.getIsVerfied())
            .expireAt(transaction.getExpireAt())
            .build();
    }

    // Generate unique transaction ID
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + new Random().nextInt(1000);
    }

    // Generate 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
