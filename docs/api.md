# REST API

Base path prefix for user endpoints: `/api/v1/user`

## Users

### GET /api/v1/user/{id}
- Description: Get a user by ID
- Path params: `id` (Long)
- Response: 200 with `User` JSON, or 404 if not found

Example:
```bash
echo "GET user 1" && curl -s http://localhost:8080/api/v1/user/1 | jq .
```

### GET /api/v1/user
- Description: List all users
- Response: 200 with `User[]`

Example:
```bash
curl -s http://localhost:8080/api/v1/user | jq .
```

### GET /api/v1/user/aadhaar/{aadhaarNumber}
- Description: Get user by Aadhaar number
- Path params: `aadhaarNumber` (Long)
- Response: 200 with `User` or 404

Example:
```bash
curl -s http://localhost:8080/api/v1/user/aadhaar/123456789012 | jq .
```

### GET /api/v1/user/phone/{phoneNumber}
- Description: Get user by phone number
- Path params: `phoneNumber` (Long)
- Response: 200 with `User` or 404

Example:
```bash
curl -s http://localhost:8080/api/v1/user/phone/9876543210 | jq .
```

### GET /api/v1/user/search?name=... 
- Description: Search users by name (case-insensitive, substring)
- Query params: `name` (String)
- Response: 200 with `User[]`

Example:
```bash
curl -s "http://localhost:8080/api/v1/user/search?name=an" | jq .
```

### GET /api/v1/user/sorted
- Description: List all users sorted by `userName` ascending
- Response: 200 with `User[]`

Example:
```bash
curl -s http://localhost:8080/api/v1/user/sorted | jq .
```

### GET /api/v1/user/check-aadhaar?aadhaarNumber=...
- Description: Check if Aadhaar number exists
- Query params: `aadhaarNumber` (Long)
- Response: 200 with `Boolean`

Example:
```bash
curl -s "http://localhost:8080/api/v1/user/check-aadhaar?aadhaarNumber=123456789012"
```

### GET /api/v1/user/check-phone?phoneNumber=...
- Description: Check if phone number exists
- Query params: `phoneNumber` (Long)
- Response: 200 with `Boolean`

Example:
```bash
curl -s "http://localhost:8080/api/v1/user/check-phone?phoneNumber=9876543210"
```

### POST /api/v1/user
- Description: Create a new user
- Request body: `User`
- Response: 200 with created `User`

Example:
```bash
curl -s -X POST http://localhost:8080/api/v1/user \
  -H 'Content-Type: application/json' \
  -d '{
    "userName": "Alice",
    "addhaarNumber": 123456789012,
    "phoneNumber": 9876543210,
    "dateOfBirth": "1995-05-10",
    "address": "221B Baker Street"
  }' | jq .
```

### PUT /api/v1/user/{id}
- Description: Update an existing user (partial update; Aadhaar is immutable)
- Path params: `id` (Long)
- Request body: `User` with fields to update (server updates name/phone only)
- Response: 200 with updated `User` or 404

Example:
```bash
curl -s -X PUT http://localhost:8080/api/v1/user/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "userName": "Alice B",
    "phoneNumber": 9898989898
  }' | jq .
```

### DELETE /api/v1/user/{id}
- Description: Delete user by ID
- Response: 204 on success, 404 if not found

Example:
```bash
curl -i -X DELETE http://localhost:8080/api/v1/user/1
```

### DELETE /api/v1/user/aadhaar/{aadhaarNumber}
- Description: Delete user by Aadhaar number
- Path params: `aadhaarNumber` (Long)
- Response: 204 on success, 404 if not found

Example:
```bash
curl -i -X DELETE http://localhost:8080/api/v1/user/aadhaar/123456789012
```

## Notes
- All responses are JSON.
- Errors return standard HTTP statuses; 404 when a resource is not found.
- The `User` schema is defined in [models.md](./models.md).
