package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Airplanes;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AirplanesRequest {
    @NotEmpty(message = "Airplane type is required.")
    private String airplaneType;
    @NotEmpty(message = "Airplane name is required.")
    private String airplaneName;

    public Airplanes toAirplanes(){
        return Airplanes.builder()
                .airplaneType(this.airplaneType)
                .airplaneName(this.airplaneName)
                .build();
    }
}
