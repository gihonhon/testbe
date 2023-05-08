package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {

    private String status = "WAITING";
    @NotEmpty(message = "User ID is required.")
    private UUID userId;
    @NotEmpty(message = "Payment ID is required.")
    private Integer paymentId;
    @NotEmpty(message = "List of Schedule Id is required.")
    private List<UUID> scheduleId;
    @NotEmpty(message = "List of Traveler List Id is required.")
    private List<UUID> travelerListId;

    public Orders toOrders (Users users, PaymentMethods paymentMethods)
    {
        return Orders.builder()
                .status(this.status)
                .usersOrder(users)
                .paymentMethodsOrder(paymentMethods)
                .build();
    }
}
