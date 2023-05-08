package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Schedules;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class ScheduleResponse {
    private UUID scheduleId;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Float price;
    private String status;
    private String airplaneName;
    private UUID routeId;

    public static ScheduleResponse build(Schedules schedules) {
        return ScheduleResponse.builder()
                .scheduleId(schedules.getScheduleId())
                .departureDate(schedules.getDepartureDate())
                .arrivalDate(schedules.getArrivalDate())
                .departureTime(schedules.getDepartureTime())
                .arrivalTime(schedules.getArrivalTime())
                .price(schedules.getPrice())
                .status(schedules.getStatus())
                .airplaneName(schedules.getAirplanesSchedules().getAirplaneName())
                .routeId(schedules.getRoutesSchedules().getRouteId())
                .build();
    }
}
