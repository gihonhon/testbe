package com.binar.finalproject.flightticket.repository;

import com.binar.finalproject.flightticket.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Tickets, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM tickets t where t.order_id = :orderId")
    List<Tickets> getAllOrderTicket(@Param("orderId") UUID orderId);
}
