package inso.rest.service;

import java.util.List;

import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public interface PowerPlantService {

    @GET("/powerplants")
    public List<PowerPlant> getPowerPlants();

    @GET("/powerplants/{id}")
    public PowerPlant getPowerPlantById(@Path("id") int id);

    @GET("/powerplants/{id}/evaluation")
    public Evaluation getPowerPlantEvaluation(@Path("id") int id);


}
