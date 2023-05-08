package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Cities;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.repository.AirportsRepository;
import com.binar.finalproject.flightticket.repository.CitiesRepository;
import com.binar.finalproject.flightticket.repository.CountriesRepository;
import com.binar.finalproject.flightticket.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AirportsServiceImpl implements AirportService {
    @Autowired
    private AirportsRepository airportsRepository;
    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public AirportResponse addAirports(AirportRequest airportRequest) {
        try {
            Optional<Cities> cities = citiesRepository.findById(airportRequest.getCityCode());
                if(cities.isPresent())
                {
                    Airports airports = Airports.builder()
                            .iataCode(airportRequest.getIataCode())
                            .airportName(airportRequest.getAirportName())
                            .citiesAirport(cities.get())
                            .build();
                    airportsRepository.save(airports);
                    return AirportResponse.build(airports);
                }
                else
                    return null;
        }
        catch (Exception ignore){
            return null;
        }
    }

    @Override
    public List<AirportGetAllResponse> searchAllAirports() {
        List<Airports> allAirports = airportsRepository.findAll();
        List<AirportGetAllResponse> allAirportResponse = new ArrayList<>();
        for (Airports airports : allAirports) {
            Optional<Cities> cities = citiesRepository.findById(airports.getCitiesAirport().getCityCode());
            if(cities.isPresent())
            {
                Optional<Countries> countries = countriesRepository.findById(cities.get().getCountriesCities().getCountryCode());
                if(countries.isPresent()){
                    AirportGetAllResponse airportResponse = AirportGetAllResponse.build(airports, cities.get(), countries.get());
                    allAirportResponse.add(airportResponse);
                }
                else
                    return Collections.emptyList();
            }
            else
                return Collections.emptyList();
        }
        return allAirportResponse;
    }

    @Override
    public AirportResponse updateAirports(AirportRequest airportRequest, String iataCode) {
        Optional<Airports> isAirport = airportsRepository.findById(iataCode);
        String message = null;
        if (isAirport.isPresent()) {
            Airports airports = isAirport.get();
            airports.setIataCode(airportRequest.getIataCode());
            airports.setAirportName(airportRequest.getAirportName());
            if (airportRequest.getCityCode() != null)
            {
                Optional<Cities> cities = citiesRepository.findById(airportRequest.getCityCode());
                if(cities.isPresent())
                    airports.setCitiesAirport(cities.get());
                else
                    message = "City with this code doesnt exist";
            }
            if(message != null)
                return null;
            else
            {
                airportsRepository.saveAndFlush(airports);
                return AirportResponse.build(airports);
            }
        } else
            return null;
    }

    @Override
    public List<AirportResponse> searchAirportsByName(String airportName) {
        List<Airports> allAirports = airportsRepository.findAllByAirportName(airportName + "%");
        List<AirportResponse> allAirportResponse = new ArrayList<>();
        for (Airports airports : allAirports) {
            AirportResponse airportResponse = AirportResponse.build(airports);
            allAirportResponse.add(airportResponse);
        }
        return allAirportResponse;
    }

    @Override
    public List<AirportResponse> searchAirportByCityName(String cityName) {
        List<Airports> allAirports = airportsRepository.findAllAirportByCity(cityName + "%");
        List<AirportResponse> allAirportResponse = new ArrayList<>();
        for (Airports airports : allAirports) {
            AirportResponse airportResponse = AirportResponse.build(airports);
            allAirportResponse.add(airportResponse);
        }
        return allAirportResponse;
    }
}
