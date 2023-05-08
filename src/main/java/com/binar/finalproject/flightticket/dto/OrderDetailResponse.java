package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailResponse {

    private UUID orderId;
    private String status;

    private String fullName;
    private String email;
    private UUID userId;

    private Float totalPrice;
    private String pnrCode;

    @JsonProperty("orderDate")
    private LocalDateTime createdAt;

    private List<UUID> scheduleId;
    private String airplaneName;

    private String departureCity;
    private String arrivalCity;
    private String departureIATA;
    private String arrivalIATA;
    private String departureTerminal;

    private LocalDate departureDate;
    private LocalTime departureTime;

    private Integer paymentId;
    private String paymentName;
    private String paymentType;

    private List<UUID> travelerListId;
    private List<String> travelerListName;

    public static OrderDetailResponse build(Orders orders, Airplanes airplanes, Airports departureAirport,
                                            Airports arrivalAirport, Routes routes, Schedules schedules, PaymentMethods paymentMethods)
    {
        return OrderDetailResponse.builder()
                .orderId(orders.getOrderId())
                .status(orders.getStatus())
                .totalPrice(schedules.getPrice())
                .pnrCode(orders.getPnrCode())
                .createdAt(orders.getCreatedAt())
                .airplaneName(airplanes.getAirplaneName())
                .departureCity(routes.getDepartureCity())
                .arrivalCity(routes.getArrivalCity())
                .departureIATA(departureAirport.getIataCode())
                .arrivalIATA(arrivalAirport.getIataCode())
                .departureTerminal(routes.getDepartureTerminal())
                .departureDate(schedules.getDepartureDate())
                .departureTime(schedules.getDepartureTime())
                .paymentName(paymentMethods.getPaymentName())
                .paymentType(paymentMethods.getPaymentType())
                .build();
    }
}
