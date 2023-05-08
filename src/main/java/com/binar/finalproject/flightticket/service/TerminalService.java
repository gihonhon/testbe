package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.TerminalRequest;
import com.binar.finalproject.flightticket.dto.TerminalResponse;

import java.util.List;

public interface TerminalService {
    TerminalResponse addTerminal(TerminalRequest terminalRequest);
    List<TerminalResponse> searchAllTerminal();
    TerminalResponse updateTerminal(TerminalRequest terminalRequest, Integer terminalId);
    TerminalResponse searchTerminalById(Integer terminalId);
}
