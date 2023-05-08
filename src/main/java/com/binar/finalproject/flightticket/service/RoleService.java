package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.RoleRequest;
import com.binar.finalproject.flightticket.dto.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse registerRole(RoleRequest roleRequest);
    List<RoleResponse> searchAllRole();
    RoleResponse updateRole(RoleRequest roleRequest, String roleName);
    Boolean deleteRole(String roleName);
}
