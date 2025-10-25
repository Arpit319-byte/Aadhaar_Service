package com.example.Aadhaar_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String userName;
    private String addhaarNumber;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    private String address;
}
