package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.*;

import java.util.UUID;

public interface PassportService {
    PassportResponse registerPassport(PassportRequest passportRequest);
    PassportResponse searchTravelerListPassport(UUID travelerId);
    PassportResponse searchPassport(UUID passportId);
    PassportResponse updatePassport(PassportRequest userUpdateRequest, UUID passportId);
}
