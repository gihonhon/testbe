package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Countries;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountriesResponse {
    private String countryCode;
    private String countryName;
    private String telephoneCode;

    public static CountriesResponse build(Countries countries) {
        return CountriesResponse.builder()
                .countryCode(countries.getCountryCode())
                .countryName(countries.getCountryName())
                .telephoneCode(countries.getTelephoneCode())
                .build();
    }
}
