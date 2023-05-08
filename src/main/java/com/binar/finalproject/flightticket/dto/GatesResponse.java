package com.binar.finalproject.flightticket.dto;

import com.binar.finalproject.flightticket.model.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GatesResponse {
    private Integer gateId;
    private String gateName;
    private Integer terminalId;

    public static GatesResponse build(Gates gates) {
        return GatesResponse.builder()
                .gateId(gates.getGateId())
                .gateName(gates.getGateName())
                .terminalId(gates.getTerminalsGates().getTerminalId())
                .build();
    }
}
