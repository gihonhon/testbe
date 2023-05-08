package com.binar.finalproject.flightticket.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue
    @Column(name = "ticket_id")
    private UUID ticketId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "modified_at", insertable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="traveler_id", nullable = false)
    private TravelerList travelerListTicket;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id", nullable = false)
    private Orders ordersTicket;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seat_id", nullable = false)
    private Seats seatsTicket;
}
