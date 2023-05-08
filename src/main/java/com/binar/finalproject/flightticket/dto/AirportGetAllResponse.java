package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Cities;
import com.binar.finalproject.flightticket.model.Countries;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportGetAllResponse {
    private String iataCode;
    private String airportName;
    private String cityCode;
    private String cityName;
    private String countryName;

    public static AirportGetAllResponse build(Airports airports, Cities cities, Countries countries) {
        return AirportGetAllResponse.builder()
                .iataCode(airports.getIataCode())
                .airportName(airports.getAirportName())
                .cityCode(airports.getCitiesAirport().getCityCode())
                .cityName(cities.getCityName())
                .countryName(countries.getCountryName())
                .build();
    }
}
