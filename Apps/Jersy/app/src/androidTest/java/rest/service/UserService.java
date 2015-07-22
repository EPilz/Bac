package rest.service;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import inso.rest.model.AuthToken;
import inso.rest.model.User;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class UserService {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;

    @Path(("/account/authentication")

    public AuthToken getAuthToken(@Body User user) {

            }

    @Path("/account")
    public User getUserAccount() {
            
            }
}
