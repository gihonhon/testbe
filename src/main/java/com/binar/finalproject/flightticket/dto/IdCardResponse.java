package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.IdCard;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class IdCardResponse {
    private UUID idCardId;
    private String idCardNumber;
    private LocalDate idCardExpiry;
    private String countryCode;
    private UUID travelerId;

    public static IdCardResponse build(IdCard idCard) {
        return IdCardResponse.builder()
                .idCardId(idCard.getIdCardId())
                .idCardNumber(idCard.getIdCardNumber())
                .idCardExpiry(idCard.getIdCardExpiry())
                .countryCode(idCard.getCountriesIdCard().getCountryCode())
                .travelerId(idCard.getTravelerListIdCard().getTravelerId())
                .build();
    }
}
