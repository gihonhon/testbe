package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.service.impl.security.UserDetailsImpl;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String type;
    private UUID userId;
    private String fullName;
    private String email;
    private String telephone;
    private LocalDate birthDate;
    private Boolean gender;

    public static LoginResponse build(String jwt, UserDetailsImpl userDetails) {
        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .userId(userDetails.getUserId())
                .fullName(userDetails.getFullName())
                .email(userDetails.getEmail())
                .telephone(userDetails.getTelephone())
                .birthDate(userDetails.getBirthDate())
                .gender(userDetails.getGender())
                .build();
    }
}
