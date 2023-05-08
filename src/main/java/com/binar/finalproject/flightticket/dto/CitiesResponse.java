package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitiesResponse {
    private String cityCode;
    private String cityName;
    private String countryCode;

    public static CitiesResponse build(Cities cities) {
        return CitiesResponse.builder()
                .cityCode(cities.getCityCode())
                .cityName(cities.getCityName())
                .countryCode(cities.getCountriesCities().getCountryCode())
                .build();
    }
}
