package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.PassportRequest;
import com.binar.finalproject.flightticket.dto.PassportResponse;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.model.Passport;
import com.binar.finalproject.flightticket.model.TravelerList;
import com.binar.finalproject.flightticket.repository.CountriesRepository;
import com.binar.finalproject.flightticket.repository.PassportRepository;
import com.binar.finalproject.flightticket.repository.TravelerListRepository;
import com.binar.finalproject.flightticket.service.impl.PassportServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class PassportServiceImplTest {

    @InjectMocks
    PassportServiceImpl passportService;

    @Mock
    PassportRepository passportRepository;

    @Mock
    TravelerListRepository travelerListRepository;

    @Mock
    CountriesRepository countriesRepository;

    List<PassportRequest> dataPassportRequest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test [Positive] Register Passport")
    void testPositiveRegisterPassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        Passport passport = Passport.builder()
                .passportId(UUID.randomUUID())
                .passportNumber("123")
                .passportExpiry(LocalDate.of(2022, 5, 5))
                .build();

        TravelerList travelerList = TravelerList.builder()
                .travelerId(UUID.randomUUID())
                .type("mr")
                .title("booked")
                .lastName("gunawan")
                .firstName("dika")
                .birthDate(LocalDate.of(2000,5,1))
                .build();

        Countries countries = Countries.builder()
                .countryCode("IDN")
                .countryName("INDONESIA")
                .build();

        Mockito.when(travelerListRepository.findById(passportRequest.getTravelerId())).thenReturn(Optional.of(travelerList));
        Mockito.when(countriesRepository.findById(passportRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(passportRepository.save(ArgumentMatchers.any(Passport.class))).thenReturn(passport);

        PassportResponse passportResponse = passportService.registerPassport(passportRequest);

        Assertions.assertEquals(travelerList.getTravelerId(), passportResponse.getTravelerId());
        Assertions.assertEquals(passportRequest.getCountryCode(), passportResponse.getCountryCode());

        Mockito.verify(travelerListRepository).findById(passportRequest.getTravelerId());
        Mockito.verify(countriesRepository).findById(passportRequest.getCountryCode());
        Mockito.verify(passportRepository).save(ArgumentMatchers.any(Passport.class));


    }

    @Test
    @DisplayName("Test [Negative] Register Passport")
    void testNegativeRegisterPassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        Mockito.when(travelerListRepository.findById(passportRequest.getTravelerId()))
                .thenReturn(Optional.empty());
        Mockito.when(countriesRepository.findById(passportRequest.getCountryCode()))
                .thenReturn(Optional.empty());
        var actualValue = passportService.registerPassport(passportRequest);
        Assertions.assertNull(actualValue);

    }


    @Test
    @DisplayName("Test Search Passport By TravelerList id")
    void testSearchTravelerListPassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        Passport passport = Passport.builder()
                .passportId(UUID.randomUUID())
                .passportNumber("123")
                .passportExpiry(LocalDate.of(2022, 5, 5))
                .countriesPassport(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA")
                        .build())
                .travelerListPassport(TravelerList.builder()
                        .travelerId(passportRequest.getTravelerId())
                        .type("Mr")
                        .title("Booked")
                        .firstName("Dika")
                        .lastName("gunawan")
                        .birthDate(LocalDate.of(2000,5,1))
                        .build())
                .build();

        Mockito.when(passportRepository.findAllPassportByTravelerList(passportRequest.getTravelerId())).thenReturn(passport);

        PassportResponse passportResponse = passportService.searchTravelerListPassport(passportRequest.getTravelerId());
        Assertions.assertNotNull(passportResponse);
        Assertions.assertEquals(passportRequest.getTravelerId(), passport.getTravelerListPassport().getTravelerId());

        Mockito.verify(passportRepository).findAllPassportByTravelerList(passportRequest.getTravelerId());

    }


    @Test
    @DisplayName("Test Search Passport By Passport Id")
    void testSearchPassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        Passport passport = Passport.builder()
                .passportId(UUID.randomUUID())
                .passportNumber("123")
                .passportExpiry(LocalDate.of(2022, 5, 5))
                .countriesPassport(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA")
                        .build())
                .travelerListPassport(TravelerList.builder()
                        .travelerId(passportRequest.getTravelerId())
                        .type("Mr")
                        .title("Booked")
                        .firstName("Dika")
                        .lastName("gunawan")
                        .birthDate(LocalDate.of(2000,5,1))
                        .build())
                .build();

        UUID passportId = UUID.randomUUID();
        Mockito.when(passportRepository.findById(passportId)).thenReturn(Optional.of(passport));

        PassportResponse passportResponse = passportService.searchPassport(passportId);
        Assertions.assertNotNull(passportResponse);

        Mockito.verify(passportRepository).findById(passportId);
    }

    @Test
    @DisplayName("Test [Positive] Update Passport")
    void testPositiveUpdatePassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        Passport passport = Passport.builder()
                .passportId(UUID.randomUUID())
                .passportNumber("123")
                .passportExpiry(LocalDate.of(2022, 5, 5))
                .countriesPassport(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA")
                        .build())
                .travelerListPassport(TravelerList.builder()
                        .travelerId(passportRequest.getTravelerId())
                        .type("Mr")
                        .title("Booked")
                        .firstName("Dika")
                        .lastName("gunawan")
                        .birthDate(LocalDate.of(2000,5,1))
                        .build())
                .build();
        Countries countries = Countries.builder()
                .countryCode("IDN")
                .countryName("INDONESIA")
                .build();

        UUID passportId = UUID.randomUUID();
        Mockito.when(passportRepository.findById(passportId)).thenReturn(Optional.of(passport));
        Mockito.when(countriesRepository.findById(passportRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(passportRepository.saveAndFlush(ArgumentMatchers.any(Passport.class))).thenReturn(passport);

        PassportResponse passportResponse = passportService.updatePassport(passportRequest, passportId);

        Assertions.assertNotNull(passportResponse);
        Assertions.assertEquals(passport.getPassportId(), passportResponse.getPassportId());
        Assertions.assertEquals(passportRequest.getCountryCode(), passportResponse.getCountryCode());

        Mockito.verify(passportRepository).findById(passportId);
        Mockito.verify(countriesRepository).findById(passportRequest.getCountryCode());
        Mockito.verify(passportRepository).saveAndFlush(ArgumentMatchers.any(Passport.class));

    }

    @Test
    @DisplayName("Test [Negative] Update Passport")
    void testNegativeUpdatePassport() {
        PassportRequest passportRequest = new PassportRequest();
        passportRequest.setPassportNumber("123");
        passportRequest.setPassportExpiry(LocalDate.of(2022, 5, 5));
        passportRequest.setCountryCode("IDN");
        passportRequest.setTravelerId(UUID.randomUUID());

        UUID passportId = UUID.randomUUID();
        Mockito.when(passportRepository.findById(passportId)).thenReturn(Optional.empty());

        var actualValue = passportService.updatePassport(passportRequest, passportId);

        Assertions.assertNull(actualValue);
    }
}
