package com.hs.WellnessBookingSystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String username;

    private String passwordHash;

    private UserRole role;

    private String companyName;   // For HR accounts

    private String vendorName;    // For Vendor accounts

    private List<String> eventTags;   // Optional: vendor handles specific event types
}

