package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TravelerListDetailRequest {
    private String type;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String nationality;
    private UUID userId;

    private String idCardNumber;
    private LocalDate idCardExpiry;
    private String idCardCountry;

    private String passportNumber;
    private LocalDate passportExpiry;
    private String passportCardCountry;

    public static TravelerListDetailRequest build(TravelerList travelerList) {
        return TravelerListDetailRequest.builder()
                .type(travelerList.getType())
                .title(travelerList.getTitle())
                .firstName(travelerList.getFirstName())
                .lastName(travelerList.getLastName())
                .birthDate(travelerList.getBirthDate())
                .nationality(travelerList.getCountriesTravelerList().getCountryName())
                .build();
    }
}
