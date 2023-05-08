package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Routes;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RouteResponse {
    private UUID routeId;
    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTerminal;
    private String arrivalTerminal;

    public static RouteResponse build(Routes routes) {
        return RouteResponse.builder()
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
