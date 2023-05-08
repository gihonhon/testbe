package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Countries;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CountriesRequest {
    @NotEmpty(message = "Countries code is required.")
    private String countryCode;
    @NotEmpty(message = "Countries name is required.")
    private String countryName;

    @NotEmpty(message = "Countries telephone Code is required.")
    private String telephoneCode;

    public Countries toCountries() {
        return Countries.builder()
                .countryCode(this.countryCode)
                .countryName(this.countryName)
                .telephoneCode(this.telephoneCode)
                .build();
    }
}
