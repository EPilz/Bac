package inso.rest.service;

import java.util.List;

import inso.rest.model.Component;
import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import inso.rest.model.ProductionLine;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @GET("/powerplants/{id}/productionlines")
    public List<ProductionLine> getProductionLines(@Path("id") int id);

    @GET("/productionlines/{id}/components")
    public List<Component> getComponentsFromProductionLines(@Path("id") int id);

    @GET("/productionlines/{id}/evaluation")
    public Evaluation getProductionLineEvaluation(@Path("id") int id);

    @GET("/components/{id}/evaluation")
    public Evaluation getComponentEvaluation(@Path("id") int id);

    @POST("/powerplants")
    public PowerPlant createPowerPlant(@Body PowerPlant powerPlant);

    @DELETE("/powerplants/{id}")
    public PowerPlant deletePowerPlantById(@Path("id") int id);



}
