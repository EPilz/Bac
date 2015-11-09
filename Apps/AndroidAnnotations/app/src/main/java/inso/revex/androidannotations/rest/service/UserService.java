package inso.revex.androidannotations.rest.service;


import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.net.URI;

import inso.revex.androidannotations.rest.model.AuthToken;
import inso.revex.androidannotations.rest.model.User;


/**
 * Created by Elisabeth on 13.05.2015.
 */

@Rest(rootUrl = "https://revex.inso.tuwien.ac.at/api", converters = { MappingJackson2HttpMessageConverter.class })
public interface UserService {

    @Post("/account/authentication")
    public  AuthToken getAuthToken(User user);

}