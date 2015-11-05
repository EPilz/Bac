package revex.inso.rest;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.AuthToken;
import revex.inso.rest.model.User;
import revex.inso.rest.service.UserService;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class MainTest {

    public static void main(String[] args) {

        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/powerplants/{id}")

                .build()
                .toUri();

        System.out.println(url.toString());
    }
}
