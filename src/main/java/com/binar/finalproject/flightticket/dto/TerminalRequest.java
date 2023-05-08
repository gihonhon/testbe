package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Terminals;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TerminalRequest {
    @NotEmpty(message = "Terminal name is required.")
    private String terminalName;
    @NotEmpty(message = "Airports is required.")
    private String iataCode;

    public Terminals toTerminal(Airports airports) {
        return Terminals.builder()
                .terminalName(this.terminalName)
                .airportsTerminals(airports)
                .build();
    }
}
