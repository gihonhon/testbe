package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Cities;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AirportRequest {
    @NotEmpty(message = "IATA code is required.")
    private String iataCode;
    @NotEmpty(message = "Airports name is required.")
    private String airportName;
    @NotEmpty(message = "city is required.")
    private String cityCode;

    public Airports toAirports(Cities cities){
        Airports airports = new Airports();
        airports.setIataCode(this.iataCode);
        airports.setAirportName(this.airportName);
        airports.setCitiesAirport(cities);
        return airports;
    }
}
