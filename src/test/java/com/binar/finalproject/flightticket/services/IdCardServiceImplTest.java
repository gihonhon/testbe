package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dto.IdCardRequest;
import com.binar.finalproject.flightticket.dto.IdCardResponse;
import com.binar.finalproject.flightticket.model.Countries;
import com.binar.finalproject.flightticket.model.IdCard;
import com.binar.finalproject.flightticket.model.TravelerList;
import com.binar.finalproject.flightticket.repository.CountriesRepository;
import com.binar.finalproject.flightticket.repository.IdCardRepository;
import com.binar.finalproject.flightticket.repository.TravelerListRepository;
import com.binar.finalproject.flightticket.service.impl.IdCardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

class IdCardServiceImplTest {

    @InjectMocks
    IdCardServiceImpl idCardService;

    @Mock
    IdCardRepository idCardRepository;

    @Mock
    TravelerListRepository travelerListRepository;

    @Mock
    CountriesRepository countriesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test [Positive] Register Id Card")
    void testPositiveRegisterIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        IdCard idCard = IdCard.builder()
                .idCardId(UUID.randomUUID())
                .idCardNumber("99977")
                .idCardExpiry(LocalDate.of(2022, 5, 5))
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

        Mockito.when(travelerListRepository.findById(idCardRequest.getTravelerId())).thenReturn(Optional.of(travelerList));
        Mockito.when(countriesRepository.findById(idCardRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(idCardRepository.save(ArgumentMatchers.any(IdCard.class))).thenReturn(idCard);

        idCardService.registerIdCard(idCardRequest);
        Mockito.verify(travelerListRepository).findById(idCardRequest.getTravelerId());
        Mockito.verify(countriesRepository).findById(idCardRequest.getCountryCode());
        Mockito.verify(idCardRepository).save(ArgumentMatchers.any(IdCard.class));
    }

    @Test
    @DisplayName("Test [Negative] Register Id Card")
    void testNegativeRegisterIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        UUID idCardId = UUID.randomUUID();

        Mockito.when(idCardRepository.findById(idCardId)).thenReturn(Optional.empty());

        var actualValue = idCardService.registerIdCard(idCardRequest);

        Assertions.assertNull(actualValue);
    }

    @Test
    @DisplayName("Test Search Id Card By TravelerList Id ")
    void testSearchTravelerListIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        IdCard idCard = IdCard.builder()
                .idCardId(UUID.randomUUID())
                .idCardNumber("99977")
                .idCardExpiry(LocalDate.of(2022, 5, 5))
                .countriesIdCard(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA").build())
                .travelerListIdCard(TravelerList.builder()
                        .travelerId(idCardRequest.getTravelerId()
                        ).build())
                .build();

        Mockito.when(idCardRepository.findAllIdCardByTravelerList(idCardRequest.getTravelerId())).thenReturn(idCard);

        IdCardResponse idCardResponse = idCardService.searchTravelerListIdCard(idCardRequest.getTravelerId());
        Assertions.assertNotNull(idCardResponse);
        Assertions.assertEquals(idCardRequest.getTravelerId(), idCard.getTravelerListIdCard().getTravelerId());

        Mockito.verify(idCardRepository).findAllIdCardByTravelerList(idCardRequest.getTravelerId());


    }

    @Test
    @DisplayName("Test Search Id Card")
    void testSearchIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        IdCard idCard = IdCard.builder()
                .idCardId(UUID.randomUUID())
                .idCardNumber("99977")
                .idCardExpiry(LocalDate.of(2022, 5, 5))
                .countriesIdCard(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA").build())
                .travelerListIdCard(TravelerList.builder()
                        .travelerId(idCardRequest.getTravelerId()
                        ).build())
                .build();

        UUID idCardId = UUID.randomUUID();

        Mockito.when(idCardRepository.findById(idCardId)).thenReturn(Optional.of(idCard));

        IdCardResponse idCardResponse = idCardService.searchIdCard(idCardId);
        Assertions.assertNotNull(idCardResponse);

        Mockito.verify(idCardRepository).findById(idCardId);
    }

    @Test
    @DisplayName("Test [Positive] Update Id Card")
    void testPositiveUpdateIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        IdCard idCard = IdCard.builder()
                .idCardId(UUID.randomUUID())
                .idCardNumber("99977")
                .idCardExpiry(LocalDate.of(2022, 5, 5))
                .countriesIdCard(Countries.builder()
                        .countryCode("IDN")
                        .countryName("INDONESIA").build())
                .travelerListIdCard(TravelerList.builder()
                        .travelerId(idCardRequest.getTravelerId()
                        ).build())
                .build();

        Countries countries = Countries.builder()
                .countryCode("IDN")
                .countryName("INDONESIA")
                .build();

        UUID idCardId = UUID.randomUUID();

        Mockito.when(idCardRepository.findById(idCardId)).thenReturn(Optional.of(idCard));
        Mockito.when(countriesRepository.findById(idCardRequest.getCountryCode())).thenReturn(Optional.of(countries));
        Mockito.when(idCardRepository.saveAndFlush(ArgumentMatchers.any(IdCard.class))).thenReturn(idCard);

        idCardService.updateIdCard(idCardRequest, idCardId);
        Mockito.verify(idCardRepository).findById(idCardId);
        Mockito.verify(countriesRepository).findById(idCardRequest.getCountryCode());
        Mockito.verify(idCardRepository).saveAndFlush(ArgumentMatchers.any(IdCard.class));

    }

    @Test
    @DisplayName("Test [Negative] Update Id Card")
    void testNegativeUpdateIdCard() {
        IdCardRequest idCardRequest = new IdCardRequest();
        idCardRequest.setIdCardNumber("99977");
        idCardRequest.setIdCardExpiry(LocalDate.of(2022, 5, 5));
        idCardRequest.setCountryCode("IDN");
        idCardRequest.setTravelerId(UUID.randomUUID());

        UUID idCardId = UUID.randomUUID();

        Mockito.when(idCardRepository.findById(idCardId)).thenReturn(Optional.empty());

        var actualValue = idCardService.updateIdCard(idCardRequest, idCardId);

        Assertions.assertNull(actualValue);

    }
}
