package inso.rest.service;

import java.util.List;

import inso.rest.model.PowerPlant;
import retrofit.http.GET;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public interface PowerPlantService {

    @GET("/powerplants")
    public List<PowerPlant> getPowerPlants();
}
