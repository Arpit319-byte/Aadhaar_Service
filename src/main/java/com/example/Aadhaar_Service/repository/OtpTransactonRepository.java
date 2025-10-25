package com.example.Aadhaar_Service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Aadhaar_Service.Entity.OtpTransaction;


public interface OtpTransactonRepository extends JpaRepository<OtpTransaction,Long>{
    
}
