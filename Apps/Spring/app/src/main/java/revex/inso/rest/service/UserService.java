package revex.inso.rest.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.AUser;
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

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AUser> requestEntity = new HttpEntity<AUser>(new AUser(user.getUsername(), user.getPassword()), requestHeaders);
    //    HttpEntity<User> requestEntity = new HttpEntity<User>(user, requestHeaders);

        ResponseEntity<AuthToken> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AuthToken.class);

        return response.getBody();
    }
}