package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    ScheduleResponse addSchedule(ScheduleRequest scheduleRequest);
    List<ScheduleResponse> addAllSchedule(List<ScheduleRequest> allScheduleRequest);
    ScheduleResponse updateSchedule(ScheduleRequest scheduleRequest, UUID scheduleId);
    List<ScheduleResponse> searchAirplaneSchedule(String airplaneName);
    List<ScheduleResponse> searchRouteSchedule(UUID routeId);
    List<SearchScheduleResponse> searchAirplaneTicketSchedule(String departureAirport, String arrivalAirport, String departureDate);
    List<ScheduleResponse> getAllSchedule();
    List<SearchScheduleResponse> searchAirplaneTicketOrderByLowerPrice(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    List<SearchScheduleResponse> searchAirplaneTicketOrderByHigherPrice(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    List<SearchScheduleResponse> searchAirplaneTicketOrderByEarliestDepartureTime(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    List<SearchScheduleResponse> searchAirplaneTicketOrderByLatestDepartureTime(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    List<SearchScheduleResponse> searchAirplaneTicketOrderByEarliestArrivalTime(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    List<SearchScheduleResponse> searchAirplaneTicketOrderByLatestArrivalTime(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    Iterable<SearchScheduleResponse> findByDepartureArrivalAirportAndDepartureDate(String departureAirport, String arrivalAirport, String departureDate, Pageable pageable);
    SearchScheduleResponse searchScheduleDetails(UUID scheduleId);
}
