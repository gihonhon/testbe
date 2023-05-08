package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.model.*;
import com.binar.finalproject.flightticket.repository.*;
import com.binar.finalproject.flightticket.service.TravelerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TravelerListServiceImpl implements TravelerListService {
    @Autowired
    TravelerListRepository travelerListRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CountriesRepository countriesRepository;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    IdCardRepository idCardRepository;

    @Override
    public TravelerListResponse registerTravelerList(TravelerListRequest travelerListRequest) {
        try{
            Optional<Users> users = userRepository.findById(travelerListRequest.getUserId());
            Optional<Countries> countries = countriesRepository.findById(travelerListRequest.getCountryCode());

            if(users.isPresent())
            {
                if(countries.isPresent())
                {
                    return saveUser(users.get(), countries.get(), travelerListRequest);
                }
                else
                    return null;
            }
            else
                return null;
        }catch (Exception exception)
        {
            return null;
        }
    }

    private TravelerListResponse saveUser(Users users, Countries countries, TravelerListRequest travelerListRequest){
        TravelerList travelerListExist = travelerListRepository.findTravelerListExist(travelerListRequest.getFirstName(), travelerListRequest.getLastName(), travelerListRequest.getUserId());
        if(travelerListExist == null)
        {
            TravelerList travelerList = travelerListRequest.toTravelerList(users, countries);
            travelerListRepository.save(travelerList);
            return TravelerListResponse.build(travelerList);
        }
        else
            return null;
    }

    @Override
    public List<TravelerListResponse> searchAllTravelerList() {
        List<TravelerList> allTravelerList = travelerListRepository.findAll();
        List<TravelerListResponse> allTravelerListResponse = new ArrayList<>();
        for (TravelerList travelerList: allTravelerList) {
            TravelerListResponse travelerListResponse = TravelerListResponse.build(travelerList);
            allTravelerListResponse.add(travelerListResponse);
        }
        return allTravelerListResponse;
    }

    @Override
    public List<TravelerListDetailResponse> autoComplete(UUID userId) {
        List<TravelerList> allTravelerList = travelerListRepository.findAllTravelerListByUser(userId);
        List<TravelerListDetailResponse> allTravelerListResponse = new ArrayList<>();
        for (TravelerList travelerList: allTravelerList) {
            Optional<IdCard> idCard = idCardRepository.findById(travelerList.getIdCard().getIdCardId());
            Optional<Passport> passport = passportRepository.findById(travelerList.getPassport().getPassportId());
            if(idCard.isPresent() && passport.isPresent()){
                TravelerListDetailResponse travelerListResponse = TravelerListDetailResponse.build(travelerList, idCard.get(), passport.get());
                allTravelerListResponse.add(travelerListResponse);
            }
            else
                return Collections.emptyList();
        }
        return allTravelerListResponse;
    }

    @Override
    public List<TravelerListResponse> searchAllUserTravelerList(UUID userId) {
        List<TravelerList> allTravelerList = travelerListRepository.findAllTravelerListByUser(userId);
        List<TravelerListResponse> allTravelerListResponse = new ArrayList<>();
        for (TravelerList travelerList: allTravelerList) {
            TravelerListResponse travelerListResponse = TravelerListResponse.build(travelerList);
            allTravelerListResponse.add(travelerListResponse);
        }
        return allTravelerListResponse;
    }

    @Override
    public TravelerListResponse updateTravelerList(TravelerListUpdateRequest travelerListUpdateRequest, UUID travelerId) {
        Optional<TravelerList> isTravelerList = travelerListRepository.findById(travelerId);
        String message = null;
        if (isTravelerList.isPresent()) {
            TravelerList travelerList = isTravelerList.get();
            travelerList.setType(travelerListUpdateRequest.getType());
            travelerList.setTitle(travelerListUpdateRequest.getTitle());
            travelerList.setFirstName(travelerListUpdateRequest.getFirstName());
            travelerList.setLastName(travelerListUpdateRequest.getLastName());
            travelerList.setBirthDate(travelerListUpdateRequest.getBirthDate());
            if (travelerListUpdateRequest.getCountryCode() != null)
            {
                Optional<Countries> countries = countriesRepository.findById(travelerListUpdateRequest.getCountryCode());
                if(countries.isPresent())
                    travelerList.setCountriesTravelerList(countries.get());
                else
                    message = "Countries with this code doesnt exist";
            }
            if(message != null)
                return null;
            else
            {
                travelerListRepository.saveAndFlush(travelerList);
                return TravelerListResponse.build(travelerList);
            }
        } else
            return null;
    }

    @Override
    public List<TravelerListDetailResponse> registerTravelerListFromOrder(List<TravelerListDetailRequest> listTravelerListDetailRequest) {
        List<TravelerListDetailResponse> allTravelerListDetailResponse = new ArrayList<>();
        for (TravelerListDetailRequest travelerListDetailRequest: listTravelerListDetailRequest) {
            TravelerList travelerListExist = travelerListRepository.findTravelerListExist(travelerListDetailRequest.getLastName(), travelerListDetailRequest.getFirstName(), travelerListDetailRequest.getUserId());

            if(travelerListExist == null)
            {
                TravelerList travelerList = new TravelerList();
                travelerList.setFirstName(travelerListDetailRequest.getFirstName());
                travelerList.setLastName(travelerListDetailRequest.getLastName());
                travelerList.setType(travelerListDetailRequest.getType());
                travelerList.setTitle(travelerListDetailRequest.getTitle());
                travelerList.setBirthDate(travelerListDetailRequest.getBirthDate());

                Optional<Users> users = userRepository.findById(travelerListDetailRequest.getUserId());
                if(users.isPresent())
                    travelerList.setUsersTravelerList(users.get());
                else
                    return Collections.emptyList();

                Countries countries = countriesRepository.findByCountriesName(travelerListDetailRequest.getNationality());
                if(countries != null)
                    travelerList.setCountriesTravelerList(countries);
                else
                    return Collections.emptyList();

                travelerListRepository.save(travelerList);

                Passport passport = new Passport();
                passport.setPassportNumber(travelerListDetailRequest.getPassportNumber());
                passport.setPassportExpiry(travelerListDetailRequest.getPassportExpiry());
                Countries countriesPassport = countriesRepository.findByCountriesName(travelerListDetailRequest.getPassportCardCountry());

                if(countriesPassport != null)
                    passport.setCountriesPassport(countriesPassport);
                else
                    return Collections.emptyList();

                passport.setTravelerListPassport(travelerList);
                passportRepository.save(passport);

                IdCard idCard = new IdCard();
                idCard.setIdCardNumber(travelerListDetailRequest.getIdCardNumber());
                idCard.setIdCardExpiry(travelerListDetailRequest.getIdCardExpiry());
                Countries countriesIdCard = countriesRepository.findByCountriesName(travelerListDetailRequest.getIdCardCountry());
                if(countriesIdCard != null)
                    idCard.setCountriesIdCard(countriesIdCard);
                else
                    return Collections.emptyList();

                idCard.setTravelerListIdCard(travelerList);
                idCardRepository.save(idCard);

                allTravelerListDetailResponse.add(TravelerListDetailResponse.build(travelerList, idCard, passport));
            }
            else
            {
                Optional<IdCard> idCardGet = idCardRepository.findById(travelerListExist.getIdCard().getIdCardId());
                Optional<Passport> passportGet = passportRepository.findById(travelerListExist.getPassport().getPassportId());
                if(idCardGet.isPresent() && passportGet.isPresent()){
                    allTravelerListDetailResponse.add(TravelerListDetailResponse.build(travelerListExist, idCardGet.get(), passportGet.get()));
                }
                else
                    return null;
            }
        }
        return allTravelerListDetailResponse;
    }
}
