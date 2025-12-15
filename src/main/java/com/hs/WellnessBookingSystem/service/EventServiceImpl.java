package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.model.Event;
import com.hs.WellnessBookingSystem.model.EventStatus;
import com.hs.WellnessBookingSystem.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public Event createEvent(Event event) {
        event.setStatus(EventStatus.PENDING);
        event.setCreatedAt(LocalDateTime.now());
        return eventRepository.save(event);
    }

    @PreAuthorize(
            "hasRole('HR') or (hasRole('VENDOR') and @eventSecurity.isOwner(#eventId))"
    )
    public Event getEventById(String eventId, String authID) {
        return eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Vendor not authorized to view other vendor's events"));
    }

    @PreAuthorize("hasRole('HR')")
    public List<Event> getAllEvents() {
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Event> getEventsForVendor(Authentication auth) {
        return eventRepository.findByVendorIdOrderByCreatedAtDesc(auth.getName());
    }

    public Event approveEvent(String eventId, LocalDateTime confirmedDate) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setConfirmedDate(confirmedDate);
                    event.setStatus(EventStatus.APPROVED);
                    return eventRepository.save(event);
                }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event rejectEvent(String eventId, String reason) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setStatus(EventStatus.REJECTED);
                    event.setRejectionReason(reason);
                    return eventRepository.save(event);
                }).orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
