package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.IdCard;
import com.binar.finalproject.flightticket.model.Passport;
import com.binar.finalproject.flightticket.model.TravelerList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelerListDetailResponse {
    private UUID travelerId;
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

    public static TravelerListDetailResponse build(TravelerList travelerList, IdCard idCard, Passport passport)
    {
        return TravelerListDetailResponse.builder()
                .travelerId(travelerList.getTravelerId())
                .userId(travelerList.getUsersTravelerList().getUserId())
                .title(travelerList.getTitle())
                .type(travelerList.getType())
                .firstName(travelerList.getFirstName())
                .lastName(travelerList.getLastName())
                .birthDate(travelerList.getBirthDate())
                .nationality(travelerList.getCountriesTravelerList().getCountryName())
                .idCardNumber(idCard.getIdCardNumber())
                .idCardExpiry(idCard.getIdCardExpiry())
                .idCardCountry(idCard.getCountriesIdCard().getCountryName())
                .passportNumber(passport.getPassportNumber())
                .passportExpiry(passport.getPassportExpiry())
                .passportCardCountry(passport.getCountriesPassport().getCountryName())
                .build();
    }

}
