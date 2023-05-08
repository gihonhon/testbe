package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TravelerListRequest {
    @NotEmpty(message = "type is required.")
    private String type;
    @NotEmpty(message = "title is required.")
    private String title;
    @NotEmpty(message = "firstName is required.")
    private String firstName;
    @NotEmpty(message = "lastName is required.")
    private String lastName;
    @NotEmpty(message = "birthDate is required.")
    private LocalDate birthDate;
    @NotEmpty(message = "userId is required.")
    private UUID userId;
    @NotEmpty(message = "countryCode is required.")
    private String countryCode;

    public TravelerList toTravelerList(Users users, Countries countries) {
        TravelerList travelerList = new TravelerList();
        travelerList.setType(this.type);
        travelerList.setTitle(this.title);
        travelerList.setFirstName(this.firstName);
        travelerList.setLastName(this.lastName);
        travelerList.setBirthDate(this.birthDate);
        travelerList.setUsersTravelerList(users);
        travelerList.setCountriesTravelerList(countries);
        return travelerList;
    }
}
