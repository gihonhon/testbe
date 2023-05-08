package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.CountriesRequest;
import com.binar.finalproject.flightticket.dto.CountriesResponse;

import java.util.List;

public interface CountriesService {
    CountriesResponse addCountries(CountriesRequest countriesRequest);
    List<CountriesResponse> searchAllCountries();
    CountriesResponse updateCountries(CountriesRequest countriesRequest, String countryName);
    Boolean deleteCountries(String countryName);
    List<CountriesResponse> searchCountriesByName(String countryName);
}
