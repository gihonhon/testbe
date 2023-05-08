package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.TravelerListRequest;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.model.TravelerList;
import com.binar.finalproject.flightticket.model.Users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataDummyTravelerList {

    public static List<TravelerList> getAllTravelerList() {
        List<TravelerList> listTraveler = new ArrayList<>();
        TravelerListRequest travelerListRequest = new TravelerListRequest();
        travelerListRequest.setUserId(UUID.randomUUID());
        travelerListRequest.setType("Mr");
        travelerListRequest.setTitle("Booked");
        travelerListRequest.setFirstName("Dika");
        travelerListRequest.setLastName("Gunawan");
        travelerListRequest.setCountryCode("IDN");
        listTraveler.add(travelerListRequest.toTravelerList(getUsers(),
                getCountries()));
        return listTraveler;
    }

    public static Users getUsers() {
        Users user = new Users();
        user.setUserId(UUID.randomUUID());
        user.setFullName("Dika");
        user.setEmail("dika@gmail.com");
        user.setPassword("12345678");
        user.setTelephone("098756645756");
        user.setBirthDate(LocalDate.of(2000, 8, 12));
        user.setGender(true);
        user.setStatusActive(true);
        return user;
    }

    public static Countries getCountries(){
        Countries countries = new Countries();
        countries.setCountryCode("IDN");
        countries.setCountryName("Indonesia");
        return countries;
    }
}
