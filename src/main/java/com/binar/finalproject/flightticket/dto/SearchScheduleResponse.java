package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Airplanes;
import com.binar.finalproject.flightticket.model.Routes;
import com.binar.finalproject.flightticket.model.Schedules;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class SearchScheduleResponse {
    private UUID scheduleId;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Float price;
    private String status;
    private String departureIATACode;
    private String arrivalIATACode;

    private String airplaneName;
    private String airplaneType;

    private UUID routeId;
    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTerminal;
    private String arrivalTerminal;
    public static SearchScheduleResponse build (Schedules schedules, Routes routes, Airplanes airplanes, String departureIATACode, String arrivalIATACode) {
        return SearchScheduleResponse.builder()
                .scheduleId(schedules.getScheduleId())
                .departureIATACode(departureIATACode)
                .arrivalIATACode(arrivalIATACode)
                .departureDate(schedules.getDepartureDate())
                .arrivalDate(schedules.getArrivalDate())
                .departureTime(schedules.getDepartureTime())
                .arrivalTime(schedules.getArrivalTime())
                .price(schedules.getPrice())
                .status(schedules.getStatus())
                .airplaneName(airplanes.getAirplaneName())
                .airplaneType(airplanes.getAirplaneType())
                .routeId(routes.getRouteId())
                .departureCity(routes.getDepartureCity())
                .arrivalCity(routes.getArrivalCity())
                .departureAirport(routes.getDepartureAirport())
                .arrivalAirport(routes.getArrivalAirport())
                .departureTerminal(routes.getDepartureTerminal())
                .arrivalTerminal(routes.getArrivalTerminal())
                .build();
    }
}
