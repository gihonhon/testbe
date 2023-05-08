package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addOrder (@RequestBody OrderRequest orderRequest)
    {
        MessageModel messageModel = new MessageModel();
        OrderResponse orderResponse = orderService.addOrder(orderRequest);
        if(orderResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add order");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new order");
            messageModel.setData(orderResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageModel> updateOrder(@RequestParam UUID orderId, @RequestBody OrderRequest orderRequest) {
        MessageModel messageModel = new MessageModel();
        OrderResponse orderResponse = orderService.updateOrder(orderRequest, orderId);

        if(orderResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update order with id : " + orderId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update order with id : " + orderId);
            messageModel.setData(orderResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageModel> getAllOrders()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderResponse> orderGet = orderService.getAllOrder();
            messageModel.setMessage("Success get all orders");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all orders");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get-all-dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageModel> getAllTravelerListAdmin(){
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderDetailResponse> orderGet = orderService.getAllOrderAdmin();
            messageModel.setMessage("Success get all traveler list to dashboard");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list to dashboard");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/findby-user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getUserOrders(@RequestParam UUID userId){
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderResponse> orderResponses = orderService.getAllOrderByUserId(userId);
            messageModel.setMessage("Success get order by user id : " + userId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get order by user id, " + userId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/findby-payment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageModel> getPaymentOrders(@RequestParam Integer paymentId){
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderResponse> orderResponses = orderService.getAllOrderByPaymentId(paymentId);
            messageModel.setMessage("Success get order by payment id : " + paymentId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get order, payment id not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/detail")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getOrdersDetail(@RequestParam UUID orderId){
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderDetailResponse> orderResponses = orderService.getOrderDetails(orderId);
            messageModel.setMessage("Success get order detail by id : " + orderId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get order detail by order id, " + orderId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get-status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getUserOrdersStatus(@RequestParam UUID userId, @RequestParam String status){
        MessageModel messageModel = new MessageModel();
        try {
            List<OrderResponse> orderResponses = orderService.getAllOrderByUserIdAndStatus(userId, status);
            messageModel.setMessage("Success get all order by user id : " + userId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(orderResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all order, user id not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
}
