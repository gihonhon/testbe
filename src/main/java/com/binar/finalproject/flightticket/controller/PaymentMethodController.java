package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentMethodController {
    @Autowired
    PaymentMethodService paymentMethodService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addPayment(@RequestBody PaymentMethodRequest paymentMethodRequest) {
        MessageModel messageModel = new MessageModel();

        PaymentMethodResponse paymentMethodResponse = paymentMethodService.addPaymentMethod(paymentMethodRequest);
        if(paymentMethodResponse == null)
        {
            messageModel.setMessage("Failed add new payment method");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new payment method");
            messageModel.setData(paymentMethodResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping("/findby-name")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getPaymentByName(@RequestParam String paymentName){
        MessageModel messageModel = new MessageModel();
        try {
            PaymentMethodResponse paymentGet = paymentMethodService.searchPaymentByName(paymentName);
            messageModel.setMessage("Success get payment");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(paymentGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get payment");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllPayment()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<PaymentMethodResponse> paymentGet = paymentMethodService.gettAllPaymentMethod();
            messageModel.setMessage("Success get all payment");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(paymentGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all payment");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updatePayment(@RequestParam String paymentName, @RequestBody PaymentMethodRequest paymentMethodRequest)
    {
        MessageModel messageModel = new MessageModel();
        PaymentMethodResponse paymentMethodResponse = paymentMethodService.updatePayment(paymentMethodRequest, paymentName);

        if(paymentMethodResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update payment");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update payment with name : " + paymentMethodResponse.getPaymentName());
            messageModel.setData(paymentMethodResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> deletePayment(@RequestParam String paymentName)
    {
        MessageModel messageModel = new MessageModel();
        Boolean deletePayment = paymentMethodService.deletePayment(paymentName);
        if(Boolean.TRUE.equals(deletePayment))
        {
            messageModel.setMessage("Success delete payment by name: " + paymentName);
            messageModel.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(messageModel);
        }
        else
        {
            messageModel.setMessage("Failed delete payment by name: " + paymentName + ", not found");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
    }
}
