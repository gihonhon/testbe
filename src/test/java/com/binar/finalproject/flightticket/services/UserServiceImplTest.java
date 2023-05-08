package com.binar.finalproject.flightticket.services;

import com.binar.finalproject.flightticket.dummy.DataDummyUsers;
import com.binar.finalproject.flightticket.model.Users;
import com.binar.finalproject.flightticket.repository.UserRepository;
import com.binar.finalproject.flightticket.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private DataDummyUsers dataDummyUsers;
    private List<Users> dataUsers = new ArrayList<>();

    @BeforeEach
    void Init(){
        MockitoAnnotations.initMocks(this);
        dataDummyUsers = new DataDummyUsers();
        dataUsers = dataDummyUsers.getDATA_USERS();
    }

    @Test
    @DisplayName("[Positive] Get all user")
    void testPositiveGetAllUser(){
        Mockito.when(userRepository.findAll()).thenReturn(dataUsers);
        var actualValue = userService.searchAllUser();
        var expectedSize = dataUsers.size();
        var expectedValue1 = dataUsers.get(0);
        var expectedValue2 = dataUsers.get(1);
        var expectedValue3 = dataUsers.get(2);
        Assertions.assertNotNull(actualValue);
        Assertions.assertNotNull(expectedValue1);
        Assertions.assertNotNull(expectedValue2);
        Assertions.assertNotNull(expectedValue3);
        Assertions.assertEquals(expectedSize, actualValue.size());

        Assertions.assertEquals(expectedValue1.getFullName(), actualValue.get(0).getFullName());
        Assertions.assertEquals(expectedValue1.getEmail(), actualValue.get(0).getEmail());
        Assertions.assertEquals(expectedValue1.getTelephone(), actualValue.get(0).getTelephone());
        Assertions.assertEquals(expectedValue1.getBirthDate(), actualValue.get(0).getBirthDate());
        Assertions.assertEquals(expectedValue1.getGender(), actualValue.get(0).getGender());

        Assertions.assertEquals(expectedValue2.getFullName(), actualValue.get(1).getFullName());
        Assertions.assertEquals(expectedValue2.getEmail(), actualValue.get(1).getEmail());
        Assertions.assertEquals(expectedValue2.getTelephone(), actualValue.get(1).getTelephone());
        Assertions.assertEquals(expectedValue2.getBirthDate(), actualValue.get(1).getBirthDate());
        Assertions.assertEquals(expectedValue2.getGender(), actualValue.get(1).getGender());

        Assertions.assertEquals(expectedValue3.getFullName(), actualValue.get(2).getFullName());
        Assertions.assertEquals(expectedValue3.getEmail(), actualValue.get(2).getEmail());
        Assertions.assertEquals(expectedValue3.getTelephone(), actualValue.get(2).getTelephone());
        Assertions.assertEquals(expectedValue3.getBirthDate(), actualValue.get(2).getBirthDate());
        Assertions.assertEquals(expectedValue3.getGender(), actualValue.get(2).getGender());
    }

    @Test
    @DisplayName("[Positive] Find if email exist")
    void testPositiveFindEmailExist(){
        String email = "cahyadisn6@gmail.com";

        Optional<Users> users = dataDummyUsers.getUserByEmail(email);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(users);

        var actualValue = userService.isEmailExist(email);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Negative] Find if email exist")
    void testNegativeFindEmailExist(){
        String email = "cahyadisn6@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        var actualValue = userService.isEmailExist(email);
        var expectedValue = false;
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Positive] Find if phone number exist")
    void testPositivePhoneExist(){
        String telephone = "081211572037";

        Optional<Users> users = dataDummyUsers.getUserByPhone(telephone);
        Users userData = users.get();
        Mockito.when(userRepository.findPhoneNumber(telephone)).thenReturn(userData);

        var actualValue = userService.isPhoneNumberExist(telephone);
        var expectedValue = true;

        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Negative] Find if phone number exist")
    void testNegativePhoneExist(){
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(null);
        var actualValue = userService.isPhoneNumberExist(Mockito.any());
        var expectedValue = false;
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Positive] Delete user")
    void testPositiveDeleteUser(){
        String email = "cahyadisn6@gmail.com";

        Optional<Users> users = dataDummyUsers.getUserByEmail(email);
        Users usersData = users.get();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(users);

        usersData.setStatusActive(false);

        Mockito.when(userRepository.save(usersData)).thenReturn(usersData);

        var actualValue = userService.deleteUser(email);
        var expectedValue = true;

        Assertions.assertNotNull(usersData);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("[Negative] Delete user")
    void testNegativeDeleteUser(){
        String email = "cahyadisn6@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        var actualValue = userService.deleteUser(email);
        var expectedValue = false;
        Assertions.assertEquals(expectedValue, actualValue);
    }
}
