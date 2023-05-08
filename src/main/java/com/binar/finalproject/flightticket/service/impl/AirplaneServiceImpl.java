package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.AirplanesRequest;
import com.binar.finalproject.flightticket.dto.AirplanesResponse;
import com.binar.finalproject.flightticket.exception.DataAlreadyExistException;
import com.binar.finalproject.flightticket.exception.DataNotFoundException;
import com.binar.finalproject.flightticket.model.Airplanes;
import com.binar.finalproject.flightticket.repository.AirplanesRepository;
import com.binar.finalproject.flightticket.service.AirplanesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AirplaneServiceImpl implements AirplanesService {
    @Autowired
    private AirplanesRepository airplanesRepository;
    private String notFound = "Airplane not found";
    @Override
    public AirplanesResponse insertAirplane(AirplanesRequest airplanesRequest) {
        Airplanes isAirplaneExist = airplanesRepository.findByName(airplanesRequest.getAirplaneName());
        if (isAirplaneExist == null) {
            Airplanes airplanes = airplanesRequest.toAirplanes();
            airplanesRepository.save(airplanes);
            log.info("Success add new airplane");
            return AirplanesResponse.build(airplanes);
        }
        else {
            throw new DataAlreadyExistException ("Airplane with this name already exist");
        }
    }

    @Override
    public AirplanesResponse searchAirplaneByName(String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null) {
            return AirplanesResponse.build(isAirplanes);
        }
        else {
            throw new DataNotFoundException(notFound);
        }
    }

    @Override
    public List<AirplanesResponse> getAllAirplane() {
        List<Airplanes> allAirplane = airplanesRepository.findAll();
        List<AirplanesResponse> allAirplaneResponse = new ArrayList<>();
        for (Airplanes airplanes : allAirplane){
            AirplanesResponse airplanesResponse = AirplanesResponse.build(airplanes);
            allAirplaneResponse.add(airplanesResponse);
            log.info("Success get all airplane");
        }
        return allAirplaneResponse;
    }

    @Override
    public AirplanesResponse updateAirplane(AirplanesRequest airplanesRequest, String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null){
            if (airplanesRequest.getAirplaneName() != null)
            {
                Airplanes airplanes = airplanesRepository.findByName(airplanesRequest.getAirplaneName());
                if(airplanes == null || isAirplanes.getAirplaneName().equals(airplanesRequest.getAirplaneName()))
                    isAirplanes.setAirplaneName(airplanesRequest.getAirplaneName());
                else
                    throw new DataAlreadyExistException("Airplane with this name already exist");
            }
            if (airplanesRequest.getAirplaneType() != null)
                isAirplanes.setAirplaneType(airplanesRequest.getAirplaneType());
            airplanesRepository.save(isAirplanes);
            log.info("Success update airplane");
            return AirplanesResponse.build(isAirplanes);
        }
        else
            throw new DataNotFoundException(notFound);
    }

    @Override
    public Boolean deleteAirplane(String airplaneName) {
        Airplanes isAirplanes = airplanesRepository.findByName(airplaneName);
        if (isAirplanes != null){
            airplanesRepository.deleteById(isAirplanes.getAirplaneName());
            log.info("Success delete airplane");
            return true;
        }
        else {
            throw new DataNotFoundException(notFound);
        }
    }
}
