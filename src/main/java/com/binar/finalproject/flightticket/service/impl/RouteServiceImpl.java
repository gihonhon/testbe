package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.model.Airports;
import com.binar.finalproject.flightticket.model.Cities;
import com.binar.finalproject.flightticket.model.Routes;
import com.binar.finalproject.flightticket.model.Terminals;
import com.binar.finalproject.flightticket.repository.AirportsRepository;
import com.binar.finalproject.flightticket.repository.CitiesRepository;
import com.binar.finalproject.flightticket.repository.RouteRepository;
import com.binar.finalproject.flightticket.repository.TerminalsRepository;
import com.binar.finalproject.flightticket.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    CitiesRepository citiesRepository;
    @Autowired
    AirportsRepository airportsRepository;
    @Autowired
    TerminalsRepository terminalsRepository;

    @Override
    public RouteResponse addRoute(RouteRequest routeRequest) {
        Routes routes = routeRequest.toRoutes();
        Airports arrivalAirports = airportsRepository.findByAirportName(routeRequest.getArrivalAirport());
        Airports departureAirports = airportsRepository.findByAirportName(routeRequest.getDepartureAirport());
        Cities arrivalCities = citiesRepository.findByCityName(routeRequest.getArrivalCity());
        Cities departureCities = citiesRepository.findByCityName(routeRequest.getDepartureCity());
        Terminals arrivalTerminals = terminalsRepository.findTerminalExist(routeRequest.getArrivalTerminal(), arrivalAirports.getIataCode());
        Terminals departureTerminals = terminalsRepository.findTerminalExist(routeRequest.getDepartureTerminal(), departureAirports.getIataCode());

        if(arrivalCities == null)
            return null;
        if(departureCities == null)
            return null;
        if(arrivalTerminals == null)
            return null;
        if(departureTerminals == null)
            return null;

        try {
            routeRepository.save(routes);
            return RouteResponse.build(routes);
        }
        catch(Exception exception)
        {
            return null;
        }
    }

    @Override
    public List<RouteResponse> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
        List<Routes> allRoute = routeRepository.findRouteByDepartureCityAndArrivalCity(departureCity, arrivalCity);
        List<RouteResponse> allRouteResponse = new ArrayList<>();
        for (Routes routes : allRoute)
        {
            RouteResponse routeResponse = RouteResponse.build(routes);
            allRouteResponse.add(routeResponse);
        }
        return allRouteResponse;
    }

    @Override
    public List<RouteResponse> findByDepartureAndArrivalAirport(String departureAirport, String arrivalAirport) {
        List<Routes> allRoute = routeRepository.findRouteByDepartureAndArrivalAirport(departureAirport,arrivalAirport);
        List<RouteResponse> allRouteResponse = new ArrayList<>();
        for (Routes routes : allRoute)
        {
            RouteResponse routeResponse = RouteResponse.build(routes);
            allRouteResponse.add(routeResponse);
        }
        return allRouteResponse;
    }

    @Override
    public List<RouteResponse> getAllRoute() {
        List<Routes> allRoute = routeRepository.findAll();
        List<RouteResponse> allRouteResponse = new ArrayList<>();
        for (Routes routes : allRoute)
        {
            RouteResponse routeResponse = RouteResponse.build(routes);
            allRouteResponse.add(routeResponse);
        }
        return allRouteResponse;
    }

    @Override
    public RouteResponse updateRoute(RouteRequest routeRequest, UUID routeId) {
        Optional<Routes> isRoutes = routeRepository.findById(routeId);

        Airports arrivalAirports = airportsRepository.findByAirportName(routeRequest.getArrivalAirport());
        Airports departureAirports = airportsRepository.findByAirportName(routeRequest.getDepartureAirport());
        Cities arrivalCities = citiesRepository.findByCityName(routeRequest.getArrivalCity());
        Cities departureCities = citiesRepository.findByCityName(routeRequest.getDepartureCity());
        Terminals arrivalTerminals = terminalsRepository.findByTerminalName(routeRequest.getArrivalTerminal());
        Terminals departureTerminals = terminalsRepository.findByTerminalName(routeRequest.getDepartureTerminal());

        if(arrivalAirports == null)
            return null;
        if(departureAirports == null)
            return null;
        if(arrivalCities == null)
            return null;
        if(departureCities == null)
            return null;
        if(arrivalTerminals == null)
            return null;
        if(departureTerminals == null)
            return null;

        if (isRoutes.isPresent())
        {
            Routes routes = isRoutes.get();
            routes.setDepartureCity(routeRequest.getDepartureCity());
            routes.setArrivalCity(routeRequest.getArrivalCity());
            routes.setDepartureAirport(routeRequest.getDepartureAirport());
            routes.setArrivalAirport(routeRequest.getArrivalAirport());
            routes.setDepartureTerminal(routeRequest.getDepartureTerminal());
            routes.setArrivalTerminal(routeRequest.getArrivalTerminal());
            try {
                routeRepository.save(routes);
                return RouteResponse.build(routes);
            }
            catch(Exception exception)
            {
                return null;
            }
        }
        else
            throw new RuntimeException("Route with id: " + routeId + " not found");
    }

    @Override
    public Boolean deleteRoute(UUID routeId) {
        Optional<Routes> isRoute = routeRepository.findById(routeId);
        if (isRoute.isPresent()){
            Routes routes = isRoute.get();
            routeRepository.deleteById(routes.getRouteId());
            return true;
        }
        return false;
    }
}