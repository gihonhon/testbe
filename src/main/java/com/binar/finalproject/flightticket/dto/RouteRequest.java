package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Routes;
import lombok.Data;
import javax.validation.constraints.NotEmpty;


@Data
public class RouteRequest {
    @NotEmpty(message = "Departure City is required.")
    private String departureCity;
    @NotEmpty(message = "Arrival City is required.")
    private String arrivalCity;
    @NotEmpty(message = "Departure Airport is required.")
    private String departureAirport;
    @NotEmpty(message = "Arrival Airport is required.")
    private String arrivalAirport;
    @NotEmpty(message = "Departure Terminal is required.")
    private String departureTerminal;
    @NotEmpty(message = "Arrival Terminal is required.")
    private String arrivalTerminal;

    public Routes toRoutes()
    {
        return Routes.builder()
                .departureCity(this.departureCity)
                .arrivalCity(this.arrivalCity)
                .departureAirport(this.departureAirport)
                .arrivalAirport(this.arrivalAirport)
                .departureTerminal(this.departureTerminal)
                .arrivalTerminal(this.arrivalTerminal)
                .build();
    }
}
