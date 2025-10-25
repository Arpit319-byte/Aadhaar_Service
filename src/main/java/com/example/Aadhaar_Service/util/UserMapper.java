package com.example.Aadhaar_Service.util;

import com.example.Aadhaar_Service.Entity.User;
import com.example.Aadhaar_Service.dto.UserRequestDTO;
import com.example.Aadhaar_Service.dto.UserResponseDTO;

/**
 * Utility class for converting between User entity and DTOs
 * This class provides static methods for mapping between different representations
 * of User data to maintain separation of concerns and code reusability.
 */
public class UserMapper {

    /**
     * Converts User entity to UserResponseDTO
     * 
     * @param user the User entity to convert
     * @return UserResponseDTO containing the user data
     */
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserName(user.getUserName());
        responseDTO.setAddhaarNumber(user.getAddhaarNumber());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setDateOfBirth(user.getDateOfBirth());
        responseDTO.setAddress(user.getAddress());
        return responseDTO;
    }

    /**
     * Converts UserRequestDTO to User entity
     * 
     * @param requestDTO the UserRequestDTO to convert
     * @return User entity containing the user data
     */
    public static User toEntity(UserRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        
        User user = new User();
        user.setUserName(requestDTO.getUserName());
        user.setAddhaarNumber(requestDTO.getAddhaarNumber());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        user.setEmail(requestDTO.getEmail());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setAddress(requestDTO.getAddress());
        return user;
    }

    /**
     * Updates an existing User entity with data from UserRequestDTO
     * This method is useful for partial updates where only some fields need to be updated
     * 
     * @param existingUser the existing User entity to update
     * @param requestDTO the UserRequestDTO containing the new data
     * @return the updated User entity
     */
    public static User updateEntity(User existingUser, UserRequestDTO requestDTO) {
        if (existingUser == null || requestDTO == null) {
            return existingUser;
        }
        
        // Update only non-null fields from request DTO
        if (requestDTO.getUserName() != null && !requestDTO.getUserName().trim().isEmpty()) {
            existingUser.setUserName(requestDTO.getUserName());
        }
        
        if (requestDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(requestDTO.getPhoneNumber());
        }
        
        if (requestDTO.getEmail() != null) {
            existingUser.setEmail(requestDTO.getEmail());
        }
        
        if (requestDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(requestDTO.getDateOfBirth());
        }
        
        if (requestDTO.getAddress() != null) {
            existingUser.setAddress(requestDTO.getAddress());
        }
        
        // Note: Aadhaar number is intentionally not updated as it should be immutable
        
        return existingUser;
    }
}
