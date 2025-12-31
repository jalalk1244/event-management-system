package com.jalal.evently.ticket.service;

import com.jalal.evently.auth.entity.User;
import com.jalal.evently.auth.repository.UserRepository;
import java.util.List;
import com.jalal.evently.event.entity.Event;
import com.jalal.evently.event.repository.EventRepository;
import com.jalal.evently.ticket.entity.Ticket;
import com.jalal.evently.ticket.repository.TicketRepository;
import com.jalal.evently.ticket.dto.TicketResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


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
                .orElseThrow(() -> new RuntimeException("User not found"));

        // lock event row while we check capacity + insert ticket
        Event event = eventRepository.findByIdForUpdate(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // rule 1: no duplicate booking
        if (ticketRepository.existsByEventIdAndUserEmail(eventId, userEmail)) {
            throw new RuntimeException("You have already booked this event");
        }

        // rule 2: respect capacity
        long bookedCount = ticketRepository.countByEventId(eventId);
        Integer capacity = event.getCapacity();

        if (capacity != null && bookedCount >= capacity) {
            System.out.println("The event is full");
            throw new RuntimeException("Event is fully booked");
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
