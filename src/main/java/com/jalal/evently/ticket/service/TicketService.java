package com.jalal.evently.ticket.service;

import com.jalal.evently.auth.entity.User;
import com.jalal.evently.auth.repository.UserRepository;
import com.jalal.evently.common.ApiException;
import com.jalal.evently.event.entity.Event;
import com.jalal.evently.event.repository.EventRepository;
import com.jalal.evently.ticket.dto.TicketResponse;
import com.jalal.evently.ticket.entity.Ticket;
import com.jalal.evently.ticket.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public TicketService(UserRepository userRepository, TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Ticket book(Long eventId, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User not found"));

        Event event = eventRepository.findByIdForUpdate(eventId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "EVENT_NOT_FOUND", "Event not found"));

        if (ticketRepository.existsByEventIdAndUserEmail(eventId, userEmail)) {
            throw new ApiException(HttpStatus.CONFLICT, "ALREADY_BOOKED", "You have already booked this event");
        }

        long bookedCount = ticketRepository.countByEventId(eventId);
        Integer capacity = event.getCapacity();

        if (capacity != null && bookedCount >= capacity) {
            throw new ApiException(HttpStatus.CONFLICT, "EVENT_FULL", "Event is fully booked");
        }

        Ticket ticket = new Ticket(event, user, LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<TicketResponse> myTickets(String email) {
        return ticketRepository.findAllByUserEmailOrderByBookedAtDesc(email)
                .stream()
                .map(TicketResponse::from)
                .toList();
    }
}
