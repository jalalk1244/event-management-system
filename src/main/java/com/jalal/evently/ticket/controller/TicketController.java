package com.jalal.evently.ticket.controller;

import  java.util.List;
import com.jalal.evently.ticket.dto.TicketBookRequest;
import com.jalal.evently.ticket.dto.TicketResponse;
import com.jalal.evently.ticket.entity.Ticket;
import com.jalal.evently.ticket.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse book(
            @Valid @RequestBody TicketBookRequest req,
            Authentication authentication
    ) {
        String email = authentication.getName();

        Ticket ticket = ticketService.book(req.getEventId(), email);

        return TicketResponse.from(ticket);
    }

    @GetMapping("/mine")
    public List<TicketResponse> myTickets(Authentication authentication) {
        String email = authentication.getName(); // from JWT "sub"
        return ticketService.myTickets(email);
    }
}
