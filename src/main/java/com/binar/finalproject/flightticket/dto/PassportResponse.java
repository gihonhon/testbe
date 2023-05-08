package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Passport;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PassportResponse {
    private UUID passportId;
    private String passportNumber;
    private LocalDate passportExpiry;
    private String countryCode;
    private UUID travelerId;

    public static PassportResponse build(Passport passport) {
        return PassportResponse.builder()
                .passportId(passport.getPassportId())
                .passportNumber(passport.getPassportNumber())
                .passportExpiry(passport.getPassportExpiry())
                .countryCode(passport.getCountriesPassport().getCountryCode())
                .travelerId(passport.getTravelerListPassport().getTravelerId())
                .build();
    }
}
