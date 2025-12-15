package com.hs.WellnessBookingSystem.dto;

import com.hs.WellnessBookingSystem.model.UserRole;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private UserRole role;
    private String companyName;
    private String vendorName;
}