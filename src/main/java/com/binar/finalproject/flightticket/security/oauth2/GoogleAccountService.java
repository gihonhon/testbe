package com.binar.finalproject.flightticket.security.oauth2;

import com.binar.finalproject.flightticket.model.AuthenticationProvider;
import com.binar.finalproject.flightticket.model.Roles;
import com.binar.finalproject.flightticket.model.Users;
import com.binar.finalproject.flightticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GoogleAccountService {

    @Autowired
    private UserRepository userRepository;

    public Users oAuthLoginSuccess(String email, String fullName, String googleId) {
        Users userGoogle = userRepository.findByGoogleId(googleId);
        if (userGoogle == null) {
            Users userWithGmail = userRepository.findByGmail(email);
            if (userWithGmail == null) {
                Users users = new Users();
                Roles roles = new Roles();
                users.setEmail(email);
                users.setFullName(fullName);
                users.setStatusActive(true);
                users.setAuthProvider(AuthenticationProvider.GOOGLE);
                users.setGoogleId(googleId);
                roles.setRoleName("ROLE_BUYER");
                oAuth2Password(users);
                return userRepository.save(users);
            }
        }
        return userGoogle;
    }
    public void oAuth2Password(Users users){
        String googleId = users.getGoogleId();
        String clientId = "953090499155-f5pgpt16s6lhge53hhi4s5cm5dg18in3.apps.googleusercontent.com";
        String googlePassword = googleId + clientId;
        users.setPassword(String.valueOf(googlePassword.hashCode()));
    }
}
