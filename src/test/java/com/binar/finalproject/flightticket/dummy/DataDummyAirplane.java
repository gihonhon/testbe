package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.AirplanesRequest;
import com.binar.finalproject.flightticket.model.Airplanes;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class DataDummyAirplane {
    private final List<Airplanes> DATA_AIRPLANES = new ArrayList<>();
    private final List<AirplanesRequest> DATA_AIRPLANES_REQUEST = new ArrayList<>();
    public DataDummyAirplane(){
        AirplanesRequest airplanesRequest1 = new AirplanesRequest();
        airplanesRequest1.setAirplaneName("JET123");
        airplanesRequest1.setAirplaneType("Airbus");

        AirplanesRequest airplanesRequest2 = new AirplanesRequest();
        airplanesRequest2.setAirplaneName("JET456");
        airplanesRequest2.setAirplaneType("Airbus");

        AirplanesRequest airplanesRequest3 = new AirplanesRequest();
        airplanesRequest3.setAirplaneName("JET789");
        airplanesRequest3.setAirplaneType("Airbus");

        AirplanesRequest airplanesRequest4 = new AirplanesRequest();
        airplanesRequest4.setAirplaneName("JET101");
        airplanesRequest4.setAirplaneType("Airbus");

        AirplanesRequest airplanesRequest5 = new AirplanesRequest();
        airplanesRequest5.setAirplaneName("JET112");
        airplanesRequest5.setAirplaneType("Airbus");

        Airplanes airplanes1 = airplanesRequest1.toAirplanes();
        Airplanes airplanes2 = airplanesRequest2.toAirplanes();
        Airplanes airplanes3 = airplanesRequest3.toAirplanes();
        Airplanes airplanes4 = airplanesRequest4.toAirplanes();
        Airplanes airplanes5 = airplanesRequest5.toAirplanes();

        DATA_AIRPLANES_REQUEST.add(airplanesRequest1);
        DATA_AIRPLANES_REQUEST.add(airplanesRequest2);
        DATA_AIRPLANES_REQUEST.add(airplanesRequest3);
        DATA_AIRPLANES_REQUEST.add(airplanesRequest4);
        DATA_AIRPLANES_REQUEST.add(airplanesRequest5);

        DATA_AIRPLANES.add(airplanes1);
        DATA_AIRPLANES.add(airplanes2);
        DATA_AIRPLANES.add(airplanes3);
        DATA_AIRPLANES.add(airplanes4);
        DATA_AIRPLANES.add(airplanes5);

    }

    public Optional<Airplanes> getAirplaneByName(String airplaneName){
        return DATA_AIRPLANES.stream()
                .filter(airplanes -> airplanes.getAirplaneName().equals(airplaneName))
                .findFirst();
    }
}