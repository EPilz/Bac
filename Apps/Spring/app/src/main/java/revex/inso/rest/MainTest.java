package revex.inso.rest;

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

        AuthToken token = UserService.getAuthToken(new User("admin", "admin"));

        System.out.println(token.getToken());

        /*userService = ServiceGenerator.createServiceWithAuthToken(UserService.class, token);
        User user = userService.getUserAccount();

        System.out.println(user);
        System.out.println(user.getRoles().toString());

        PowerPlantService powerPlantService = ServiceGenerator.createServiceWithAuthToken(PowerPlantService.class, token);
        List<PowerPlant> powerPlantList = powerPlantService.getPowerPlants();
        System.out.println(powerPlantList);*/
    }
}
