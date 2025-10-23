package com.example.Aadhaar_Service.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="OtpTransaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class OtpTransacton extends BaseModel {


    @Column(nullable=false,unique=true)
    private String transactionId;

    @Column(nullable=false)
    private Long aadhaarNumber;

    private String opt;

    private Boolean isVerfied;

    private LocalDateTime expireAt;
    
}
