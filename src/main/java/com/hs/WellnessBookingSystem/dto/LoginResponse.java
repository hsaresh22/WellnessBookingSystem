package com.hs.WellnessBookingSystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String username;
    private String role;
    private String jwt;
    private String message;
}
