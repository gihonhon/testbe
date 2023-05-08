package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addTicket (@RequestBody TicketRequest ticketRequest)
    {
        MessageModel messageModel = new MessageModel();
        TicketResponse ticketResponse = ticketService.addTicket(ticketRequest);
        if(ticketResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add ticket");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new ticket");
            messageModel.setData(ticketResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
    @GetMapping("/get-all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> getAllTicket()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<TicketResponse> ticketGet = ticketService.getAllTicket();
            messageModel.setMessage("Success get all ticket");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(ticketGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all ticket");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateTicket(@RequestParam UUID ticketId, @RequestBody TicketRequest ticketRequest) {
        MessageModel messageModel = new MessageModel();
        TicketResponse ticketResponse = ticketService.updateTicket(ticketRequest, ticketId);

        if(ticketResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update ticket with id : " + ticketId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update ticket with id : " + ticketId);
            messageModel.setData(ticketResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
    @GetMapping("/findby-id")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getTicketById(@RequestParam UUID ticketId){
        MessageModel messageModel = new MessageModel();
        try {
            TicketResponse ticketGet = ticketService.searchTicketById(ticketId);
            messageModel.setMessage("Success get ticket");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(ticketGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get ticket");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
    @GetMapping("/findby-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getOrderTicket(@RequestParam UUID orderId){
        MessageModel messageModel = new MessageModel();
        try {
            List<TicketResponse> ticketResponses = ticketService.searchOrderTicket(orderId);
            messageModel.setMessage("Success get ticket by order id: " + orderId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(ticketResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get ticket by order id: " + orderId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
}
