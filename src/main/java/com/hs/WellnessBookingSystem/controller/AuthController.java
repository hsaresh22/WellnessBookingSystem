package com.hs.WellnessBookingSystem.controller;

import com.hs.WellnessBookingSystem.dto.CreateUserRequest;
import com.hs.WellnessBookingSystem.model.User;
import com.hs.WellnessBookingSystem.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller to handle authentication-related endpoints
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs", description = "APIs for registering system users")
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(authService.createUser(req));
    }
}
