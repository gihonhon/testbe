package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.*;

import java.util.List;

public interface GatesService {
    GatesResponse addGates(GatesRequest  gatesRequest);
    List<GatesResponse> searchAllGates();
    GatesResponse updateGates(GatesRequest gatesRequest, Integer gateId);
}
