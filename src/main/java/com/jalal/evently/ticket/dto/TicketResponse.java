package com.jalal.evently.ticket.dto;

import com.jalal.evently.ticket.entity.Ticket;
import java.time.LocalDateTime;
public class TicketResponse {
    private Long id;
    private Long eventId;
    private String userEmail;
    private String eventTitle;
    private LocalDateTime bookedAt;

    public TicketResponse() {}

    public TicketResponse(Long id, Long eventId, String eventTitle, String userEmail, LocalDateTime bookedAt) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventId = eventId;
        this.userEmail = userEmail;
        this.bookedAt = bookedAt;
    }

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getEvent().getId(),
                ticket.getEvent().getTitle(),
                ticket.getUser().getEmail(),
                ticket.getBookedAt()
        );
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getEventTitle() { return eventTitle; }
    public void setEventTitle(String eventTitle) { this.eventTitle = eventTitle; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }
}
