package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.GatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gates")
public class GatesController {
    @Autowired
    private GatesService gatesService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> createGates(@RequestBody GatesRequest gatesRequest) {
        MessageModel messageModel = new MessageModel();

        GatesResponse gatesResponse = gatesService.addGates(gatesRequest);
        if(gatesResponse == null) {
            messageModel.setMessage("Failed to create gates");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Create new gates");
            messageModel.setData(gatesResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageModel> getAllGates() {
        MessageModel messageModel = new MessageModel();
        try {
            List<GatesResponse> getAllGates = gatesService.searchAllGates();
            messageModel.setMessage("Success get all gates");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(getAllGates);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception) {
            messageModel.setMessage("Failed get all gates");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateGates(@RequestParam Integer gateId, @RequestBody GatesRequest gatesRequest) {
        MessageModel messageModel = new MessageModel();
        GatesResponse gatesResponse = gatesService.updateGates(gatesRequest, gateId);
        if(gatesResponse == null) {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update gates");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update gates with name : " + gatesResponse.getGateName());
            messageModel.setData(gatesResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
