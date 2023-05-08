package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.Gates;
import com.binar.finalproject.flightticket.model.Terminals;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GatesRequest {
    @NotEmpty(message = "Gate name is required.")
    private String gateName;

    @NotEmpty(message = "Terminal is required.")
    private Integer terminalId;

    public Gates toGates(Terminals  terminals) {
        Gates gates = new Gates();
        gates.setGateName(this.gateName);
        gates.setTerminalsGates(terminals);
        return gates;
    }
}
