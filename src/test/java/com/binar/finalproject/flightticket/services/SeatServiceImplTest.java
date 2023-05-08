package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.SeatRequest;
import com.binar.finalproject.flightticket.dto.SeatResponse;
import com.binar.finalproject.flightticket.model.Airplanes;
import com.binar.finalproject.flightticket.model.Seats;
import com.binar.finalproject.flightticket.repository.AirplanesRepository;
import com.binar.finalproject.flightticket.repository.SeatRepository;
import com.binar.finalproject.flightticket.service.impl.SeatServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class SeatServiceImplTest {

    @InjectMocks
    SeatServiceImpl seatService;

    @Mock
    SeatRepository seatRepository;

    @Mock
    AirplanesRepository airplanesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Test [Positive] Add Seat")
    void testPositiveAddSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .build();

        Airplanes airplanes = Airplanes.builder()
                .airplaneName("JT 150")
                .airplaneType("Boeing")
                .build();

        Mockito.when(airplanesRepository.findById(seatRequest.getAirplaneName())).thenReturn(Optional.of(airplanes));
        Mockito.when(seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName())).thenReturn(null);
        Mockito.when(seatRepository.save(ArgumentMatchers.any(Seats.class))).thenReturn(seats);

        SeatResponse seatResponse = seatService.addSeat(seatRequest);
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(seatRequest.getAirplaneName(), seatResponse.getAirplaneName());
        Assertions.assertEquals(seatRequest.getSeatNumber(), seatResponse.getSeatNumber());


        Mockito.verify(airplanesRepository).findById(airplanes.getAirplaneName());
        Mockito.verify(seatRepository).findSeatExist(seats.getSeatNumber(), airplanes.getAirplaneName());
        Mockito.verify(seatRepository).save(ArgumentMatchers.any(Seats.class));
    }

    @Test
    @DisplayName("Test [Negative] Add Seat ")
    void testNegativeAddSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Mockito.when(airplanesRepository.findById(seatRequest.getAirplaneName())).thenReturn(Optional.empty());

        var actualValue = seatService.addSeat(seatRequest);

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test [Positive] Add All Seats")
    void testPositiveAddAllSeats() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("Garuda");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .build();

        Airplanes airplanes = Airplanes.builder()
                .airplaneName(seatRequest.getAirplaneName())
                .airplaneType("JT 150")
                .build();

        List<SeatRequest> seatsList = new ArrayList<>();
        seatsList.add(seatRequest);
        seatsList.add(seatRequest);

        Mockito.when(airplanesRepository.findById(seatRequest.getAirplaneName())).thenReturn(Optional.of(airplanes));
        Mockito.when(seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName())).thenReturn(null);
        Mockito.when(seatRepository.save(ArgumentMatchers.any(Seats.class))).thenReturn(seats);

        List<SeatResponse> seatResponse = seatService.addAllSeat(seatsList);
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(2, seatResponse.size());

        Mockito.verify(airplanesRepository, Mockito.times(2)).findById(airplanes.getAirplaneName());
        Mockito.verify(seatRepository, Mockito.times(2)).findSeatExist(seats.getSeatNumber(), airplanes.getAirplaneName());
        Mockito.verify(seatRepository, Mockito.times(2)).save(ArgumentMatchers.any(Seats.class));

    }

    @Test
    @DisplayName("Test [Negative] Add All Seat")
    void testNegativeAddAllSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("Garuda");

        Integer seatId = 1;
        List<SeatResponse>  seatResponses = List.of();

        Mockito.when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        var actualValue = seatService.addAllSeat(List.of());

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Search Seat By Seat Id")
    void testSearchSeatById() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .airplanesSeats(Airplanes.builder()
                        .airplaneName("JT 150")
                        .airplaneType("Boeing")
                        .build())
                .build();
        Integer seatId = 1;

        Mockito.when(seatRepository.findById(seatId)).thenReturn(Optional.of(seats));

        SeatResponse seatResponse = seatService.searchSeatById(seatId);
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(seats.getSeatId(), seatResponse.getSeatId());

        Mockito.verify(seatRepository).findById(seatId);
    }

    @Test
    @DisplayName("Test [Positive] Update Seats")
    void testPositiveUpdateSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .airplanesSeats(Airplanes.builder()
                        .airplaneName("JT 150")
                        .airplaneType("Boeing")
                        .build())
                .build();

        Airplanes airplanes = Airplanes.builder()
                .airplaneName("JT 150")
                .airplaneType("Boeing")
                .build();

        Integer seatId = 1;

        Mockito.when(seatRepository.findById(seatId)).thenReturn(Optional.of(seats));
        Mockito.when(seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName())).thenReturn(null);
        Mockito.when(airplanesRepository.findById(seatRequest.getAirplaneName())).thenReturn(Optional.of(airplanes));
        Mockito.when(seatRepository.saveAndFlush(ArgumentMatchers.any(Seats.class))).thenReturn(seats);

        SeatResponse seatResponse = seatService.updateSeat(seatRequest,seatId);
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(seatRequest.getAirplaneName(), seatResponse.getAirplaneName());
        Assertions.assertEquals(seatRequest.getSeatNumber(), seatResponse.getSeatNumber());


        Mockito.verify(seatRepository).findById(seats.getSeatId());
        Mockito.verify(seatRepository).findSeatExist(seats.getSeatNumber(), airplanes.getAirplaneName());
        Mockito.verify(airplanesRepository).findById(airplanes.getAirplaneName());
        Mockito.verify(seatRepository).saveAndFlush(ArgumentMatchers.any(Seats.class));

    }

    @Test
    @DisplayName("Test [Negative] Update Seat")
    void testNegativeUpdateSeat() {
        Integer seatId = 1;
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Mockito.when(seatRepository.findById(seatId)).thenReturn(Optional.empty());

        var actualValue = seatService.updateSeat(seatRequest, seatId);

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Search Seat By Airplane Name")
    void testSearchAirplaneSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .airplanesSeats(Airplanes.builder()
                        .airplaneName("JT 150")
                        .airplaneType("Boeing")
                        .build())
                .build();

        List<Seats> seatsList = new ArrayList<>();
        seatsList.add(seats);
        seatsList.add(seats);

        Mockito.when(seatRepository.findAllSeatsByAirplaneName(seatRequest.getAirplaneName())).thenReturn(seatsList);

        List<SeatResponse> seatResponse = seatService.searchAirplaneSeat(seatRequest.getAirplaneName());
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(2, seatResponse.size());

        Mockito.verify(seatRepository).findAllSeatsByAirplaneName(seatRequest.getAirplaneName());
    }

    @Test
    @DisplayName("Test Get All Seat")
    void testGetAllSeat() {
        SeatRequest seatRequest = new SeatRequest();
        seatRequest.setSeatNumber("1A");
        seatRequest.setSeatType("Green");
        seatRequest.setAirplaneName("JT 150");

        Seats seats = Seats.builder()
                .seatId(1)
                .seatNumber("1A")
                .seatType("Green")
                .airplanesSeats(Airplanes.builder()
                        .airplaneName("JT 150")
                        .airplaneType("Boeing")
                        .build())
                .build();

        List<Seats> seatsList = new ArrayList<>();
        seatsList.add(seats);

        Mockito.when(seatRepository.findAll()).thenReturn(seatsList);

        List<SeatResponse> seatResponse = seatService.getAllSeat();
        Assertions.assertNotNull(seatResponse);
        Assertions.assertEquals(1, seatResponse.size());

        Mockito.verify(seatRepository).findAll();

    }

}
