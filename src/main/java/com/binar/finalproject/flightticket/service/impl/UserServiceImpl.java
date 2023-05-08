package com.binar.finalproject.flightticket.service.impl;

import com.binar.finalproject.flightticket.dto.UserRequest;
import com.binar.finalproject.flightticket.dto.UserResponse;
import com.binar.finalproject.flightticket.dto.UserUpdateRequest;
import com.binar.finalproject.flightticket.model.AuthenticationProvider;
import com.binar.finalproject.flightticket.model.Roles;
import com.binar.finalproject.flightticket.model.Users;
import com.binar.finalproject.flightticket.repository.RoleRepository;
import com.binar.finalproject.flightticket.repository.UserRepository;
import com.binar.finalproject.flightticket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserResponse registerUser(UserRequest userRequest) {

        if(Boolean.FALSE.equals(isEmailExist(userRequest.getEmail())))
        {
            if(Boolean.FALSE.equals(isPhoneNumberExist(userRequest.getTelephone())))
            {
                try {
                    Roles roles = roleRepository.findByName("ROLE_BUYER");
                    if(roles != null)
                    {
                        Users users = userRequest.toUsers();
                        users.getRolesUsers().add(roles);
                        users.setPassword(encoder.encode(users.getPassword()));

                        if(userRequest.getAuthProvider().equals(AuthenticationProvider.GOOGLE.toString())){
                            users.setAuthProvider(AuthenticationProvider.GOOGLE);
                            users.setGoogleId(userRequest.getGoogleId());
                        }

                        userRepository.saveAndFlush(users);
                        return UserResponse.build(users);
                    }
                    else
                        return null;
                }
                catch (Exception ignore){
                    return null;
                }
            }
            else
                return null;
        }
        else
            return null;
    }

    @Override
    public List<UserResponse> searchAllUser() {
        List<Users> allUser = userRepository.findAll();
        return toListUserResponses(allUser);
    }

    @Override
    public UserResponse searchUserById(UUID userId) {
        Optional<Users> users = userRepository.findById(userId);
        if(users.isEmpty())
            return null;
        else
            return UserResponse.build(users.get());
    }

    @Override
    public Boolean isEmailExist(String email) {
        Optional<Users> users = userRepository.findByEmail(email);
        return users.isPresent();
    }

    @Override
    public Boolean isPhoneNumberExist(String telephone) {
        Users users = userRepository.findPhoneNumber(telephone);
        return users != null;
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, UUID userId) {
        Optional<Users> userGet = userRepository.findById(userId);
        Users users = null;
        if(userGet.isPresent())
            users = userGet.get();
        else
            return null;

        String message = null;

        users.setFullName(userUpdateRequest.getFullName());

        if(Boolean.TRUE.equals(!isEmailExist(userUpdateRequest.getEmail())) || users.getEmail().equals(userUpdateRequest.getEmail()))
            users.setEmail(userUpdateRequest.getEmail());
        else
            message = "Email already exist";

        if(Boolean.TRUE.equals(!isPhoneNumberExist(userUpdateRequest.getTelephone()))  || users.getTelephone().equals(userUpdateRequest.getTelephone()))
            users.setTelephone(userUpdateRequest.getTelephone());
        else
            message = "Phone number already exist";

        users.setGender(userUpdateRequest.getGender());
        users.setBirthDate(userUpdateRequest.getBirthDate());
        users.setRolesUsers(users.getRolesUsers());

        if(message == null)
            return null;
        else
        {
            userRepository.saveAndFlush(users);
            return UserResponse.build(users);
        }
    }

    @Override
    public Boolean deleteUser(String email) {
        Optional<Users> users = userRepository.findByEmail(email);
        if(users.isPresent())
        {
            users.get().setStatusActive(false);
            userRepository.saveAndFlush(users.get());
            return true;
        }
        else
            return false;
    }


    private List<UserResponse> toListUserResponses(List<Users> allUser) {
        List<UserResponse> allUserResponse = new ArrayList<>();
        for (Users user : allUser) {
            UserResponse userResponse = UserResponse.build(user);
            allUserResponse.add(userResponse);
        }
        return allUserResponse;
    }
}
