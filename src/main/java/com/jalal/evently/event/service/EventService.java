package com.jalal.evently.event.service;

import com.jalal.evently.common.exception.NotFoundException;
import com.jalal.evently.event.dto.EventCreateRequest;
import com.jalal.evently.event.dto.EventResponse;
import com.jalal.evently.event.entity.Event;
import com.jalal.evently.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse create(EventCreateRequest req) {
        Event e = new Event();
        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());
        e.setDateTime(req.getDateTime());
        e.setLocation(req.getLocation());
        e.setCapacity(req.getCapacity());

        Event saved = eventRepository.save(e);
        return toResponse(saved);
    }

    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EventResponse getById(Long id) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found: " + id));
        return toResponse(e);
    }

    public EventResponse update(Long id, EventCreateRequest req) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found: " + id));

        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());
        e.setDateTime(req.getDateTime());
        e.setLocation(req.getLocation());
        e.setCapacity(req.getCapacity());

        Event saved = eventRepository.save(e);
        return toResponse(saved);
    }

    public void delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event not found: " + id);
        }
        eventRepository.deleteById(id);
    }

    private EventResponse toResponse(Event e) {
        return new EventResponse(
                e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getDateTime(),
                e.getLocation(),
                e.getCapacity()
        );
    }
}
