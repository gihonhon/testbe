package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.TravelerListService;
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
@RequestMapping("/traveler-list")
public class TravelerListController {
    @Autowired
    private TravelerListService travelerListService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Register TravelerList",
                            description = "Mendaftar TravelerList baru",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Register new traveler list",
                                       "data": {
                                            "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                            "type": "Mr",
                                            "title": "ADT",
                                            "firstName": "testing",
                                            "lastName": "testing123",
                                            "birthDate": "2022-12-30",
                                            "userId": "40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                            "countryCode": "IDN"
                                       }
                                     }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerTravelerList(@RequestBody TravelerListRequest travelerListRequest) {
        MessageModel messageModel = new MessageModel();

        TravelerListResponse travelerListResponse = travelerListService.registerTravelerList(travelerListRequest);
        if(travelerListResponse == null)
        {
            messageModel.setMessage("Failed register new traveler list");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new traveler list");
            messageModel.setData(travelerListResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Register TravelerList From Order",
                            description = "Mendaftar TravelerList baru dari Order",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Register new traveler list from order",
                                       "data":   {
                                            "type": "Mr",
                                            "title": "ADL",
                                            "firstName": "testing",
                                            "lastName": "testing123",
                                            "birthDate": "2022-12-30",
                                            "nationality": "INDONESIA",
                                            "userId": "40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                            "idCardNumber": "123",
                                            "idCardExpiry": "2022-12-30",
                                            "idCardCountry": "IDN",
                                            "passportNumber": "1111",
                                            "passportExpiry": "2022-12-30",
                                            "passportCardCountry": "IDN"
                                          }
                                     }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add/from-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerTravelerListFromOrder(@RequestBody List<TravelerListDetailRequest> travelerListDetailRequest) {
        MessageModel messageModel = new MessageModel();

        List<TravelerListDetailResponse> travelerListDetailResponse = travelerListService.registerTravelerListFromOrder(travelerListDetailRequest);
        if(travelerListDetailResponse == null)
        {
            messageModel.setMessage("Failed register new traveler list from order");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new traveler list from order");
            messageModel.setData(travelerListDetailResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get TravelerList By userId",
                            description = "Menampilkan TravelerList dengan userId",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get all traveler list by userId : 40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                      "data": [
                                        {
                                          "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                          "type": "Mr",
                                          "title": "ADT ",
                                          "firstName": "testing",
                                          "lastName": "testing123",
                                          "birthDate": "2022-12-30",
                                          "userId": "40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                          "countryCode": "IDN "
                                        }
                                    ] }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-userId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllUserTravelerList(@RequestParam UUID userId){
        MessageModel messageModel = new MessageModel();
        try {
            List<TravelerListResponse> travelerListGet = travelerListService.searchAllUserTravelerList(userId);
            messageModel.setMessage("Success get all traveler list by userId : " + userId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(travelerListGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list by userId, " + userId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @GetMapping("/auto-complete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> autoComplete(@RequestParam UUID userId){
        MessageModel messageModel = new MessageModel();
        try {
            List<TravelerListDetailResponse> travelerListGet = travelerListService.autoComplete(userId);
            messageModel.setMessage("Success get all traveler list by userId : " + userId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(travelerListGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list by userId, " + userId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get All TravelerList",
                            description = "Menampilkan Semua TravelerList",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success get all traveler list",
                                       "data": [
                                         {
                                           "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                           "type": "Mr",
                                           "title": "ADT ",
                                           "firstName": "testing",
                                           "lastName": "testing123",
                                           "birthDate": "2022-12-30",
                                           "userId": "40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                           "countryCode": "IDN "
                                         }
                                     ]}""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getAllTravelerList(){
        MessageModel messageModel = new MessageModel();
        try {
            List<TravelerListResponse> travelerListGet = travelerListService.searchAllTravelerList();
            messageModel.setMessage("Success get all traveler list");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(travelerListGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all traveler list");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update TravelerList",
                            description = "Mengupdate TravelerList",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Update traveler-list with id : 26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                      "data": {
                                        "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                        "type": "MR",
                                        "title": "SCS",
                                        "firstName": "Budi",
                                        "lastName": "Santoso",
                                        "birthDate": "2022-12-30",
                                        "userId": "40a59ad7-b809-479b-9ca4-fb04e65350a6",
                                        "countryCode": "IDN"
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> updateUser(@RequestParam UUID travelerId, @RequestBody TravelerListUpdateRequest travelerListUpdateRequest) {
        MessageModel messageModel = new MessageModel();
        TravelerListResponse travelerListResponse = travelerListService.updateTravelerList(travelerListUpdateRequest, travelerId);

        if(travelerListResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed traveler-list with id : " + travelerId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update traveler-list with id : " + travelerId);
            messageModel.setData(travelerListResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
