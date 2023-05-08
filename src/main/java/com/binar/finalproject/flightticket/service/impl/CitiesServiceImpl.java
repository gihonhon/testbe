package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.CitiesRequest;
import com.binar.finalproject.flightticket.dto.CitiesResponse;
import com.binar.finalproject.flightticket.model.Cities;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.repository.CitiesRepository;
import com.binar.finalproject.flightticket.repository.CountriesRepository;
import com.binar.finalproject.flightticket.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitiesServiceImpl implements CitiesService {
    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public CitiesResponse addCity(CitiesRequest citiesRequest) {
        try {
            Optional<Countries> countries = countriesRepository.findById(citiesRequest.getCountryCode());
            if(countries.isPresent())
            {
                Cities cities = Cities.builder()
                        .cityCode(citiesRequest.getCityCode())
                        .cityName(citiesRequest.getCityName())
                        .countriesCities(countries.get())
                        .build();
                citiesRepository.saveAndFlush(cities);
                return CitiesResponse.build(cities);
            }
            else
                return null;
        }
        catch (Exception ignore){
            return null;
        }
    }

    @Override
    public List<CitiesResponse> searchAllCity() {
        List<Cities> getAllCity = citiesRepository.findAll();
        List<CitiesResponse> allCityResponse = new ArrayList<>();
        for (Cities cities : getAllCity) {
            CitiesResponse citiesResponse = CitiesResponse.build(cities);
            allCityResponse.add(citiesResponse);
        }
        return allCityResponse;
    }

    @Override
    public CitiesResponse updateCity(CitiesRequest citiesRequest, String cityCode) {
        Optional<Cities> isCity = citiesRepository.findById(cityCode);
        String message = null;
        if (isCity.isPresent()) {
            Cities cities = isCity.get();
            cities.setCityCode(citiesRequest.getCityCode());
            cities.setCityName(citiesRequest.getCityName());
            Optional<Countries> countries = countriesRepository.findById(citiesRequest.getCountryCode());
            if (countries.isPresent())
                cities.setCountriesCities(countries.get());
            else
                message = "Countries with this code doesnt exist";

            if (message != null)
                return null;
            else {
                citiesRepository.saveAndFlush(cities);
                return CitiesResponse.build(cities);
            }
        }
        else {
            return null;
        }

    }

    @Override
    public List<CitiesResponse> searchCityByName(String cityName) {
        List<Cities> allCity = citiesRepository.findAllByCityName(cityName  + "%");
        List<CitiesResponse> allCityResponse = new ArrayList<>();
        for (Cities cities : allCity) {
            CitiesResponse citiesResponse = CitiesResponse.build(cities);
            allCityResponse.add(citiesResponse);
        }
        return allCityResponse;
    }
}
