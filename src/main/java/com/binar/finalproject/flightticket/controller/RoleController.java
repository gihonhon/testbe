package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.MessageModel;
import com.binar.finalproject.flightticket.dto.RoleRequest;
import com.binar.finalproject.flightticket.dto.RoleResponse;
import com.binar.finalproject.flightticket.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> createRole(@RequestBody RoleRequest roleRequest)
    {
        MessageModel messageModel = new MessageModel();
        RoleResponse roleResponse = roleService.registerRole(roleRequest);
        if(roleResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to create roles");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.CREATED.value());
            messageModel.setMessage("Create new roles");
            messageModel.setData(roleResponse);
            return ResponseEntity.ok().body(messageModel);

        }
    }

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> getAllRole()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<RoleResponse> rolesGet = roleService.searchAllRole();
            messageModel.setMessage("Success get all role");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(rolesGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all role");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateRole(@RequestParam String roleName, @RequestBody RoleRequest roleRequest)
    {
        MessageModel messageModel = new MessageModel();
        RoleResponse roleResponse = roleService.updateRole(roleRequest, roleName);

        if(roleResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update roles");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update role with id : " + roleResponse.getRoleId());
            messageModel.setData(roleResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> deleteRole(@RequestParam String roleName)
    {
        MessageModel messageModel = new MessageModel();
        Boolean deleteRole = roleService.deleteRole(roleName);
        if(Boolean.TRUE.equals(deleteRole))
        {
            messageModel.setMessage("Success delete role by name : " + roleName);
            messageModel.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(messageModel);
        }
        else
        {
            messageModel.setMessage("Failed delete role by name : " + roleName + ", not found");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
    }
}
