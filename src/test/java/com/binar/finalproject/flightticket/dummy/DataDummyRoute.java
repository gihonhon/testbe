package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.RouteRequest;
import com.binar.finalproject.flightticket.model.Routes;

import java.util.ArrayList;
import java.util.List;

public class DataDummyRoute {

    public static List<Routes> getAllRoutes() {
        List<Routes> listRoutes = new ArrayList<>();
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setDepartureCity("semarang");
        routeRequest.setArrivalCity("bali");
        routeRequest.setDepartureAirport("ahmad yani");
        routeRequest.setArrivalAirport("ngurah rai");
        routeRequest.setDepartureTerminal("A1");
        routeRequest.setArrivalTerminal("B1");
        listRoutes.add(routeRequest.toRoutes());
        return listRoutes;
    }


    public static List<Routes> getRoutes(){
        Routes routes = new Routes();
        Routes routes1 = new Routes();
        routes.setDepartureCity("semarang");
        routes.setArrivalCity("bali");
        routes.setDepartureAirport("ahmad yani");
        routes.setArrivalAirport("ngurah rai");
        routes.setDepartureTerminal("A1");
        routes.setArrivalTerminal("B1");
        routes1.setDepartureCity("semarang");
        routes1.setArrivalCity("bali");
        routes1.setDepartureAirport("ahmad yani");
        routes1.setArrivalAirport("ngurah rai");
        routes1.setDepartureTerminal("A1");
        routes1.setArrivalTerminal("B1");
        return List.of(routes, routes1);
    }
}
