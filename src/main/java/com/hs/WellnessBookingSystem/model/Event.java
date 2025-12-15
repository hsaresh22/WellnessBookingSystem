package com.hs.WellnessBookingSystem.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    private String name;                 // Event Name
    private String vendorId;             // Vendor account ID (owner)
    private String createdBy;            // HR admin ID who created
    private String description;

    private List<LocalDateTime> proposedDates;   // list of proposed dates
    private LocalDateTime confirmedDate;         // approved date, nullable

    private EventStatus status;           // PENDING, APPROVED, REJECTED
    private String rejectionReason;       // if rejected
    private LocalDateTime createdAt;      // timestamp
}
