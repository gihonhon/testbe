package com.binar.finalproject.flightticket.service;

import com.binar.finalproject.flightticket.dto.UserRequest;
import com.binar.finalproject.flightticket.dto.UserResponse;
import com.binar.finalproject.flightticket.dto.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse registerUser(UserRequest userRequest);
    List<UserResponse> searchAllUser();
    UserResponse searchUserById(UUID userId);
    Boolean isEmailExist(String email);
    Boolean isPhoneNumberExist(String telephone);
    UserResponse updateUser(UserUpdateRequest userUpdateRequest, UUID userId);
    Boolean deleteUser(String email);
}