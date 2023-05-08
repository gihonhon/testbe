package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ScheduleRequest {
    @NotEmpty(message = "Departure Date is required.")
    private LocalDate departureDate;
    @NotEmpty(message = "Arrival Date is required.")
    private LocalDate arrivalDate;
    @NotEmpty(message = "Departure Time is required.")
    private LocalTime departureTime;
    @NotEmpty(message = "Arrival Time is required.")
    private LocalTime arrivalTime;
    @NotEmpty(message = "Price is required.")
    private Float price;
    @NotEmpty(message = "Status is required.")
    private String status;
    @NotEmpty(message = "Airplane Name is required.")
    private String airplaneName;
    @NotEmpty(message = "Route ID is required.")
    private UUID routeId;

    public Schedules toSchedule(Airplanes airplanes, Routes routes) {
        Schedules schedules = new Schedules();
        schedules.setDepartureDate(this.departureDate);
        schedules.setArrivalDate(this.arrivalDate);
        schedules.setDepartureTime(this.departureTime);
        schedules.setArrivalTime(this.arrivalTime);
        schedules.setPrice(this.price);
        schedules.setStatus(this.status);
        schedules.setAirplanesSchedules(airplanes);
        schedules.setRoutesSchedules(routes);
        return schedules;
    }
}
