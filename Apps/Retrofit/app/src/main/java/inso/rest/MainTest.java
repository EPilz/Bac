package inso.rest;

import java.util.List;

import inso.rest.model.AuthToken;
import inso.rest.model.PowerPlant;
import inso.rest.model.User;
import inso.rest.service.PowerPlantService;
import inso.rest.service.UserService;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class MainTest {

    public static void main(String[] args) {
        UserService userService = ServiceGenerator.createService(UserService.class);

        AuthToken token = userService.getAuthToken(new User("admin", "admin"));

        System.out.println(token.getToken());

        userService = ServiceGenerator.createServiceWithAuthToken(UserService.class, token);
        User user = userService.getUserAccount();

        System.out.println(user);
        System.out.println(user.getRoles().toString());

        PowerPlantService powerPlantService = ServiceGenerator.createServiceWithAuthToken(PowerPlantService.class, token);
        List<PowerPlant> powerPlantList = powerPlantService.getPowerPlants();
        System.out.println(powerPlantList);
    }
}
