package com.example.Aadhaar_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Aadhaar_Service.Entity.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByAddhaarNumber(String addhaarNumber);
    boolean existsByAddhaarNumber(String addhaarNumber);

    User findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    User findByEmail(String email);
    boolean existsByEmail(String email);

    List<User> findByUserNameContainingIgnoreCase(String namePart);
    List<User> findAllByOrderByUserNameAsc();
}
