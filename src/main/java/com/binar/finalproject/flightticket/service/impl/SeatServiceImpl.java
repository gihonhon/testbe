package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.SeatRequest;
import com.binar.finalproject.flightticket.dto.SeatResponse;
import com.binar.finalproject.flightticket.model.Airplanes;
import com.binar.finalproject.flightticket.model.Seats;
import com.binar.finalproject.flightticket.repository.AirplanesRepository;
import com.binar.finalproject.flightticket.repository.SeatRepository;
import com.binar.finalproject.flightticket.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AirplanesRepository airplanesRepository;

    @Override
    public SeatResponse addSeat(SeatRequest seatRequest) {
       try {
           Optional<Airplanes> airplanes = airplanesRepository.findById(seatRequest.getAirplaneName());
           if (airplanes.isPresent())
           {
               Seats seatsExist = seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName());
               if(seatsExist == null) {
                   Seats seats = seatRequest.toSeats(airplanes.get());
                   seatRepository.save(seats);
                   return SeatResponse.build(seats);
               }
               else
                   return null;
           }
           else
               return null;
       }
       catch (Exception exception)
       {
           return null;
       }
    }

    @Override
    public List<SeatResponse> addAllSeat(List<SeatRequest> seatAllRequest) {
        List<SeatResponse> allSeatResponse = new ArrayList<>();
        for (SeatRequest seatRequest : seatAllRequest)
        {
            try {
                Optional<Airplanes> airplanes = airplanesRepository.findById(seatRequest.getAirplaneName());
                if (airplanes.isPresent())
                {
                    Seats seatsExist = seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName());
                    if(seatsExist == null)
                    {
                        Seats seats = seatRequest.toSeats(airplanes.get());
                        seatRepository.save(seats);
                        allSeatResponse.add(SeatResponse.build(seats));
                    }
                    else
                        return Collections.emptyList();
                }
                else
                    return Collections.emptyList();
            }
            catch (Exception exception)
            {
                return Collections.emptyList();
            }
        }
        if(allSeatResponse.isEmpty())
            return null;
        else
            return allSeatResponse;
    }

    @Override
    public SeatResponse searchSeatById(Integer seatId) {
        Optional<Seats> seats = seatRepository.findById(seatId);
        return seats.map(SeatResponse::build).orElse(null);
    }

    @Override
    public List<SeatResponse> getAllSeat() {
        List<Seats> allSeat = seatRepository.findAll();
        List<SeatResponse> allSeatResponse = new ArrayList<>();
        for (Seats seats : allSeat)
        {
            SeatResponse seatResponse = SeatResponse.build(seats);
            allSeatResponse.add(seatResponse);
        }
        return allSeatResponse;
    }

    @Override
    public SeatResponse updateSeat(SeatRequest seatRequest, Integer seatId) {
        Optional<Seats> isSeat = seatRepository.findById(seatId);
        String message = null;
        if (isSeat.isPresent())
        {
            Seats seats = isSeat.get();
            Seats seatsExist = seatRepository.findSeatExist(seatRequest.getSeatNumber(), seatRequest.getAirplaneName());

            if(seatsExist == null)
                seats.setSeatNumber(seatRequest.getSeatNumber());
            else
                return null;

            seats.setSeatType(seatRequest.getSeatType());
            Optional<Airplanes> airplanes = airplanesRepository.findById(seatRequest.getAirplaneName());
            if (airplanes.isPresent())
                seats.setAirplanesSeats(airplanes.get());
            else
                message = "Airplane with this name doesnt exist";

            if (message != null)
                return null;
            else
            {
                seatRepository.saveAndFlush(seats);
                return SeatResponse.build(seats);
            }
        }
        else
            return null;
    }

    @Override
    public List<SeatResponse> searchAirplaneSeat(String airplaneName) {
        List<Seats> allSeat = seatRepository.findAllSeatsByAirplaneName(airplaneName);
        List<SeatResponse> allSeatResponse = new ArrayList<>();
        for (Seats seats : allSeat)
        {
            SeatResponse seatResponse = SeatResponse.build(seats);
            allSeatResponse.add(seatResponse);
        }
        return allSeatResponse;
    }
}
