package revex.inso.rest.service;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.AuthToken;
import revex.inso.rest.model.User;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class UserService {

    public static AuthToken getAuthToken(User user) {
        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/account/authentication")
                .build()
                .toUri();

        RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

        AuthToken authToken = restTemplate.postForObject(url, user, AuthToken.class);
        return authToken;
    }
}