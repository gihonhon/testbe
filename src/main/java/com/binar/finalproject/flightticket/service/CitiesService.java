package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.CitiesRequest;
import com.binar.finalproject.flightticket.dto.CitiesResponse;

import java.util.List;

public interface CitiesService {
    CitiesResponse addCity(CitiesRequest citiesRequest);
    List<CitiesResponse> searchAllCity();
    CitiesResponse updateCity(CitiesRequest citiesRequest, String cityCode);
    List<CitiesResponse> searchCityByName(String cityName);
}
