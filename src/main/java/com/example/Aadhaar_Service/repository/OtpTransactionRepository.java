package com.example.Aadhaar_Service.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Aadhaar_Service.Entity.OtpTransaction;

public interface OtpTransactionRepository extends JpaRepository<OtpTransaction,Long>{
    
    // Find OTP transaction by transaction ID
    Optional<OtpTransaction> findByTransactionId(String transactionId);
    
    // Find OTP transaction by Aadhaar number
    Optional<OtpTransaction> findByAadhaarNumber(Long aadhaarNumber);
}
