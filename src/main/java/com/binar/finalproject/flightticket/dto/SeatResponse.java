package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Seats;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {
    private Integer seatId;
    private String seatNumber;
    private String seatType;
    private String airplaneName;

    public static SeatResponse build(Seats seats)
    {
        return SeatResponse.builder()
                .seatId(seats.getSeatId())
                .seatNumber(seats.getSeatNumber())
                .seatType(seats.getSeatType())
                .airplaneName(seats.getAirplanesSeats().getAirplaneName())
                .build();
    }
}
