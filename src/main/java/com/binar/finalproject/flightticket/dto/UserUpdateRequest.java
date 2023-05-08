package com.binar.finalproject.flightticket.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String telephone;
    private LocalDate birthDate;
    private Boolean gender;
}
