package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.dummy.DataDummyTravelerList;
import com.binar.finalproject.flightticket.model.*;
import com.binar.finalproject.flightticket.repository.*;
import com.binar.finalproject.flightticket.service.impl.TravelerListServiceImpl;
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

class TravelerListServiceImplTest {

    @InjectMocks
    TravelerListServiceImpl travelerListService;

    @Mock
    TravelerListRepository travelerListRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    CountriesRepository countriesRepository;

    @Mock
    PassportRepository passportRepository;

    @Mock
    IdCardRepository idCardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test [Positive] Register TravelerList")
    void registerPositiveTravelerList() {
        TravelerListRequest travelerListRequest = new TravelerListRequest();
        travelerListRequest.setType("mr");
        travelerListRequest.setTitle("booked");
        travelerListRequest.setLastName("gunawan");
        travelerListRequest.setFirstName("dika");
        travelerListRequest.setUserId(UUID.randomUUID());
        travelerListRequest.setCountryCode("IDN");

        Users users = Users.builder()
                .userId(UUID.randomUUID())
                .fullName("JOKO")
                .email("joko@gmail.com")
                .password("12345678")
                .telephone("009987755")
                .birthDate(LocalDate.of(2001, 5, 1))
                .gender(true)
                .statusActive(true)
                .build();

        Countries countries = Countries.builder()
                .countryCode("IDN")
                .countryName("INDONESIA")
                .build();

        TravelerList travelerList = TravelerList.builder()
                .type("mr")
                .title("booked")
                .lastName("gunawan")
                .firstName("dika")
                .birthDate(LocalDate.of(2000,5,1))
                .build();

        String countryCode = "IDN";

        Mockito.when(userRepository.findById(travelerListRequest.getUserId())).thenReturn(Optional.of(users));
        Mockito.when(countriesRepository.findById(travelerListRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(travelerListRepository.findTravelerListExist(travelerListRequest.getFirstName(), travelerListRequest.getLastName(), travelerListRequest.getUserId()))
                .thenReturn(null);
        Mockito.when(countriesRepository.findByCountriesName(countries.getCountryName())).thenReturn(countries);
        Mockito.when(travelerListRepository.save(ArgumentMatchers.any(TravelerList.class))).thenReturn(travelerList);

        TravelerListResponse travelerListResponse = travelerListService.registerTravelerList(travelerListRequest);
        Assertions.assertEquals(users.getUserId(), travelerListResponse.getUserId());
        Assertions.assertEquals(travelerListRequest.getCountryCode(), travelerListResponse.getCountryCode());
        Assertions.assertEquals(travelerListRequest.getLastName(), travelerListResponse.getLastName());
        Assertions.assertEquals(travelerListRequest.getFirstName(), travelerListResponse.getFirstName());

        Mockito.verify(userRepository).findById(travelerListRequest.getUserId());
        Mockito.verify(countriesRepository).findById(countryCode);
        Mockito.verify(travelerListRepository).findTravelerListExist(travelerListRequest.getFirstName(), travelerListRequest.getLastName(),  travelerListRequest.getUserId());
        Mockito.verify(travelerListRepository).save(ArgumentMatchers.any(TravelerList.class));
    }

    @Test
    @DisplayName("Test [Negative] Register TravelerList")
    void testNegativeRegisterTravelerList() {
        TravelerListRequest travelerListRequest = new TravelerListRequest();
        travelerListRequest.setType("mr");
        travelerListRequest.setTitle("booked");
        travelerListRequest.setLastName("gunawan");
        travelerListRequest.setFirstName("dika");
        travelerListRequest.setUserId(UUID.randomUUID());
        travelerListRequest.setCountryCode("IDN");

        Mockito.when(userRepository.findById(travelerListRequest.getUserId())).thenReturn(Optional.empty());
        Mockito.when(countriesRepository.findById(travelerListRequest.getCountryCode())).thenReturn(Optional.empty());

        var actualValue = travelerListService.registerTravelerList(travelerListRequest);
        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Search All TravelerList")
    void TestSearchAllTravelerList() {
        List<TravelerList> travelerLists = DataDummyTravelerList.getAllTravelerList();
        Mockito.when(travelerListRepository.findAll()).thenReturn(travelerLists);
        var actualValue = travelerListService.searchAllTravelerList();
        var expectedSize =  1;

        Assertions.assertEquals(expectedSize, actualValue.size());

    }

    @Test
    @DisplayName("Test Search All User TravelerList")
    void testSearchAllUserTravelerList() {
        TravelerListRequest travelerListRequest = new TravelerListRequest();
        travelerListRequest.setUserId(UUID.randomUUID());
        travelerListRequest.setType("Mr");
        travelerListRequest.setTitle("Booked");
        travelerListRequest.setFirstName("Dika");
        travelerListRequest.setLastName("Gunawan");
        travelerListRequest.setCountryCode("IDN");

        TravelerList travelerList = TravelerList.builder()
                .travelerId(UUID.randomUUID())
                .type("mr")
                .title("booked")
                .firstName("dika")
                .lastName("gunawan")
                .birthDate(LocalDate.of(2000,5,1))
                .usersTravelerList(Users.builder()
                        .userId(travelerListRequest.getUserId())
                        .fullName("JOKO")
                        .email("joko@gmail.com")
                        .password("12345678")
                        .telephone("009987755")
                        .birthDate(LocalDate.of(2001, 5, 1))
                        .gender(true)
                        .statusActive(true)
                        .build())
                .countriesTravelerList(Countries.builder()
                        .countryCode(travelerListRequest.getCountryCode())
                        .countryName("INDONESIA")
                        .build())
                .build();

        List<TravelerList> travelerLists = new ArrayList<>();
        travelerLists.add(travelerList);
        travelerLists.add(travelerList);

        Mockito.when(travelerListRepository.findAllTravelerListByUser(travelerListRequest.getUserId())).thenReturn(travelerLists);

        List<TravelerListResponse> travelerListResponse = travelerListService.searchAllUserTravelerList(travelerListRequest.getUserId());
        Assertions.assertNotNull(travelerListResponse);
        Assertions.assertEquals(2, travelerListResponse.size());

        Mockito.verify(travelerListRepository).findAllTravelerListByUser(travelerListRequest.getUserId());

    }

    @Test
    @DisplayName("Test [Positive] Update TravelerList")
    void testPositiveUpdateTravelerList() {
        TravelerListUpdateRequest travelerListRequest = new TravelerListUpdateRequest();
        travelerListRequest.setType("Mr");
        travelerListRequest.setTitle("Booked");
        travelerListRequest.setFirstName("Dika");
        travelerListRequest.setLastName("Gunawan");
        travelerListRequest.setBirthDate(LocalDate.of(2000, 8, 12));
        travelerListRequest.setCountryCode("IDN");

        TravelerListRequest request = new TravelerListRequest();
        request.setUserId(UUID.randomUUID());
        request.setType(travelerListRequest.getType());
        request.setTitle(travelerListRequest.getTitle());
        request.setFirstName(travelerListRequest.getFirstName());
        request.setLastName(travelerListRequest.getLastName());
        request.setCountryCode(travelerListRequest.getCountryCode());

        TravelerList travelerList = TravelerList.builder()
                .travelerId(UUID.randomUUID())
                .type("mr")
                .title("booked")
                .firstName("dika")
                .lastName("gunawan")
                .birthDate(LocalDate.of(2000,5,1))
                .usersTravelerList(Users.builder()
                        .userId(request.getUserId())
                        .fullName("JOKO")
                        .email("joko@gmail.com")
                        .password("12345678")
                        .telephone("009987755")
                        .birthDate(LocalDate.of(2001, 5, 1))
                        .gender(true)
                        .statusActive(true)
                        .build())
                .countriesTravelerList(Countries.builder()
                        .countryCode(travelerListRequest.getCountryCode())
                        .countryName("INDONESIA")
                        .build())
                .build();

        Countries countries = Countries.builder()
                .countryCode(travelerListRequest.getCountryCode())
                .countryName("INDONESIA")
                .build();

        UUID travelerId = travelerList.getTravelerId();

        Mockito.when(travelerListRepository.findById(travelerId)).thenReturn(Optional.of(travelerList));
        Mockito.when(countriesRepository.findById(travelerListRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(travelerListRepository.saveAndFlush(ArgumentMatchers.any(TravelerList.class))).thenReturn(travelerList);

        TravelerListResponse travelerListResponse = travelerListService.updateTravelerList(travelerListRequest, travelerId);
        Assertions.assertNotNull(travelerListResponse);
        Assertions.assertEquals(travelerId, travelerListResponse.getTravelerId());
        Assertions.assertEquals(travelerListRequest.getCountryCode(), travelerListResponse.getCountryCode());

        Mockito.verify(travelerListRepository).findById(travelerId);
        Mockito.verify(countriesRepository).findById(travelerListRequest.getCountryCode());
        Mockito.verify(travelerListRepository).saveAndFlush(ArgumentMatchers.any(TravelerList.class));

    }

    @Test
    @DisplayName("Test [Negative] Update TravelerList")
    void testNegativeUpdateTravelerList() {
        TravelerListUpdateRequest travelerListRequest = new TravelerListUpdateRequest();
        travelerListRequest.setType("Mr");
        travelerListRequest.setTitle("Booked");
        travelerListRequest.setFirstName("Dika");
        travelerListRequest.setLastName("Gunawan");
        travelerListRequest.setBirthDate(LocalDate.of(2000, 8, 12));
        travelerListRequest.setCountryCode("IDN");

        TravelerListRequest request = new TravelerListRequest();
        request.setUserId(UUID.randomUUID());
        request.setType(travelerListRequest.getType());
        request.setTitle(travelerListRequest.getTitle());
        request.setFirstName(travelerListRequest.getFirstName());
        request.setLastName(travelerListRequest.getLastName());
        request.setCountryCode(travelerListRequest.getCountryCode());

        UUID travelerId = UUID.randomUUID();

        Mockito.when(travelerListRepository.findById(travelerId)).thenReturn(Optional.empty());

        var actualValue = travelerListService.updateTravelerList(travelerListRequest, travelerId);

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Register TravelerList From Order")
    void testRegisterTravelerListFromOrder() {
        TravelerListUpdateRequest travelerListRequest = new TravelerListUpdateRequest();
        travelerListRequest.setType("Mr");
        travelerListRequest.setTitle("Booked");
        travelerListRequest.setFirstName("Dika");
        travelerListRequest.setLastName("Gunawan");
        travelerListRequest.setBirthDate(LocalDate.of(2000, 8, 12));
        travelerListRequest.setCountryCode("IDN");

        TravelerListRequest request = new TravelerListRequest();
        request.setUserId(UUID.randomUUID());
        request.setType(travelerListRequest.getType());
        request.setTitle(travelerListRequest.getTitle());
        request.setFirstName(travelerListRequest.getFirstName());
        request.setLastName(travelerListRequest.getLastName());
        request.setCountryCode(travelerListRequest.getCountryCode());

        TravelerListDetailRequest travelerListDetailRequest = TravelerListDetailRequest.builder()
                .type("mr")
                .title("booked")
                .firstName(travelerListRequest.getFirstName())
                .lastName(travelerListRequest.getLastName())
                .birthDate(LocalDate.of(2000,5,1))
                .nationality("idn")
                .userId(request.getUserId())
                .idCardNumber("778899")
                .idCardExpiry(LocalDate.of(2000,5,2))
                .idCardCountry("idnn")
                .passportNumber("09979")
                .passportExpiry(LocalDate.of(2000,5,3))
                .passportCardCountry("000")
                .build();


        TravelerList travelerList = TravelerList.builder()
                .travelerId(UUID.randomUUID())
                .type("mr")
                .title("booked")
                .firstName("dika")
                .lastName("gunawan")
                .birthDate(LocalDate.of(2000,5,1))
                .usersTravelerList(Users.builder()
                        .userId(request.getUserId())
                        .fullName("JOKO")
                        .email("joko@gmail.com")
                        .password("12345678")
                        .telephone("009987755")
                        .birthDate(LocalDate.of(2001, 5, 1))
                        .gender(true)
                        .statusActive(true)
                        .build())
                .countriesTravelerList(Countries.builder()
                        .countryCode(travelerListRequest.getCountryCode())
                        .countryName("INDONESIA")
                        .build())
                .build();
        Passport passport = Passport.builder()
                .passportId(UUID.randomUUID())
                .passportNumber(travelerListDetailRequest.getPassportNumber())
                .passportExpiry(travelerListDetailRequest.getPassportExpiry())
                .countriesPassport(Countries.builder()
                        .countryCode(travelerListRequest.getCountryCode())
                        .countryName(travelerList.getCountriesTravelerList().getCountryName())
                        .build())
                .travelerListPassport(TravelerList.builder()
                        .travelerId(travelerList.getTravelerId())
                        .type(travelerList.getType())
                        .title(travelerList.getTitle())
                        .firstName(travelerList.getFirstName())
                        .lastName(travelerList.getLastName())
                        .birthDate(travelerList.getBirthDate())
                        .build())
                .build();

        Users users = Users.builder()
                .userId(request.getUserId())
                .fullName(travelerList.getUsersTravelerList().getFullName())
                .email(travelerList.getUsersTravelerList().getEmail())
                .password(travelerList.getUsersTravelerList().getPassword())
                .telephone(travelerList.getUsersTravelerList().getTelephone())
                .birthDate(travelerList.getUsersTravelerList().getBirthDate())
                .gender(travelerList.getUsersTravelerList().getGender())
                .statusActive(travelerList.getUsersTravelerList().getStatusActive())
                .build();


        Countries countries = Countries.builder()
                .countryCode(travelerListRequest.getCountryCode())
                .countryName(travelerList.getCountriesTravelerList().getCountryName())
                .build();

        IdCard idCard = IdCard.builder()
                .idCardId(UUID.randomUUID())
                .idCardNumber(travelerListDetailRequest.getIdCardNumber())
                .idCardExpiry(travelerListDetailRequest.getIdCardExpiry())
                .countriesIdCard(Countries.builder()
                        .countryCode(travelerListRequest.getCountryCode())
                        .countryName(travelerList.getCountriesTravelerList().getCountryName()).build())
                .travelerListIdCard(TravelerList.builder()
                        .travelerId(travelerList.getTravelerId()
                        ).build())
                .build();

        List<TravelerListDetailRequest> listDetailRequests = new ArrayList<>();
        listDetailRequests.add(travelerListDetailRequest);

        Mockito.when(travelerListRepository.findTravelerListExist(travelerListRequest.getFirstName(), travelerListRequest.getLastName(), request.getUserId()))
                .thenReturn(null);
        Mockito.when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(users));
        Mockito.when(countriesRepository.findByCountriesName(travelerListDetailRequest.getNationality())).thenReturn(countries);
        Mockito.when(travelerListRepository.save(ArgumentMatchers.any(TravelerList.class))).thenReturn(travelerList);

        Mockito.when(countriesRepository.findByCountriesName(travelerListDetailRequest.getPassportCardCountry())).thenReturn(countries);
        Mockito.when(passportRepository.save(ArgumentMatchers.any(Passport.class))).thenReturn(passport);
        Mockito.when(countriesRepository.findByCountriesName(travelerListDetailRequest.getIdCardCountry())).thenReturn(countries);
        Mockito.when(idCardRepository.save(ArgumentMatchers.any(IdCard.class))).thenReturn(idCard);

        List<TravelerListDetailResponse> travelerListDetailResponses = travelerListService.registerTravelerListFromOrder(listDetailRequests);
        Assertions.assertNotNull(travelerListDetailResponses);
        Assertions.assertEquals(1, travelerListDetailResponses.size());

        Mockito.verify(travelerListRepository).findTravelerListExist(travelerListRequest.getFirstName(), travelerListRequest.getLastName(), request.getUserId());
        Mockito.verify(userRepository).findById(request.getUserId());
        Mockito.verify(countriesRepository).findByCountriesName(travelerListDetailRequest.getNationality());
        Mockito.verify(travelerListRepository).save(ArgumentMatchers.any(TravelerList.class));
        Mockito.verify(countriesRepository).findByCountriesName(travelerListDetailRequest.getPassportCardCountry());
        Mockito.verify(passportRepository).save(ArgumentMatchers.any(Passport.class));
        Mockito.verify(countriesRepository).findByCountriesName(travelerListDetailRequest.getIdCardCountry());
        Mockito.verify(idCardRepository).save(ArgumentMatchers.any(IdCard.class));
    }
}
