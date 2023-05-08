package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.RoleRequest;
import com.binar.finalproject.flightticket.dummy.DataDummyRoles;
import com.binar.finalproject.flightticket.exception.DataAlreadyExistException;
import com.binar.finalproject.flightticket.exception.DataNotFoundException;
import com.binar.finalproject.flightticket.model.Roles;
import com.binar.finalproject.flightticket.repository.RoleRepository;
import com.binar.finalproject.flightticket.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private DataDummyRoles dataDummyRoles;
    private List<RoleRequest> dataRoleRequest = new ArrayList<>();
    private List<Roles> dataRoles = new ArrayList<>();

    @BeforeEach
    void Init(){
        MockitoAnnotations.initMocks(this);
        dataDummyRoles = new DataDummyRoles();
        dataRoleRequest = dataDummyRoles.getDATA_ROLES_REQUEST();
        dataRoles = dataDummyRoles.getDATA_ROLES();
    }

    @Test
    @DisplayName("[Positive] Register new role")
    void testPositiveRegisterRole(){
        Mockito.when(roleRepository.findByName(dataRoleRequest.get(0).getRoleName())).thenReturn(null);
        Mockito.when(roleRepository.save(dataRoles.get(0))).thenReturn(dataRoles.get(0));
        var actualValue = roleService.registerRole(dataRoleRequest.get(0));
        var expectedValue = dataRoleRequest.get(0).getRoleName();
        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue);
        Assertions.assertEquals(expectedValue, actualValue.getRoleName());
    }

    @Test
    @DisplayName("[Negative] Register new role")
    void testNegativeRegisterRole(){

        DataAlreadyExistException exception = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            Mockito.when(roleRepository.findByName(dataRoleRequest.get(0).getRoleName())).thenReturn(dataRoles.get(0));
            roleService.registerRole(dataRoleRequest.get(0));
        });

        var expectedValue = "Role with this name already exist";
        Assertions.assertEquals(expectedValue, exception.getMessage());
    }

    @Test
    @DisplayName("[Positive] Get all role")
    void testPositiveGetAllRole(){
        Mockito.when(roleRepository.findAll()).thenReturn(dataRoles);
        var actualValue = roleService.searchAllRole();
        var expectedSize = dataRoles.size();
        var expectedValue1 = dataRoles.get(0);
        var expectedValue2 = dataRoles.get(1);
        var expectedValue3 = dataRoles.get(2);
        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertNotNull(expectedValue3);
        Assertions.assertEquals(expectedSize, actualValue.size());
        Assertions.assertEquals(expectedValue1.getRoleName(), actualValue.get(0).getRoleName());
        Assertions.assertEquals(expectedValue2.getRoleName(), actualValue.get(1).getRoleName());
        Assertions.assertEquals(expectedValue3.getRoleName(), actualValue.get(2).getRoleName());
    }

    @Test
    @DisplayName("[Positive] Update role")
    void testPositiveUpdateRole(){
        RoleRequest dataUpdateRole = new RoleRequest();
        dataUpdateRole.setRoleName("ROLE_CUSTOMER");

        RoleRequest roleRequest = dataRoleRequest.get(2);
        Optional<Roles> roles = dataDummyRoles.getRolesByName(roleRequest.getRoleName());

        Roles rolesData = roles.get();

        Mockito.when(roleRepository.findByName(roleRequest.getRoleName())).thenReturn(rolesData);

        rolesData.setRoleName(dataUpdateRole.getRoleName());

        Mockito.when(roleRepository.save(rolesData)).thenReturn(rolesData);

        var actualValue = roleService.updateRole(dataUpdateRole, roleRequest.getRoleName());

        Assertions.assertNotNull(rolesData);
        Assertions.assertNotNull(actualValue);
        Assertions.assertEquals(dataUpdateRole.getRoleName(), actualValue.getRoleName());
    }

    @Test
    @DisplayName("[Negative] Update role")
    void testNegativeUpdateRole(){
        String roleName =  "ROLE_SUPER-ADMIN";

        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> {
            Mockito.when(roleRepository.findByName(roleName)).thenReturn(null);
            roleService.updateRole(dataRoleRequest.get(0), roleName);
        });

        DataAlreadyExistException exception2 = Assertions.assertThrows(DataAlreadyExistException.class, () -> {
            Mockito.when(roleRepository.findByName(roleName)).thenReturn(dataRoles.get(0));
            roleService.updateRole(dataRoleRequest.get(0), roleName);
        });

        Assertions.assertEquals("Roles not found", exception.getMessage());
        Assertions.assertEquals("Role with this name already exist", exception2.getMessage());
    }

    @Test
    @DisplayName("[Positive] Delete role")
    void testPositiveDeleteRole(){
        String roleName =  "ROLE_SUPER-ADMIN";

        Optional<Roles> roles = dataDummyRoles.getRolesByName(roleName);

        Roles rolesData = roles.get();

        Mockito.when(roleRepository.findByName(roleName)).thenReturn(rolesData);
        Mockito.doNothing().when(roleRepository).deleteById(rolesData.getRoleId());

        var actualValue = roleService.deleteRole(roleName);
        var expectedValue = true;

        Assertions.assertNotNull(rolesData);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Negative] Delete role")
    void testNegativeUDeleteRole(){
        String roleName =  "ROLE_SUPER-ADMIN";

        DataNotFoundException exception = Assertions.assertThrows(DataNotFoundException.class, () -> {
            Mockito.when(roleRepository.findByName(roleName)).thenReturn(null);
            roleService.deleteRole(dataRoleRequest.get(0).getRoleName());
        });

        Assertions.assertEquals("Role not found", exception.getMessage());
    }
}
