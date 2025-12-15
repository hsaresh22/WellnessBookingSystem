package com.hs.WellnessBookingSystem.helper;

import com.hs.WellnessBookingSystem.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// Security component to check if the current user/vendor is the owner of an event
@Component("eventSecurity")
@RequiredArgsConstructor
public class EventSecurity {

    private final EventRepository eventRepository;

    public boolean isOwner(String eventId) {
        if (eventId == null) return false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_HR"))) {
            return true;
        }

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return eventRepository
                .findById(eventId)
                .map(e -> currentUser.equals(e.getVendorId()))
                .orElse(false);
    }
}
