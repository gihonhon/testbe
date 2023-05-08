package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.SeatRequest;
import com.binar.finalproject.flightticket.dto.SeatResponse;

import java.util.List;

public interface SeatService {
    SeatResponse addSeat(SeatRequest seatAllRequest);
    List<SeatResponse> addAllSeat(List<SeatRequest> seatRequest);
    SeatResponse searchSeatById(Integer seatId);
    List<SeatResponse> getAllSeat();
    SeatResponse updateSeat(SeatRequest seatRequest, Integer seatId);
    List<SeatResponse> searchAirplaneSeat(String airplaneName);
}
