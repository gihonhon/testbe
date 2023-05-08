package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.CountriesRequest;
import com.binar.finalproject.flightticket.dto.CountriesResponse;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.repository.CountriesRepository;
import com.binar.finalproject.flightticket.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {
    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public CountriesResponse addCountries(CountriesRequest countriesRequest) {
        Countries countries = countriesRequest.toCountries();
        try {
            countriesRepository.save(countries);
            return CountriesResponse.build(countries);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    @Override
    public List<CountriesResponse> searchAllCountries() {
        List<Countries> allCountries = countriesRepository.findAll();
        List<CountriesResponse> allCountriesResponse = new ArrayList<>();
        for (Countries countries : allCountries) {
            CountriesResponse countriesResponse = CountriesResponse.build(countries);
            allCountriesResponse.add(countriesResponse);
        }
        return allCountriesResponse;
    }

    @Override
    public CountriesResponse updateCountries(CountriesRequest countriesRequest, String countryName) {
        Countries countries = countriesRepository.findByCountriesName(countryName);
        if(countries != null){
            countries.setCountryName(countriesRequest.getCountryName());
            try {
                countriesRepository.save(countries);
                return CountriesResponse.build(countries);
            }
            catch(Exception exception)
            {
                return null;
            }
        }
        else {
            throw new RuntimeException("Countries with name : " + countryName + " is not found");
        }
    }

    @Override
    public Boolean deleteCountries(String countryName) {
        Countries countries = countriesRepository.findByCountriesName(countryName);
        if(countries != null) {
            countriesRepository.deleteById(countries.getCountryCode());
            return true;
        }
        else
            return false;
    }

    @Override
    public List<CountriesResponse> searchCountriesByName(String countryName) {
        List<Countries> allCountries = countriesRepository.findAllByCountriesName(countryName+"%");
        List<CountriesResponse> allCountriesResponse = new ArrayList<>();
        for (Countries countries : allCountries) {
            CountriesResponse countriesResponse = CountriesResponse.build(countries);
            allCountriesResponse.add(countriesResponse);
        }
        return allCountriesResponse;
    }
}
