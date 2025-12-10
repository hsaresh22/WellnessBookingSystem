package com.hs.WellnessBookingSystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private String id;

    private String eventId;         // Event identifier from dropdown
    private String eventName;       // Human readable name

    private String companyName;
    private String vendorName;

    private List<LocalDate> proposedDates;

    private EventLocation proposedLocation;

    private EventStatus status;

    private LocalDateTime createdAt;

    private LocalDate confirmedDate;

    private String remarks;
}

