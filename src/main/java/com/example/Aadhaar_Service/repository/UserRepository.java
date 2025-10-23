package com.example.Aadhaar_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Aadhaar_Service.Entity.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByAddhaarNumber(Long addhaarNumber);
    boolean existsByAddhaarNumber(Long addhaarNumber);

    User findByPhoneNumber(Long phoneNumber);
    boolean existsByPhoneNumber(Long phoneNumber);

    List<User> findByUserNameContainingIgnoreCase(String namePart);
    List<User> findAllByOrderByUserNameAsc();
}
