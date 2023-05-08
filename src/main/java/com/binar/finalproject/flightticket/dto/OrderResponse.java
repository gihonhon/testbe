package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Orders;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponse {
    private UUID orderId;
    private String status;
    private Integer totalTicket;
    private Float totalPrice;
    private String pnrCode;
    private UUID userId;
    private List<String> departureCity;
    private List<String> arrivalCity;
    private Integer paymentId;
    private List<UUID> scheduleId;

    public static OrderResponse build(Orders orders, List<UUID> scheduleId,
                                      List<String> departureCity, List<String> arrivalCity)
    {
        return OrderResponse.builder()
                .orderId(orders.getOrderId())
                .status(orders.getStatus())
                .totalTicket(orders.getTotalTicket())
                .totalPrice(orders.getTotalPrice())
                .pnrCode(orders.getPnrCode())
                .userId(orders.getUsersOrder().getUserId())
                .paymentId(orders.getPaymentMethodsOrder().getPaymentId())
                .departureCity(departureCity)
                .arrivalCity(arrivalCity)
                .scheduleId(scheduleId)
                .build();
    }
}
