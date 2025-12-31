package com.jalal.evently.event.service;

import com.jalal.evently.event.dto.EventCreateRequest;
import com.jalal.evently.event.dto.EventResponse;
import com.jalal.evently.event.entity.Event;
import com.jalal.evently.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse create(EventCreateRequest req, String createdByEmail) {
        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setLocation(req.getLocation());
        event.setStartTime(req.getStartTime());
        event.setEndTime(req.getEndTime());
        event.setCapacity(req.getCapacity());
        event.setCreatedByEmail(createdByEmail);

        Event saved = eventRepository.save(event);
        return toResponse(saved);
    }

    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EventResponse getById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return EventResponse.from(event);
    }

    private EventResponse toResponse(Event e) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getLocation(),
                e.getStartTime(),
                e.getEndTime(),
                e.getCapacity(),
                e.getCreatedByEmail()
        );
    }

    public EventResponse update(Long id, EventCreateRequest req) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setLocation(req.getLocation());
        event.setStartTime(req.getStartTime());
        event.setEndTime(req.getEndTime());
        event.setCapacity(req.getCapacity());

        return EventResponse.from(eventRepository.save(event));
    }

    @Transactional
    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }
}
