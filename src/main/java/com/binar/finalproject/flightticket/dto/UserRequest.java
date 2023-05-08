package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Users;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class UserRequest {
    @NotEmpty(message = "fullName is required.")
    private String fullName;
    @NotEmpty(message = "email is required.")
    private String email;
    @NotEmpty(message = "password is required.")
    private String password;
    @NotEmpty(message = "telephone is required.")
    private String telephone;
    @NotEmpty(message = "birthDate is required.")
    private LocalDate birthDate;
    @NotEmpty(message = "gender is required.")
    private Boolean gender;

    private String authProvider;
    private String googleId;

    public Users toUsers() {
        Users users = new Users();
        users.setFullName(this.fullName);
        users.setEmail(this.email);
        users.setPassword(this.password);
        users.setTelephone(this.telephone);
        users.setBirthDate(this.birthDate);
        users.setGender(this.gender);
        users.setStatusActive(true);
        return users;
    }
}
