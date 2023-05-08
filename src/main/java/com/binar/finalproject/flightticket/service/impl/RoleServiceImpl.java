package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.RoleRequest;
import com.binar.finalproject.flightticket.dto.RoleResponse;
import com.binar.finalproject.flightticket.exception.DataAlreadyExistException;
import com.binar.finalproject.flightticket.exception.DataNotFoundException;
import com.binar.finalproject.flightticket.model.Roles;
import com.binar.finalproject.flightticket.repository.RoleRepository;
import com.binar.finalproject.flightticket.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleResponse registerRole(RoleRequest roleRequest) {
        Roles roles = roleRepository.findByName(roleRequest.getRoleName());
        if(roles == null){
            Roles roleData = roleRequest.toRoles();
            roleRepository.save(roleData);
            return RoleResponse.build(roleData);
        }
        else {
            throw new DataAlreadyExistException("Role with this name already exist");
        }
    }

    @Override
    public List<RoleResponse> searchAllRole() {
        List<Roles> allRole = roleRepository.findAll();
        List<RoleResponse> allRoleResponse = new ArrayList<>();
        for (Roles roles: allRole) {
            RoleResponse roleResponse = RoleResponse.build(roles);
            allRoleResponse.add(roleResponse);
        }
        return allRoleResponse;
    }

    @Override
    public RoleResponse updateRole(RoleRequest roleRequest, String roleName) {
        Roles roles = roleRepository.findByName(roleName);
        if(roles != null){
            Roles isRoleExist = roleRepository.findByName(roleRequest.getRoleName());
            if(isRoleExist == null){
                roles.setRoleName(roleRequest.getRoleName());
                roleRepository.save(roles);
                return RoleResponse.build(roles);
            }
            else {
                throw new DataAlreadyExistException("Role with this name already exist");
            }
        }
        else
            throw new DataNotFoundException("Roles not found");
    }

    @Override
    public Boolean deleteRole(String roleName) {
        Roles roles = roleRepository.findByName(roleName);
        if(roles != null) {
            roleRepository.deleteById(roles.getRoleId());
            return true;
        }
        else
            throw new DataNotFoundException("Role not found");
    }
}
