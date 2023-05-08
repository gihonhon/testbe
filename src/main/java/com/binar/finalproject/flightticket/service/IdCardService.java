package com.binar.finalproject.flightticket.service;


import com.binar.finalproject.flightticket.dto.IdCardRequest;
import com.binar.finalproject.flightticket.dto.IdCardResponse;

import java.util.UUID;

public interface IdCardService {
    IdCardResponse registerIdCard(IdCardRequest idCardRequest);
    IdCardResponse searchTravelerListIdCard(UUID travelerId);
    IdCardResponse searchIdCard(UUID idCardId);
    IdCardResponse updateIdCard(IdCardRequest idCardRequest, UUID idCardId);
}
