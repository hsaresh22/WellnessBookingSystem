package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.dto.CreateUserRequest;
import com.hs.WellnessBookingSystem.model.User;
import com.hs.WellnessBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(CreateUserRequest req) {

        User user = User.builder()
                .username(req.getUsername())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .companyName(req.getCompanyName())
                .vendorName(req.getVendorName())
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_HR')")
    @Override
    public Iterable<User> getAllUsers(Authentication auth) {

        var result = userRepository.findAll();
        for (User user : result) {
            user.setPasswordHash("***MASKED***");
        }
        return result;
    }
}
