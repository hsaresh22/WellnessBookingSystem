package com.hs.WellnessBookingSystem.controller;


import com.hs.WellnessBookingSystem.model.User;
import com.hs.WellnessBookingSystem.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller to handle user-related endpoints
@RestController
@RequestMapping("/api/user")
@Tag(name = "User APIs", description = "APIs for viewing managing wellness events users (HR Only)")
public class UserController {

    @Autowired
    private AuthService authService;

    //create api to display all users
    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAllUsers(Authentication auth) {
        return ResponseEntity.ok(authService.getAllUsers(auth));
    }
}
