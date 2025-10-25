# Models

## User
Package: `com.example.Aadhaar_Service.Entity.User`

Fields:
- `id` (Long): Auto-generated identifier (from `BaseModel`)
- `createdAt` (LocalDateTime): Creation timestamp (from `BaseModel`)
- `updatedAt` (LocalDateTime): Update timestamp (from `BaseModel`)
- `userName` (String, required)
- `addhaarNumber` (Long, required, unique)
- `phoneNumber` (Long, required, unique)
- `dateOfBirth` (String, required)
- `address` (String, required)

Example JSON:
```json
{
  "id": 1,
  "createdAt": "2025-01-01T12:00:00",
  "updatedAt": "2025-01-02T09:15:00",
  "userName": "Alice",
  "addhaarNumber": 123456789012,
  "phoneNumber": 9876543210,
  "dateOfBirth": "1995-05-10",
  "address": "221B Baker Street"
}
```

Notes:
- Property names reflect the entity fields; note `addhaarNumber` spelling.
- `BaseModel` supplies `id`, `createdAt`, `updatedAt`.

## OtpTransacton
Package: `com.example.Aadhaar_Service.Entity.OtpTransacton`

Fields:
- `id` (Long): Auto-generated identifier (from `BaseModel`)
- `createdAt` (LocalDateTime): Creation timestamp (from `BaseModel`)
- `updatedAt` (LocalDateTime): Update timestamp (from `BaseModel`)
- `transactionId` (String, required, unique)
- `aadhaarNumber` (Long, required)
- `opt` (String): One-time password value (note: field named `opt` in code)
- `isVerfied` (Boolean)
- `expireAt` (LocalDateTime)

Example JSON:
```json
{
  "id": 10,
  "createdAt": "2025-01-03T10:00:00",
  "updatedAt": "2025-01-03T10:01:00",
  "transactionId": "ab12cd34",
  "aadhaarNumber": 123456789012,
  "opt": "563911",
  "isVerfied": false,
  "expireAt": "2025-01-03T10:05:00"
}
```

## BaseModel
Package: `com.example.Aadhaar_Service.Entity.BaseModel`

Fields:
- `id` (Long)
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)
