# Services

## UserService
Package: `com.example.Aadhaar_Service.Service.UserService`

Public methods:
- `void createUser(User user)`
  - Persists a new `User`.
- `User updateUser(User incoming, Long id)`
  - Applies partial update to `userName` and `phoneNumber` for user with `id`. Aadhaar is immutable. Throws `RuntimeException` if not found.
- `boolean deleteUser(User user)`
  - Deletes by provided entity's id. Returns false if not exists.
- `User getUser(Long id)`
  - Returns user by id or null.
- `List<User> getAllUsers()`
  - Returns all users.
- `User getByAadhaarNumber(Long aadhaarNumber)`
  - Returns user by Aadhaar.
- `User getByPhoneNumber(Long phoneNumber)`
  - Returns user by phone.
- `List<User> searchByUserName(String namePart)`
  - Case-insensitive contains search by `userName`.
- `boolean isAadhaarTaken(Long aadhaarNumber)`
  - Checks existence by Aadhaar.
- `boolean isPhoneTaken(Long phoneNumber)`
  - Checks existence by phone.
- `boolean deleteById(Long id)`
  - Deletes by id if exists; otherwise returns false.
- `List<User> getAllUsersSortedByName()`
  - Returns users sorted by `userName` ascending.

Notes:
- The last duplicate `getByAadhaarNumber(Long)` method appears incomplete in code; not used by controller.

### Usage example
```java
UserService userService = // obtain via Spring injection
User user = new User();
user.setUserName("Alice");
user.setAddhaarNumber(123456789012L);
user.setPhoneNumber(9876543210L);
user.setDateOfBirth("1995-05-10");
user.setAddress("221B Baker Street");
userService.createUser(user);

User fetched = userService.getByAadhaarNumber(123456789012L);
```
