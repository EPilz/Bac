package inso.rest.service;

import inso.rest.model.AuthToken;
import inso.rest.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public interface UserService {

    @POST("account/authentication")
    public Call<AuthToken> getAuthToken(@Body User user);

    @GET("account")
    public Call<User> getUserAccount();
}
