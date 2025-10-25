package com.example.Aadhaar_Service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aadhaar_Service.Entity.User;
import com.example.Aadhaar_Service.Service.UserService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        log.info("UserController initialized with constructor-based dependency injection");
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        log.info("Get user by the userId: " + id);
        User user = userService.getUser(id);

        if(user != null){
            log.info("User found with userId: " + id);
            return ResponseEntity.ok(user);
        } else {
            log.info("User not found with id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    // Get user by Aadhaar number
    @GetMapping("/aadhaar/{aadhaarNumber}")
    public ResponseEntity<User> getUserByAadhaarNumber(@PathVariable String aadhaarNumber){
        log.info("Finding user by Aadhaar number: " + aadhaarNumber);
        User user = userService.getByAadhaarNumber(aadhaarNumber);

        if(user != null){
            log.info("User found with Aadhaar number: " + aadhaarNumber);
            return ResponseEntity.ok(user);
        } else {
            log.info("No user found with Aadhaar number: " + aadhaarNumber);
            return ResponseEntity.notFound().build();
        }
    }

    // Get user by phone number
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phoneNumber){
        log.info("Finding user by phone number: " + phoneNumber);
        User user = userService.getByPhoneNumber(phoneNumber);

        if(user != null){
            log.info("User found with phone number: " + phoneNumber);
            return ResponseEntity.ok(user);
        } else {
            log.info("No user found with phone number: " + phoneNumber);
            return ResponseEntity.notFound().build();
        }
    }

    // Search users by name
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name){
        log.info("Searching users by name: " + name);
        List<User> users = userService.searchByUserName(name);
        return ResponseEntity.ok(users);
    }

    // Get all users sorted by name
    @GetMapping("/sorted")
    public ResponseEntity<List<User>> getAllUsersSorted(){
        log.info("Getting all users sorted by name");
        List<User> users = userService.getAllUsersSortedByName();
        return ResponseEntity.ok(users);
    }

    // Check if Aadhaar exists
    @GetMapping("/check-aadhaar")
    public ResponseEntity<Boolean> checkAadhaarExists(@RequestParam String aadhaarNumber){
        log.info("Checking if Aadhaar exists: " + aadhaarNumber);
        boolean exists = userService.isAadhaarTaken(aadhaarNumber);
        return ResponseEntity.ok(exists);
    }

    // Check if phone exists
    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneExists(@RequestParam String phoneNumber){
        log.info("Checking if phone exists: " + phoneNumber);
        boolean exists = userService.isPhoneTaken(phoneNumber);
        return ResponseEntity.ok(exists);
    }

    // Create new user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        log.info("Creating new user");
        User createdUser = userService.createUser(user);
        log.info("User created successfully: " + createdUser.toString());
        return ResponseEntity.ok(createdUser);
    }

    
    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user){
        log.info("Updating user with ID: " + id);
        User updatedUser = userService.updateUser(user, id);

        if(updatedUser != null){
            log.info("User updated successfully with ID: " + id);
            return ResponseEntity.ok(updatedUser);
        } else {
            log.info("User not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        log.info("Deleting user with ID: " + id);
        boolean deleted = userService.deleteById(id);

        if(deleted){
            log.info("User deleted successfully with ID: " + id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("User not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user by Aadhaar number
    @DeleteMapping("/aadhaar/{aadhaarNumber}")
    public ResponseEntity<Void> deleteUserByAadhaar(@PathVariable String aadhaarNumber){
        log.info("Deleting user with Aadhaar number: " + aadhaarNumber);
        User user = userService.getByAadhaarNumber(aadhaarNumber);
        
        if(user != null){
            boolean deleted = userService.deleteById(user.getId());
            if(deleted){
                log.info("User deleted successfully with Aadhaar: " + aadhaarNumber);
                return ResponseEntity.noContent().build();
            }
        }
        
        log.info("User not found with Aadhaar number: " + aadhaarNumber);
        return ResponseEntity.notFound().build();
    }

}
