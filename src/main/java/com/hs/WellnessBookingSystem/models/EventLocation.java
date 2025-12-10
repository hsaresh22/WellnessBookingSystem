package com.hs.WellnessBookingSystem.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventLocation {

    private String postalCode;
    private String streetName;
}
