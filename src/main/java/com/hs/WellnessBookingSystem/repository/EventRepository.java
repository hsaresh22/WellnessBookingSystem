package com.hs.WellnessBookingSystem.repository;

import com.hs.WellnessBookingSystem.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository interface for Event entity
@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    // For HR admin: get all events
    List<Event> findAllByOrderByCreatedAtDesc();

    // For Vendor Admin: get events owned by them
    List<Event> findByVendorIdOrderByCreatedAtDesc(String vendorId);
}
