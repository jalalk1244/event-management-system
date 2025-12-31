package com.jalal.evently.ticket.dto;

import com.jalal.evently.ticket.entity.Ticket;
import java.time.LocalDateTime;
public class TicketResponse {
    private Long id;
    private Long eventId;
    private String userEmail;
    private LocalDateTime bookedAt;

    public TicketResponse() {}

    public TicketResponse(Long id, Long eventId, String userEmail, LocalDateTime bookedAt) {
        this.id = id;
        this.eventId = eventId;
        this.userEmail = userEmail;
        this.bookedAt = bookedAt;
    }

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getUser().getEmail(),
                ticket.getBookedAt()
        );
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }
}
