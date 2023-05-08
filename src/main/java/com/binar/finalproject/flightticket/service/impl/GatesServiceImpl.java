package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.model.*;
import com.binar.finalproject.flightticket.repository.GatesRepository;
import com.binar.finalproject.flightticket.repository.TerminalsRepository;
import com.binar.finalproject.flightticket.service.GatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GatesServiceImpl implements GatesService {
    @Autowired
    private GatesRepository gatesRepository;
    @Autowired
    private TerminalsRepository terminalsRepository;

    @Override
    public GatesResponse addGates(GatesRequest gatesRequest) {
        try {
            Optional<Terminals> terminals = terminalsRepository.findById(gatesRequest.getTerminalId());
            if(terminals.isPresent())
            {
                Gates gatesExist = gatesRepository.findGateExist(gatesRequest.getGateName(), gatesRequest.getTerminalId());
                if(gatesExist == null)
                {
                    Gates gates = Gates.builder()
                            .gateName(gatesRequest.getGateName())
                            .terminalsGates(terminals.get())
                            .build();
                    gatesRepository.saveAndFlush(gates);
                    return GatesResponse.build(gates);
                }
                else
                    return null;
            }
            else
                return null;
        }
        catch (Exception ignore){
            return null;
        }
    }

    @Override
    public List<GatesResponse> searchAllGates() {
        List<Gates> allGates = gatesRepository.findAll();
        List<GatesResponse> allGatesResponse = new ArrayList<>();
        for (Gates  gates : allGates) {
            GatesResponse gatesResponse = GatesResponse.build(gates);
            allGatesResponse.add(gatesResponse);
        }
        return allGatesResponse;
    }

    @Override
    public GatesResponse updateGates(GatesRequest gatesRequest, Integer gateId) {
        Optional<Gates> isGates = gatesRepository.findById(gateId);
        String message = null;
        if (isGates.isPresent()) {
            Gates gates = isGates.get();

            Gates gatesExist = gatesRepository.findGateExist(gatesRequest.getGateName(), gatesRequest.getTerminalId());
            if(gatesExist == null)
                gates.setGateName(gatesRequest.getGateName());
            else
                return null;


            Optional<Terminals> terminals = terminalsRepository.findById(gatesRequest.getTerminalId());
            if(terminals.isPresent())
                gates.setTerminalsGates(terminals.get());
            else
                message = "Terminals with this id doesnt exist";

            if(message != null)
                return null;
            else
            {
                gatesRepository.saveAndFlush(gates);
                return GatesResponse.build(gates);
            }
        } else
            return null;
    }
}
