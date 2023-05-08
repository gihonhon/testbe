package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.AirportGetAllResponse;
import com.binar.finalproject.flightticket.dto.AirportRequest;
import com.binar.finalproject.flightticket.dto.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse addAirports(AirportRequest airportRequest);
    List<AirportGetAllResponse> searchAllAirports();
    AirportResponse updateAirports(AirportRequest airportRequest, String iataCode);
    List<AirportResponse> searchAirportsByName(String airportName);
    List<AirportResponse> searchAirportByCityName(String cityName);
}
