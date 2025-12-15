package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.dto.CreateUserRequest;
import com.hs.WellnessBookingSystem.model.User;
import com.hs.WellnessBookingSystem.model.UserRole;
import com.hs.WellnessBookingSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    private CreateUserRequest sampleRequest;
    private User savedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleRequest = new CreateUserRequest();
        sampleRequest.setUsername("testuser");
        sampleRequest.setPassword("password123");
        sampleRequest.setRole(UserRole.HR);
        sampleRequest.setCompanyName("Acme Corp");
        sampleRequest.setVendorName(null);

        savedUser = User.builder()
                .username("testuser")
                .passwordHash("***MASKED***")
                .role(UserRole.HR)
                .companyName("Acme Corp")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateUser_savesUserWithEncodedPassword() {
        when(passwordEncoder.encode(sampleRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User created = authService.createUser(sampleRequest);

        assertNotNull(created);
        assertEquals("testuser", created.getUsername());
        assertEquals("encodedPassword", created.getPasswordHash());
        assertEquals(UserRole.HR, created.getRole());
        assertEquals("Acme Corp", created.getCompanyName());
        assertNotNull(created.getCreatedAt());

        verify(passwordEncoder, times(1)).encode(sampleRequest.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetAllUsers_masksPasswords() {
        User user1 = User.builder().username("u1").passwordHash("pass1").role(UserRole.HR).build();
        User user2 = User.builder().username("u2").passwordHash("pass2").role(UserRole.VENDOR).build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        Iterable<User> users = authService.getAllUsers(authentication);

        for (User u : users) {
            assertEquals("***MASKED***", u.getPasswordHash());
        }

        verify(userRepository, times(1)).findAll();
    }
}
