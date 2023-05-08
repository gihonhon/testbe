package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.TerminalRequest;
import com.binar.finalproject.flightticket.dto.TerminalResponse;
import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Terminals;
import com.binar.finalproject.flightticket.repository.AirportsRepository;
import com.binar.finalproject.flightticket.repository.TerminalsRepository;
import com.binar.finalproject.flightticket.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TerminalServiceImpl implements TerminalService {
    @Autowired
    private TerminalsRepository terminalsRepository;
    @Autowired
    private AirportsRepository airportsRepository;

    @Override
    public TerminalResponse addTerminal(TerminalRequest terminalRequest) {
        try {
            Optional<Airports> airports = airportsRepository.findById(terminalRequest.getIataCode());
            if(airports.isPresent())
            {
                Terminals terminalsExist = terminalsRepository.findTerminalExist(terminalRequest.getTerminalName(), terminalRequest.getIataCode());
                if(terminalsExist == null)
                {
                    Terminals  terminals = Terminals.builder()
                            .terminalName(terminalRequest.getTerminalName())
                            .airportsTerminals(airports.get())
                            .build();
                    terminalsRepository.saveAndFlush(terminals);
                    return TerminalResponse.build(terminals);
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
    public List<TerminalResponse> searchAllTerminal() {
        List<Terminals> allTerminal = terminalsRepository.findAll();
        List<TerminalResponse> allTerminalResponses = new ArrayList<>();
        for (Terminals terminals : allTerminal) {
            TerminalResponse terminalResponse = TerminalResponse.build(terminals);
            allTerminalResponses.add(terminalResponse);
        }
        return allTerminalResponses;
    }

    @Override
    public TerminalResponse updateTerminal(TerminalRequest terminalRequest, Integer terminalId) {
        Optional<Terminals> isTerminal = terminalsRepository.findById(terminalId);
        String message = null;
        if (isTerminal.isPresent()) {
            Terminals terminals = isTerminal.get();

            Terminals terminalsExist = terminalsRepository.findTerminalExist(terminalRequest.getTerminalName(), terminalRequest.getIataCode());

            if(terminalsExist == null)
                terminals.setTerminalName(terminalRequest.getTerminalName());
            else
                return null;

            if (terminalRequest.getIataCode() != null)
            {
                Optional<Airports> airports = airportsRepository.findById(terminalRequest.getIataCode());
                if(airports.isPresent())
                    terminals.setAirportsTerminals(airports.get());
                else
                    message = "Airport with this iata code doesnt exist";
            }

            if(message != null)
                return null;
            else
            {
                terminalsRepository.saveAndFlush(terminals);
                return TerminalResponse.build(terminals);
            }
        }
        else
            return null;
    }

    @Override
    public TerminalResponse searchTerminalById(Integer terminalId) {
        Optional<Terminals> isTerminals = terminalsRepository.findById(terminalId);
        return isTerminals.map(TerminalResponse::build).orElse(null);
    }
}
