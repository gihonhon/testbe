package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Tickets;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class TicketResponse {
    private UUID ticketId;
    private UUID travelerId;
    private UUID orderId;
    private Integer seatId;

    public static TicketResponse build(Tickets tickets)
    {
        return TicketResponse.builder()
                .ticketId(tickets.getTicketId())
                .travelerId(tickets.getTravelerListTicket().getTravelerId())
                .orderId(tickets.getOrdersTicket().getOrderId())
                .seatId(tickets.getSeatsTicket().getSeatId())
                .build();
    }
}
