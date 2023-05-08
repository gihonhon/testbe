package com.binar.finalproject.flightticket.controller;


import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.PassportService;
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

import java.util.UUID;

@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    PassportService passportService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Register Passport",
                            description = "Mendaftar Passport baru",
                            value = """
                                    {
                                        "status": 200,
                                        "message": "Register new passport",
                                        "data": {
                                            "passportId": "8cb50ba1-2823-4bad-9f52-f0cdbedb89b0",
                                            "passportNumber": "12345",
                                            "passportExpiry": "2022-12-30",
                                            "countryCode": "IDN",
                                            "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerPassport(@RequestBody PassportRequest passportRequest) {
        MessageModel messageModel = new MessageModel();

        PassportResponse passportResponse = passportService.registerPassport(passportRequest);
        if(passportResponse == null)
        {
            messageModel.setMessage("Failed register new passport");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new passport");
            messageModel.setData(passportResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get Passport By TrvelerId",
                            description = "Menampilkan Passport dengan travelerId",
                            value = """
                                    {
                                        "status": 200,
                                        "message": "Success get passport by traveler id : 26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                        "data": {
                                            "passportId": "8cb50ba1-2823-4bad-9f52-f0cdbedb89b0",
                                            "passportNumber": "12345                                             ",
                                            "passportExpiry": "2022-12-30",
                                            "countryCode": "IDN ",
                                            "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-traveler")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getTravelerPassport(@RequestParam UUID travelerId){
        MessageModel messageModel = new MessageModel();
        try {
            PassportResponse passportResponse = passportService.searchTravelerListPassport(travelerId);
            messageModel.setMessage("Success get passport by traveler id : " + travelerId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(passportResponse);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get passport by traveler id, " + travelerId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }


    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get Passport By passportId",
                            description = "Menampilkan Passport dengan passportId",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success get passport",
                                       "data": {
                                            "passportId": "8cb50ba1-2823-4bad-9f52-f0cdbedb89b0",
                                            "passportNumber": "12345                                             ",
                                            "passportExpiry": "2022-12-30",
                                            "countryCode": "IDN ",
                                            "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                       }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @GetMapping("/findby-id")
    public ResponseEntity<MessageModel> getPassport(@RequestParam UUID passportId){
        MessageModel messageModel = new MessageModel();
        try {
            PassportResponse passportResponse = passportService.searchPassport(passportId);
            messageModel.setMessage("Success get passport");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(passportResponse);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get passport");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Passport By passportId",
                            description = "Mengupdate Passport dengan passportId",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success update passport with id : 8cb50ba1-2823-4bad-9f52-f0cdbedb89b0",
                                       "data": {
                                            "passportId": "8cb50ba1-2823-4bad-9f52-f0cdbedb89b0",
                                            "passportNumber": "11111",
                                            "passportExpiry": "2022-12-30",
                                            "countryCode": "IDN",
                                            "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                       }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> updatePassport(@RequestParam UUID passportId, @RequestBody PassportRequest passportRequest) {
        MessageModel messageModel = new MessageModel();
        PassportResponse passportResponse = passportService.updatePassport(passportRequest, passportId);

        if(passportResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update passport with id : " + passportId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update passport with id : " + passportId);
            messageModel.setData(passportResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
