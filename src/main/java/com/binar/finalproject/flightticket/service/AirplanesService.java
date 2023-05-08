package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.AirplanesRequest;
import com.binar.finalproject.flightticket.dto.AirplanesResponse;

import java.util.List;

public interface AirplanesService {
    AirplanesResponse insertAirplane(AirplanesRequest airplanesRequest);
    AirplanesResponse searchAirplaneByName(String airplaneName);
    List<AirplanesResponse> getAllAirplane();
    AirplanesResponse updateAirplane(AirplanesRequest airplanesRequest, String airplaneName);
    Boolean deleteAirplane (String airplaneName);
}
