# Repositories

## UserRepository
Extends: `JpaRepository<User, Long>`
Package: `com.example.Aadhaar_Service.repository.UserRepository`

Derived query methods:
- `User findByAddhaarNumber(Long addhaarNumber)`
- `boolean existsByAddhaarNumber(Long addhaarNumber)`
- `User findByPhoneNumber(Long phoneNumber)`
- `boolean existsByPhoneNumber(Long phoneNumber)`
- `List<User> findByUserNameContainingIgnoreCase(String namePart)`
- `List<User> findAllByOrderByUserNameAsc()`

### Usage example
```java
@Autowired UserRepository userRepository;
boolean exists = userRepository.existsByAddhaarNumber(123456789012L);
List<User> results = userRepository.findByUserNameContainingIgnoreCase("an");
```

## OtpTransactonRepository
- The file exists (`repository/OtpTransactonRepository.java`) but is empty in this codebase snapshot.
