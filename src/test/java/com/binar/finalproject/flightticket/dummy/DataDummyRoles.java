package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.RoleRequest;
import com.binar.finalproject.flightticket.model.Roles;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DataDummyRoles {

    private final List<Roles> DATA_ROLES = new ArrayList<>();
    private final List<RoleRequest> DATA_ROLES_REQUEST = new ArrayList<>();

    public DataDummyRoles(){
        RoleRequest roleRequest1 = new RoleRequest();
        roleRequest1.setRoleName("ROLE_SUPER-ADMIN");
        RoleRequest roleRequest2 = new RoleRequest();
        roleRequest2.setRoleName("ROLE_ADMIN");
        RoleRequest roleRequest3 = new RoleRequest();
        roleRequest3.setRoleName("ROLE_BUYER");

        Roles roles1 = roleRequest1.toRoles();
        Roles roles2 = roleRequest2.toRoles();
        Roles roles3 = roleRequest3.toRoles();

        DATA_ROLES.add(roles1);
        DATA_ROLES.add(roles2);
        DATA_ROLES.add(roles3);

        DATA_ROLES_REQUEST.add(roleRequest1);
        DATA_ROLES_REQUEST.add(roleRequest2);
        DATA_ROLES_REQUEST.add(roleRequest3);
    }

    public Optional<Roles> getRolesByName(String roleName){
        return DATA_ROLES.stream()
                .filter(roles -> roles.getRoleName().equals(roleName))
                .findFirst();
    }
}
