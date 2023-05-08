package com.binar.finalproject.flightticket.dummy;

import com.binar.finalproject.flightticket.dto.UserRequest;
import com.binar.finalproject.flightticket.model.Users;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class DataDummyUsers {

    private final List<Users> DATA_USERS = new ArrayList<>();
    private final List<UserRequest> DATA_USERS_REQUEST = new ArrayList<>();
    public DataDummyUsers(){
        DataDummyRoles dataDummyRoles = new DataDummyRoles();
        UserRequest userRequest1 = new UserRequest();
        userRequest1.setFullName("ADMIN");
        userRequest1.setEmail("admin@gmail.com");
        userRequest1.setPassword("123456789");
        userRequest1.setTelephone("081211572031");
        userRequest1.setBirthDate(LocalDate.now());
        userRequest1.setGender(true);

        UserRequest userRequest2 = new UserRequest();
        userRequest2.setFullName("Reinaldi Surya Nugraha");
        userRequest2.setEmail("reinaldi.surya6@gmail.com");
        userRequest2.setPassword("123456789");
        userRequest2.setTelephone("081211572038");
        userRequest2.setBirthDate(LocalDate.now());
        userRequest2.setGender(true);

        UserRequest userRequest3 = new UserRequest();
        userRequest3.setFullName("Viana Tameira");
        userRequest3.setEmail("vianatameira@gmail.com");
        userRequest3.setPassword("123456789");
        userRequest3.setTelephone("081211572039");
        userRequest3.setBirthDate(LocalDate.now());
        userRequest3.setGender(false);

        UserRequest userRequest4 = new UserRequest();
        userRequest4.setFullName("Angga Anugerah");
        userRequest4.setEmail("anggaanugerah@gmail.com");
        userRequest4.setPassword("123456789");
        userRequest4.setTelephone("081211572030");
        userRequest4.setBirthDate(LocalDate.now());
        userRequest4.setGender(true);

        UserRequest userRequest5 = new UserRequest();
        userRequest5.setFullName("Cahyadi Surya Nugraha");
        userRequest5.setEmail("cahyadisn6@gmail.com");
        userRequest5.setPassword("123456789");
        userRequest5.setTelephone("081211572037");
        userRequest5.setBirthDate(LocalDate.now());
        userRequest5.setGender(true);

        Users users1 = userRequest1.toUsers();
        users1.getRolesUsers().add(dataDummyRoles.getDATA_ROLES().get(1));

        Users users2 = userRequest2.toUsers();
        users2.getRolesUsers().add(dataDummyRoles.getDATA_ROLES().get(2));

        Users users3 = userRequest3.toUsers();
        users3.getRolesUsers().add(dataDummyRoles.getDATA_ROLES().get(2));

        Users users4 = userRequest4.toUsers();
        users4.getRolesUsers().add(dataDummyRoles.getDATA_ROLES().get(2));

        Users users5 = userRequest5.toUsers();
        users5.getRolesUsers().add(dataDummyRoles.getDATA_ROLES().get(2));

        DATA_USERS_REQUEST.add(userRequest1);
        DATA_USERS_REQUEST.add(userRequest2);
        DATA_USERS_REQUEST.add(userRequest3);
        DATA_USERS_REQUEST.add(userRequest4);
        DATA_USERS_REQUEST.add(userRequest5);

        DATA_USERS.add(users1);
        DATA_USERS.add(users2);
        DATA_USERS.add(users3);
        DATA_USERS.add(users4);
        DATA_USERS.add(users5);
    }

    public Optional<Users> getUserById(UUID userId){
        return DATA_USERS.stream()
                .filter(users -> users.getUserId().equals(userId))
                .findFirst();
    }

    public Optional<Users> getUserByEmail(String email){
        return DATA_USERS.stream()
                .filter(users -> users.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Users> getUserByPhone(String phoneNumber){
        return DATA_USERS.stream()
                .filter(users -> users.getTelephone().equals(phoneNumber))
                .findFirst();
    }
}
