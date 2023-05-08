package com.binar.finalproject.flightticket.security.oauth2;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthorizedClientService implements OAuth2AuthorizedClientService {

    private final GoogleAccountService googleAccountService;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
       this.googleAccountService.oAuthLoginSuccess(
               authentication.getName(),
               authentication.getName().split("\\|")[0],
               ((DefaultOidcUser) authentication.getPrincipal()).getClaims().toString());
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        // Not Used Method
    }

}