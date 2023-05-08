package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.MessageModel;
import com.binar.finalproject.flightticket.dto.RouteRequest;
import com.binar.finalproject.flightticket.dto.RouteResponse;
import com.binar.finalproject.flightticket.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Route",
                            description = "Menambahkan Route baru",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Create new Route",
                                      "data": [
                                        {
                                          "departureCity": "Jakarta",
                                          "arrivalCity": "Bali",
                                          "departureAirport": "Soekarno Hatta",
                                          "arrivalAirport": "Ngurah Rai",
                                          "departureTerminal": "A1",
                                          "arrivalTerminal": "B2"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addRoute(@RequestBody RouteRequest routeRequest)
    {
        MessageModel messageModel = new MessageModel();
        RouteResponse routeResponse = routeService.addRoute(routeRequest);
        if(routeResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add route");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.CREATED.value());
            messageModel.setMessage("Add new route");
            messageModel.setData(routeResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Find Route By City",
                            description = "Menampilkan Route dengan departure City dan arrival City",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get route",
                                      "data": [
                                        {
                                          "routeId": "f4aa97ed-bd89-4595-9c16-8005fffb4ba1",
                                          "departureCity": "Jakarta",
                                          "arrivalCity": "Surabaya",
                                          "departureAirport": "Soekarno Hatta",
                                          "arrivalAirport": "Djuanda",
                                          "departureTerminal": "1A",
                                          "arrivalTerminal": "1B"
                                        }
                                      ]
                                      }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-city")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getRouteByDepartureAndArrivalCity(@RequestParam String departureCity,@RequestParam String arrivalCity){
        MessageModel messageModel = new MessageModel();
        try {
            List<RouteResponse> routeResponse = routeService.findByDepartureCityAndArrivalCity(departureCity, arrivalCity);
            messageModel.setMessage("Success get route");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(routeResponse);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get route");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Find Route By Airport",
                            description = "Menampilkan Route dengan departure Airport dan arrival Airport",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get route",
                                      "data": [
                                        {
                                          "routeId": "f4aa97ed-bd89-4595-9c16-8005fffb4ba1",
                                          "departureCity": "Jakarta",
                                          "arrivalCity": "Surabaya",
                                          "departureAirport": "Soekarno Hatta",
                                          "arrivalAirport": "Djuanda",
                                          "departureTerminal": "1A",
                                          "arrivalTerminal": "1B"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("findby-airport")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getRouteByDepartureAndArrivalAirport(@RequestParam String departureAirport,@RequestParam String arrivalAirport){
        MessageModel messageModel = new MessageModel();
        try {
            List<RouteResponse> routeResponse = routeService.findByDepartureAndArrivalAirport(departureAirport, arrivalAirport);
            messageModel.setMessage("Success get route");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(routeResponse);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get route");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Find All Route",
                            description = "Menampilkan Semua Route",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get all route",
                                      "data": [
                                        {
                                          "routeId": "f4aa97ed-bd89-4595-9c16-8005fffb4ba1",
                                          "departureCity": "Jakarta",
                                          "arrivalCity": "Surabaya",
                                          "departureAirport": "Soekarno Hatta",
                                          "arrivalAirport": "Djuanda",
                                          "departureTerminal": "1A",
                                          "arrivalTerminal": "1B"
                                        }
                                        ]""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageModel> getAllRoute()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<RouteResponse> routeGet = routeService.getAllRoute();
            messageModel.setMessage("Success get all route");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(routeGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all route");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {

                    @ExampleObject(name = "Update Route",
                            description = "Mengubah Route dengan routeId",
                            value = """
                                      {
                                           "status": 200,
                                           "message": "Success update route with id : f4aa97ed-bd89-4595-9c16-8005fffb4ba1",
                                           "data": [
                                             {
                                               "routeId": "f4aa97ed-bd89-4595-9c16-8005fffb4ba1",
                                               "departureCity": "Surabaya",
                                               "arrivalCity": "Jakarta",
                                               "departureAirport": "Djuanda",
                                               "arrivalAirport": "Soekarno Hatta",
                                               "departureTerminal": "1A",
                                               "arrivalTerminal": "1B"
                                             }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateRoute(@RequestParam UUID routeId, @RequestBody RouteRequest routeRequest) {
        MessageModel messageModel = new MessageModel();
        RouteResponse routeResponse = routeService.updateRoute(routeRequest, routeId);

        if(routeResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update route with id : " + routeId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update route with id : " + routeId);
            messageModel.setData(routeResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "delete Route By Route Id",
                            description = "menghapus Route dengan Route Id",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success delete route by id : f4aa97ed-bd89-4595-9c16-8005fffb4ba1"
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> deleteRoute(@RequestParam UUID routeId)
    {
        MessageModel messageModel = new MessageModel();
        Boolean deleteRoute = routeService.deleteRoute(routeId);
        if(Boolean.TRUE.equals(deleteRoute))
        {
            messageModel.setMessage("Success delete route by id: " + routeId);
            messageModel.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(messageModel);
        }
        else
        {
            messageModel.setMessage("Failed delete route by id: " + routeId + ", not found");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
    }
}
