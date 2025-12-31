package com.jalal.evently.ticket.entity;

import com.jalal.evently.auth.entity.User;
import com.jalal.evently.event.entity.Event;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ticket_event_user", columnNames = {"event_id", "user_id"})
        }
)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    protected Ticket() {}

    public Ticket(Event event, User user, LocalDateTime bookedAt) {
        this.event = event;
        this.user = user;
        this.bookedAt = bookedAt;
    }

    public Long getId() { return id; }

    public Event getEvent() { return event; }
    public void setEvent(Event event) { this.event = event; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }
}
