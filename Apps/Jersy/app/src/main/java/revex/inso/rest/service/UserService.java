package revex.inso.rest.service;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.AuthToken;
import revex.inso.rest.model.User;
/**
 * Created by Elisabeth on 13.05.2015.
 */
public class UserService {

    public static AuthToken getAuthToken(User user) {
        Builder builder = ServiceGenerator.getBuilderFromUrl("/account/authentication");

        AuthToken authToken = builder.
                accept(MediaType.APPLICATION_JSON).
                post(Entity.entity(user, MediaType.APPLICATION_JSON), AuthToken.class);

        System.out.println(authToken.getToken());
        return authToken;
    }
}
