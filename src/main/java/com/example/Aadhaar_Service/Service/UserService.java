package com.example.Aadhaar_Service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Aadhaar_Service.Entity.User;
import com.example.Aadhaar_Service.repository.UserRepository;
import com.example.Aadhaar_Service.dto.UserRequestDTO;
import com.example.Aadhaar_Service.dto.UserResponseDTO;
import com.example.Aadhaar_Service.util.UserMapper;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        log.info("UserService initialized with constructor-based dependency injection");
    }


    public UserResponseDTO createUser(@Valid UserRequestDTO userRequestDTO){
        log.info("Creating the User in the database");
        
        // Convert DTO to Entity
        User user = UserMapper.toEntity(userRequestDTO);
        
        // Check for duplicates (business logic validation)
        if (isAadhaarTaken(user.getAddhaarNumber())) {
            throw new IllegalArgumentException("Aadhaar number already exists");
        }
        if (isPhoneTaken(user.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        if (isEmailTaken(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        User savedUser = userRepository.save(user);
        return UserMapper.toResponseDTO(savedUser);
    }


    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, Long id){
        log.info("Updating user with id: " + id);
        
        return userRepository.findById(id).map(existing -> {
            // Update only provided fields - safe partial updates using utility method
            User updatedUser = UserMapper.updateEntity(existing, userRequestDTO);
            log.info("User updated successfully for id: " + id);
            User savedUser = userRepository.save(updatedUser);
            return UserMapper.toResponseDTO(savedUser);
            
        }).orElseThrow(() -> {
            log.warn("User with id " + id + " not found, cannot update");
            return new RuntimeException("User not found with id: " + id);
        });
    }

    public boolean deleteUser(User user){
       
        if(!userRepository.existsById(user.getId())){
           log.info("User with the given Id "+user.getId()+" does not exist hence unable to delete the user");
           return false;
        }

        log.info("User with the given Id "+user.getId()+" exist and hence deleting the user");
        userRepository.deleteById(user.getId());
        return true;
    
    }


    public UserResponseDTO getUser(Long id){
        log.info("Finding the user by the given id: " + id);
        if (id == null || id <= 0) {
            log.warn("Invalid user ID provided: " + id);
            return null;
        }
        User user = userRepository.findById(id).orElse(null);
        return UserMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> getAllUsers(){
        log.info("Getting all the user from the database");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }


    public UserResponseDTO getByAadhaarNumber(String aadhaarNumber){
        log.info("Finding user by Aadhaar number: " + aadhaarNumber);
        User user = userRepository.findByAddhaarNumber(aadhaarNumber);
        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO getByPhoneNumber(String phoneNumber){
        log.info("Finding user by phone number: " + phoneNumber);
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return UserMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> searchByUserName(String namePart){
        log.info("Searching users by name: " + namePart);
        if (namePart == null || namePart.trim().isEmpty()) {
            log.warn("Empty search term provided");
            return List.of(); // Return empty list for empty search
        }
        List<User> users = userRepository.findByUserNameContainingIgnoreCase(namePart.trim());
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }

    public boolean isAadhaarTaken(String aadhaarNumber){
        log.info("Checking if Aadhaar is taken: " + aadhaarNumber);
        if (aadhaarNumber == null || aadhaarNumber.trim().isEmpty()) {
            log.warn("Null or empty Aadhaar number provided");
            return false;
        }
        return userRepository.existsByAddhaarNumber(aadhaarNumber);
    }

    public boolean isPhoneTaken(String phoneNumber){
        log.info("Checking if phone is taken: " + phoneNumber);
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            log.warn("Null or empty phone number provided");
            return false;
        }
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean isEmailTaken(String email){
        log.info("Checking if email is taken: " + email);
        if (email == null || email.trim().isEmpty()) {
            log.warn("Null or empty email provided");
            return false;
        }
        return userRepository.existsByEmail(email);
    }

    public boolean deleteById(Long id){
        log.info("Deleting user by id: " + id);
        if(!userRepository.existsById(id)){
            log.info("User with id " + id + " does not exist");
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public List<UserResponseDTO> getAllUsersSortedByName(){
        log.info("Getting all users sorted by name");
        List<User> users = userRepository.findAllByOrderByUserNameAsc();
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .toList();
    }
    
}
