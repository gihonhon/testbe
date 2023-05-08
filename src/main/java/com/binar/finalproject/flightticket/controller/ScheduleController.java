package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addSchedule (@RequestBody ScheduleRequest scheduleRequest)
    {
        MessageModel messageModel = new MessageModel();
        ScheduleResponse scheduleResponse = scheduleService.addSchedule(scheduleRequest);
        if(scheduleResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add schedule");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new schedule");
            messageModel.setData(scheduleResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
    @PostMapping("/add-all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addSchedule (@RequestBody List<ScheduleRequest> allScheduleRequest)
    {
        MessageModel messageModel = new MessageModel();
        List<ScheduleResponse> scheduleResponse = scheduleService.addAllSchedule(allScheduleRequest);
        if(scheduleResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add schedule");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new schedule");
            messageModel.setData(scheduleResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateSchedule(@RequestParam ("schedule id") UUID scheduleId, @RequestBody ScheduleRequest scheduleRequest) {
        MessageModel messageModel = new MessageModel();
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(scheduleRequest, scheduleId);

        if(scheduleResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update schedule with id : " + scheduleId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update schedule with id : " + scheduleId);
            messageModel.setData(scheduleResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @GetMapping("/by-airplane")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAirplaneSchedule(@RequestParam("airplane name") String airplaneName){
        MessageModel messageModel = new MessageModel();
        try {
            List<ScheduleResponse> scheduleResponses = scheduleService.searchAirplaneSchedule(airplaneName);
            messageModel.setMessage("Success get schedule by airplane name : " + airplaneName);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get schedule by airplane name, " + airplaneName + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/by-route")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageModel> getRouteSchedule(@RequestParam("route id") UUID routeId){
        MessageModel messageModel = new MessageModel();
        try {
            List<ScheduleResponse> scheduleResponses = scheduleService.searchRouteSchedule(routeId);
            messageModel.setMessage("Success get schedule by route id : " + routeId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get schedule by route id, " + routeId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllSchedule()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<ScheduleResponse> scheduleGet = scheduleService.getAllSchedule();
            messageModel.setMessage("Success get all schedule");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all schedule");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicket(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate){
        MessageModel messageModel = new MessageModel();
        try {
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketSchedule(departureAirport, arrivalAirport, departureDate);
            messageModel.setMessage("Success get schedule");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get schedule");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("lower-price")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByLowerPrice(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByLowerPrice(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by price (low)");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by price (low)");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("higher-price")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByHighestPrice(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByHigherPrice(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by price (high)");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by price (high)");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("earliest-departure")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByEarliestDepartureTime(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByEarliestDepartureTime(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by earliest departure");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by earliest departure");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("latest-departure")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByLatestDepartureTime(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByLatestDepartureTime(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by latest departure");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by latest departure");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("earliest-arrival")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByEarliestArrivalTime(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByEarliestArrivalTime(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by earliest arrival");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by earliest arrival");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("latest-arrival")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicketOrderByLatestArrivalTime(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            List<SearchScheduleResponse> scheduleResponses = scheduleService.searchAirplaneTicketOrderByLatestArrivalTime(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success sort schedule by latest arrival");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed sort schedule by latest arrival");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("paging")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicket(@RequestParam("depAirport") String departureAirport,@RequestParam("arrAirport") String arrivalAirport, @RequestParam("depDate") String departureDate, @RequestParam Integer page){
        MessageModel messageModel = new MessageModel();
        try {
            int size = 5;
            Pageable pageable = PageRequest.of(page, size);
            Iterable<SearchScheduleResponse> scheduleResponses = scheduleService.findByDepartureArrivalAirportAndDepartureDate(departureAirport, arrivalAirport, departureDate, pageable);
            messageModel.setMessage("Success get schedule");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get schedule");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }
    
    @GetMapping("/id")
    public ResponseEntity<MessageModel> getAirplaneScheduleTicket(@RequestParam("id") UUID scheduleId){
        MessageModel messageModel = new MessageModel();
        try {
            SearchScheduleResponse scheduleResponses = scheduleService.searchScheduleDetails(scheduleId);
            messageModel.setMessage("Success get schedule details");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(scheduleResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get schedule details");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

}
