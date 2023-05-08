package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Roles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
    private Integer roleId;
    private String roleName;

    public static RoleResponse build(Roles roles) {
        return RoleResponse.builder()
                .roleId(roles.getRoleId())
                .roleName(roles.getRoleName())
                .build();
    }
}
