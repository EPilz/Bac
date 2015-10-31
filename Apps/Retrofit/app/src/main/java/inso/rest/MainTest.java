package inso.rest;

import java.util.List;

import inso.rest.model.AuthToken;
import inso.rest.model.PowerPlant;
import inso.rest.model.User;
import inso.rest.service.PowerPlantService;
import inso.rest.service.UserService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class MainTest {

    public static void main(String[] args) {
        UserService userService = ServiceGenerator.createService(UserService.class);

        Call<AuthToken> call = userService.getAuthToken(new User("admin", "admin"));

        call.enqueue(new Callback<AuthToken>() {
            @Override
            public void onResponse(Response<AuthToken> response, Retrofit retrofit) {
                AuthToken token = response.body();
                System.out.println(response.body().getToken());
            }

            @Override
            public void onFailure(Throwable t) {
                t.fillInStackTrace();
            }
        });
    }
}
