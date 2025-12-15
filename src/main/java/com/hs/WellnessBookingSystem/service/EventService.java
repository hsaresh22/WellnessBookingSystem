package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.model.Event;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    List<Event> getAllEvents();
    Event getEventById(String auth, String authID);
    List<Event> getEventsForVendor(Authentication auth);
    Event approveEvent(String vendorId, LocalDateTime confirmedDate);
    Event rejectEvent(String eventId, String reason);
}
