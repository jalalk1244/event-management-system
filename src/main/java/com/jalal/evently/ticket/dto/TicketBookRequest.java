package com.jalal.evently.ticket.dto;

import jakarta.validation.constraints.NotNull;

public class TicketBookRequest {

    @NotNull
    private Long eventId;

    public TicketBookRequest() {}

    public TicketBookRequest(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
