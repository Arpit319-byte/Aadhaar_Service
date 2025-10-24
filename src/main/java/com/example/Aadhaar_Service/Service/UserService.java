package com.example.Aadhaar_Service.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Aadhaar_Service.Entity.User;
import com.example.Aadhaar_Service.repository.UserRepository;

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


    public User createUser(@Valid User user){
        log.info("Creating the User in the database");
        
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
        
        return userRepository.save(user);
    }


    public User updateUser(User incoming, Long id){
        log.info("Updating user with id: " + id);
        
        return userRepository.findById(id).map(existing -> {
            // Update only provided fields - safe partial updates
            if (incoming.getUserName() != null && !incoming.getUserName().trim().isEmpty()) {
                existing.setUserName(incoming.getUserName());
                log.info("Updated user name for id: " + id);
            }
            
            if (incoming.getPhoneNumber() != null) {
                existing.setPhoneNumber(incoming.getPhoneNumber());
                log.info("Updated phone number for id: " + id);
            }
            
            // Aadhaar number is immutable - never update it
            log.info("User updated successfully for id: " + id);
            return userRepository.save(existing);
            
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


    public User getUser(Long id){
        log.info("Finding the user by the given id: " + id);
        if (id == null || id <= 0) {
            log.warn("Invalid user ID provided: " + id);
            return null;
        }
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        log.info("Getting all the user from the database");
        return userRepository.findAll();
    }


    public User getByAadhaarNumber(String aadhaarNumber){
        log.info("Finding user by Aadhaar number: " + aadhaarNumber);
        return userRepository.findByAddhaarNumber(aadhaarNumber);
    }

    public User getByPhoneNumber(String phoneNumber){
        log.info("Finding user by phone number: " + phoneNumber);
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public List<User> searchByUserName(String namePart){
        log.info("Searching users by name: " + namePart);
        if (namePart == null || namePart.trim().isEmpty()) {
            log.warn("Empty search term provided");
            return List.of(); // Return empty list for empty search
        }
        return userRepository.findByUserNameContainingIgnoreCase(namePart.trim());
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

    public List<User> getAllUsersSortedByName(){
        log.info("Getting all users sorted by name");
        return userRepository.findAllByOrderByUserNameAsc();
    }
    
}
