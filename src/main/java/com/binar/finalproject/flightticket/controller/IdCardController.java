package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.IdCardService;
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
@RequestMapping("/id-card")
public class IdCardController {
    @Autowired
    IdCardService idCardService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Register IdCard",
                            description = "Mendaftar IdCard baru",
                            value = """
                                    {
                                        "status": 200,
                                        "message": "Register new id-card",
                                        "data": {
                                          "idCardId": "ad65c304-6f12-46a1-83b8-c52c3e92f4e8",
                                          "idCardNumber": "123",
                                          "idCardExpiry": "2022-12-30",
                                          "countryCode": "IDN",
                                          "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                        }
                                      }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> registerIdCard(@RequestBody IdCardRequest idCardRequest) {
        MessageModel messageModel = new MessageModel();

        IdCardResponse idCardResponse = idCardService.registerIdCard(idCardRequest);
        if(idCardResponse == null)
        {
            messageModel.setMessage("Failed register new id-card");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new id-card");
            messageModel.setData(idCardResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Find IdCard By travelerId",
                            description = "Menampilkan IdCard dengan travelerId",
                            value = """
                                    {
                                         "status": 200,
                                         "message": "Success get id-card by traveler id : 26b32e79-cc0c-43ec-b470-cf1c6b2b7b56",
                                         "data": {
                                           "idCardId": "ad65c304-6f12-46a1-83b8-c52c3e92f4e8",
                                           "idCardNumber": "123                                               ",
                                           "idCardExpiry": "2022-12-30",
                                           "countryCode": "IDN ",
                                           "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                         }
                                       }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-travelerId")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> getTravelerIdCard(@RequestParam UUID travelerId){
        MessageModel messageModel = new MessageModel();
        try {
            IdCardResponse idCardResponses = idCardService.searchTravelerListIdCard(travelerId);
            messageModel.setMessage("Success get id-card by traveler id : " + travelerId);
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(idCardResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get id-card by traveler id, " + travelerId + " not found");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Find IdCard By idCardId",
                            description = "Menampilkan IdCard dengan idCardId",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success get id-card",
                                      "data": {
                                        "idCardId": "ad65c304-6f12-46a1-83b8-c52c3e92f4e8",
                                        "idCardNumber": "123                                               ",
                                        "idCardExpiry": "2022-12-30",
                                        "countryCode": "IDN ",
                                        "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    @GetMapping("/findby-id")
    public ResponseEntity<MessageModel> getIdCard(@RequestParam UUID idCardId){
        MessageModel messageModel = new MessageModel();
        try {
            IdCardResponse idCardResponses = idCardService.searchIdCard(idCardId);
            messageModel.setMessage("Success get id-card");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(idCardResponses);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get id-card");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update IdCard By idCardId",
                            description = "Mengupdate IdCard dengan idCardId",
                            value = """
                                    {
                                      "status": 200,
                                      "message": "Success update id-card with id : ad65c304-6f12-46a1-83b8-c52c3e92f4e8",
                                      "data": {
                                        "idCardId": "ad65c304-6f12-46a1-83b8-c52c3e92f4e8",
                                        "idCardNumber": "111",
                                        "idCardExpiry": "2022-12-30",
                                        "countryCode": "IDN",
                                        "travelerId": "26b32e79-cc0c-43ec-b470-cf1c6b2b7b56"
                                      }
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BUYER')")
    public ResponseEntity<MessageModel> updateIdCard(@RequestParam UUID idCardId, @RequestBody IdCardRequest idCardRequest) {
        MessageModel messageModel = new MessageModel();
        IdCardResponse idCardResponse = idCardService.updateIdCard(idCardRequest, idCardId);

        if(idCardResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed update id-card with id : " + idCardId);
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Success update id-card with id : " + idCardId);
            messageModel.setData(idCardResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }
}
