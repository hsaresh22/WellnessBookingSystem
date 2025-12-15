package com.hs.WellnessBookingSystem.service;

import com.hs.WellnessBookingSystem.model.Event;
import com.hs.WellnessBookingSystem.model.EventStatus;
import com.hs.WellnessBookingSystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Unit tests for EventServiceImpl
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event sampleEvent;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sampleEvent = new Event();
        sampleEvent.setId("evt1");
        sampleEvent.setVendorId("vd1");
        sampleEvent.setStatus(EventStatus.PENDING);
    }

    @Test
    void testCreateEvent_setsStatusAndCreatedAt() {
        when(eventRepository.save(any(Event.class))).thenReturn(sampleEvent);

        Event created = eventService.createEvent(sampleEvent);

        assertNotNull(created);
        assertEquals(EventStatus.PENDING, created.getStatus());
        assertNotNull(created.getCreatedAt());
        verify(eventRepository, times(1)).save(sampleEvent);
    }

    @Test
    void testGetEventById_found() {
        when(eventRepository.findById("evt1")).thenReturn(Optional.of(sampleEvent));

        Event fetched = eventService.getEventById("evt1", "vd1");

        assertEquals("vd1", fetched.getVendorId());
    }

    @Test
    void testGetEventById_notFound() {
        when(eventRepository.findById("evt2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> eventService.getEventById("evt2", "vd1"));

        assertEquals("Vendor not authorized to view other vendor's events", ex.getMessage());
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = List.of(sampleEvent);
        when(eventRepository.findAllByOrderByCreatedAtDesc()).thenReturn(events);

        List<Event> fetched = eventService.getAllEvents();
        assertEquals(1, fetched.size());
        verify(eventRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    void testGetEventsForVendor() {
        when(authentication.getName()).thenReturn("vd1");
        List<Event> events = List.of(sampleEvent);
        when(eventRepository.findByVendorIdOrderByCreatedAtDesc("vd1")).thenReturn(events);

        List<Event> fetched = eventService.getEventsForVendor(authentication);
        assertEquals(1, fetched.size());
        assertEquals("vd1", fetched.getFirst().getVendorId());
    }

    @Test
    void testApproveEvent_success() {
        LocalDateTime confirmDate = LocalDateTime.now();
        when(eventRepository.findById("evt1")).thenReturn(Optional.of(sampleEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(sampleEvent);

        Event approved = eventService.approveEvent("evt1", confirmDate);

        assertEquals(EventStatus.APPROVED, approved.getStatus());
        assertEquals(confirmDate, approved.getConfirmedDate());
    }

    @Test
    void testApproveEvent_notFound() {
        when(eventRepository.findById("evt2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> eventService.approveEvent("evt2", LocalDateTime.now()));
        assertEquals("Event not found", ex.getMessage());
    }

    @Test
    void testRejectEvent_success() {
        when(eventRepository.findById("evt1")).thenReturn(Optional.of(sampleEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(sampleEvent);

        Event rejected = eventService.rejectEvent("evt1", "Not suitable");

        assertEquals(EventStatus.REJECTED, rejected.getStatus());
        assertEquals("Not suitable", rejected.getRejectionReason());
    }

    @Test
    void testRejectEvent_notFound() {
        when(eventRepository.findById("evt2")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> eventService.rejectEvent("evt2", "Reason"));
        assertEquals("Event not found", ex.getMessage());
    }
}
