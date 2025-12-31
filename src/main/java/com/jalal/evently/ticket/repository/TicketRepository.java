package com.jalal.evently.ticket.repository;

import com.jalal.evently.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByEventIdAndUserEmail(Long eventId, String email);
    long countByEventId(Long eventId);

    List<Ticket> findAllByUserEmailOrderByBookedAtDesc(String email);
}
