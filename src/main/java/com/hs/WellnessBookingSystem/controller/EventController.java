package com.hs.WellnessBookingSystem.controller;

import com.hs.WellnessBookingSystem.model.Event;
import com.hs.WellnessBookingSystem.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

// Controller to handle event-related endpoints
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event APIs", description = "APIs for managing wellness events")
public class EventController {

    @Autowired
    private final EventService eventService;

    // HR admin sees all events
    @GetMapping("/all")
    @Operation(summary = "Get all events", description = "HR admin can see all events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    // Vendor sees only their events
    @GetMapping("/vendor")
    @Operation(summary = "Get vendor events", description = "Vendor can see only their own events")
    public ResponseEntity<List<Event>> getVendorEvents(Authentication auth) {
        return ResponseEntity.ok(eventService.getEventsForVendor(auth));
    }

    @GetMapping("/{eventId}")
    @Operation(summary = "Get event by ID", description = "Returns event details, respects role-based visibility")
    public ResponseEntity<Event> getEventById(@PathVariable String eventId, Authentication auth) {
        return ResponseEntity.ok(eventService.getEventById(eventId, auth.getName()));
    }


    @PostMapping
    @Operation(summary = "Create event", description = "HR admin creates a new event")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, Authentication auth) {
        event.setCreatedBy(auth.getName());
        Event saved = eventService.createEvent(event);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve event", description = "Vendor approves an event by selecting a date")
    public ResponseEntity<Event> approveEvent(@PathVariable String id, @RequestParam LocalDateTime confirmedDate) {
        return ResponseEntity.ok(eventService.approveEvent(id, confirmedDate));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Reject event", description = "Vendor rejects an event with a reason")
    public ResponseEntity<Event> rejectEvent(@PathVariable String id, @RequestParam String reason) {
        return ResponseEntity.ok(eventService.rejectEvent(id, reason));
    }
}
