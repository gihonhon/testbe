package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Roles;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RoleRequest {
    @NotEmpty(message = "Role name is required.")
    private String roleName;

    public Roles toRoles() {
        return Roles.builder()
                .roleName(this.roleName)
                .build();
    }
}
