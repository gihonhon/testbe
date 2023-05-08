package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.TravelerList;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelerListResponse {
    private UUID travelerId;
    private String type;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private UUID userId;
    private String countryCode;

    public static TravelerListResponse build(TravelerList travelerList) {
        return TravelerListResponse.builder()
                .travelerId(travelerList.getTravelerId())
                .type(travelerList.getType())
                .title(travelerList.getTitle())
                .firstName(travelerList.getFirstName())
                .lastName(travelerList.getLastName())
                .birthDate(travelerList.getBirthDate())
                .userId(travelerList.getUsersTravelerList().getUserId())
                .countryCode(travelerList.getCountriesTravelerList().getCountryCode())
                .build();
    }
}
