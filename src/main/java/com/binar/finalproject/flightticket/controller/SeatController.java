package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.SeatService;
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

@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Seats",
                            description = "Menambahkan Seats Baru",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Add new seat",
                                      "data": [
                                        {
                                           "seatNumber": "1A",
                                           "seatType": "GREEN",
                                           "airplaneName": "Garuda"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addSeat (@RequestBody SeatRequest seatRequest)
    {
        MessageModel messageModel = new MessageModel();
        SeatResponse seatResponse = seatService.addSeat(seatRequest);
        if(seatResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add seat");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new seat");
            messageModel.setData(seatResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add All Seats",
                            description = "Menambahkan Semua Seats Baru",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Add new seat",
                                      "data": [
                                        {
                                           "seatNumber": "1A",
                                           "seatType": "GREEN",
                                           "airplaneName": "Garuda"
                                         }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add-all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addSeat (@RequestBody List<SeatRequest> seatAllRequest)
    {
        MessageModel messageModel = new MessageModel();
        List<SeatResponse> seatResponse = seatService.addAllSeat(seatAllRequest);
        if(seatResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add seat");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Add new seat");
            messageModel.setData(seatResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get Seats By seatId",
                            description = "Menampilkan Seat dengan seatId",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success get seat",
                                       "data": {
                                            "seatId": 11,
                                            "seatNumber": "1A ",
                                            "seatType": "GREEN",
                                            "airplaneName": "JT 150"
                                        }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-id")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getSeatById(@RequestParam Integer seatId){
        MessageModel messageModel = new MessageModel();
        try {
            SeatResponse seatGet = seatService.searchSeatById(seatId);
            messageModel.setMessage("Success get seat");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(seatGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get seat");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get All Seats",
                            description = "Menampilkan Semua Seat",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get all seats",
                                      "data": [
                                        {
                                          "seatId": 11,
                                          "seatNumber": "1A ",
                                          "seatType": "GREEN",
                                          "airplaneName": "JT 150"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllSeats()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<SeatResponse> seatGet = seatService.getAllSeat();
            messageModel.setMessage("Success get all seats");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(seatGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all seats");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get Seats By airplaneName",
                            description = "Menampilkan Seat dengan airplaneName",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get seat by airplane name : JT 150",
                                      "data": [
                                        {
                                          "seatId": 11,
                                          "seatNumber": "1A ",
                                          "seatType": "GREEN",
                                          "airplaneName": "JT 150"
                                        }
                                       ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-airplane")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAirplaneSeat(@RequestParam String airplaneName){
        MessageModel messageModel = new MessageModel();
        try {
            List<SeatResponse> seatResponses = seatService.searchAirplaneSeat(airplaneName);
            messageModel.setMessage("Success get seat by airplane name : " + airplaneName);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(seatResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get seat by airplane name, " + airplaneName + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }


    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Seats By seatId",
                            description = "Menampilkan Seat dengan seatId",
                            value = """
                                   {
                                      "status": 200,
                                      "message": "Update seat with number: 11",
                                      "data": [
                                        {
                                          "seatNumber": "1A ",
                                          "seatType": "GREEN",
                                          "airplaneName": "JT 150"
                                        }
                                      ]
                                   }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateSeat(@RequestParam Integer seatId, @RequestBody SeatRequest seatRequest)
    {
        MessageModel messageModel = new MessageModel();
        SeatResponse seatResponse = seatService.updateSeat(seatRequest, seatId);

        if(seatResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update seat");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update seat with number: " + seatResponse.getSeatId());
            messageModel.setData(seatResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

}
