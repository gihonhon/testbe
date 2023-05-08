package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/terminals")
public class TerminalController {
    @Autowired
    private TerminalService terminalService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> createTerminals(@RequestBody TerminalRequest terminalRequest) {
        MessageModel messageModel = new MessageModel();
        TerminalResponse terminalResponse = terminalService.addTerminal(terminalRequest);
        if(terminalResponse == null) {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to create terminals");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else {
            messageModel.setStatus(HttpStatus.CREATED.value());
            messageModel.setMessage("Create new terminals");
            messageModel.setData(terminalResponse);
            return ResponseEntity.ok().body(messageModel);

        }
    }

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllTerminal() {
        MessageModel messageModel = new MessageModel();
        try {
            List<TerminalResponse> getAllTerminal = terminalService.searchAllTerminal();
            messageModel.setMessage("Success get all Terminal");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(getAllTerminal);
            return ResponseEntity.ok().body(messageModel);
        } catch (Exception exception) {
            messageModel.setMessage("Failed get all Terminal");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateTerminal(@RequestParam Integer terminalId, @RequestBody TerminalRequest terminalRequest)
    {
        MessageModel messageModel = new MessageModel();
        TerminalResponse terminalResponse = terminalService.updateTerminal(terminalRequest, terminalId);

        if(terminalResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update Terminal");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update Terminal with name : " + terminalResponse.getTerminalName());
            messageModel.setData(terminalResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping(value = "/findby-id", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getTerminalById(@RequestParam Integer terminalId){
        MessageModel messageModel = new MessageModel();
        try {
            TerminalResponse getTerminal = terminalService.searchTerminalById(terminalId);
            messageModel.setMessage("Success get Terminal By name");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(getTerminal);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get Terminal By Name");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
}
