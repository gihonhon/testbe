package com.binar.finalproject.flightticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.binar.finalproject.flightticket.dto.*;
import com.binar.finalproject.flightticket.service.AirplanesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {
    @Autowired
    private AirplanesService airplanesService;

    @Operation(responses = {
            @ApiResponse(responseCode = "201", content = @Content(examples = {
                    @ExampleObject(name = "Add Airplane",
                            description = "Menambahkan pesawat",
                            value = """
                                    {
                                         "status": 201,
                                         "message": "Add new airplane",
                                         "data": {
                                            "airplaneName": "JET 601",
                                            "airplaneType": "Airbus"
                                        }
                                      }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addAirplane(@RequestBody AirplanesRequest airplanesRequest)
    {
        MessageModel messageModel = new MessageModel();
        AirplanesResponse airplanesResponse = airplanesService.insertAirplane(airplanesRequest);
        if(airplanesResponse == null)
        {
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            messageModel.setMessage("Failed to add airplane");
            return ResponseEntity.badRequest().body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.CREATED.value());
            messageModel.setMessage("Add new airplane");
            messageModel.setData(airplanesResponse);
            return ResponseEntity.ok().body(messageModel);

        }
    }
    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get all airplane",
                            description = "Menampilkan pesawat berdasarkan nama pesawat",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success get airplane",
                                       "data": {
                                         "airplaneName": "JET 700",
                                         "airplaneType": "Airbus"
                                       }
                                     }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/findby-name")
    public ResponseEntity<MessageModel> getAirplaneByName(@RequestParam String airplaneName){
        MessageModel messageModel = new MessageModel();
        try {
            AirplanesResponse airplaneGet = airplanesService.searchAirplaneByName(airplaneName);
            messageModel.setMessage("Success get airplane");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(airplaneGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get airplane");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Get all airplane",
                            description = "Menampilkan semua pesawat",
                            value = """
                                    {
                                       "status": 200,
                                       "message": "Success get all airplane",
                                       "data": [
                                         {
                                           "airplaneName": "JET 700",
                                           "airplaneType": "Airbus"
                                         },
                                         {
                                           "airplaneName": "JET 601",
                                           "airplaneType": "Airbus"
                                         },
                                         {
                                           "airplaneName": "JET 788",
                                           "airplaneType": "BOEING 123"
                                         }
                                       ]
                                     }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/get-all")
    public ResponseEntity<MessageModel> getAllAirplane()
    {
        MessageModel messageModel = new MessageModel();
        try {
            List<AirplanesResponse> airplaneGet = airplanesService.getAllAirplane();
            messageModel.setMessage("Success get all airplane");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(airplaneGet);
            return ResponseEntity.ok().body(messageModel);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all airplane");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY.value()).body(messageModel);
        }
    }


    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Update Airplane",
                            description = "Update data pesawat",
                            value = """
                                    {
                                         "status": 200,
                                           "message": "Update airplane with name : JET 600",
                                           "data": {
                                             "airplaneName": "JET 600",
                                             "airplaneType": "Airbus"
                                           }
                                      }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> updateAirplane(@RequestParam String airplaneName, @RequestBody AirplanesRequest airplanesRequest)
    {
        MessageModel messageModel = new MessageModel();
        AirplanesResponse airplanesResponse = airplanesService.updateAirplane(airplanesRequest, airplaneName);

        if(airplanesResponse == null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage("Failed to update airplane");
            return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(messageModel);
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update airplane with name : " + airplanesResponse.getAirplaneName());
            messageModel.setData(airplanesResponse);
            return ResponseEntity.ok().body(messageModel);
        }
    }


    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Delete Airplane",
                            description = "Menghapus pesawat",
                            value = """
                                    {
                                          "status": 200,
                                          "message": "Success delete airplane by name: JET 600"
                                      }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<MessageModel> deleteAirplane(@RequestParam String airplaneName)
    {
        MessageModel messageModel = new MessageModel();
        Boolean deleteAirplane = airplanesService.deleteAirplane(airplaneName);
        if(Boolean.TRUE.equals(deleteAirplane))
        {
            messageModel.setMessage("Success delete airplane by name: " + airplaneName);
            messageModel.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(messageModel);
        }
        else
        {
            messageModel.setMessage("Failed delete airplane by name: " + airplaneName + ", not found");
            messageModel.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(messageModel);
        }
    }
}
