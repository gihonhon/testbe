package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class TicketRequest {
    @NotEmpty(message = "Traveler Id is required.")
    private UUID travelerId;
    @NotEmpty(message = "Order Id is required.")
    private UUID orderId;
    @NotEmpty(message = "Seat Id is required.")
    private Integer seatId;
    public Tickets toTickets(TravelerList travelerList, Orders orders, Seats seats)
    {
        return Tickets.builder()
                .travelerListTicket(travelerList)
                .ordersTicket(orders)
                .seatsTicket(seats)
                .build();
    }
}
