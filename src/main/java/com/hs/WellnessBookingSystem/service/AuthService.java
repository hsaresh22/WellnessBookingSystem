package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.dto.CreateUserRequest;
import com.hs.WellnessBookingSystem.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

public interface AuthService {
    User createUser(CreateUserRequest req);

    @PreAuthorize("hasRole('ROLE_HR')")
    Iterable<User> getAllUsers(Authentication auth);
}
