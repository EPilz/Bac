package inso.rest.service;

import java.util.List;

import inso.rest.model.Component;
import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import inso.rest.model.ProductionLine;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public interface PowerPlantService {

    @GET("powerplants")
    public Call<List<PowerPlant>> getPowerPlants();

    @GET("powerplants/{id}")
    public Call<PowerPlant> getPowerPlantById(@Path("id") int id);

    @GET("powerplants/{id}/evaluation")
    public Call<Evaluation> getPowerPlantEvaluation(@Path("id") int id);

    @GET("powerplants/{id}/productionlines")
    public Call<List<ProductionLine>> getProductionLines(@Path("id") int id);

    @GET("productionlines/{id}/components")
    public Call<List<Component>> getComponentsFromProductionLines(@Path("id") int id);

    @GET("productionlines/{id}/evaluation")
    public Call<Evaluation> getProductionLineEvaluation(@Path("id") int id);

    @GET("components/{id}/evaluation")
    public Call<Evaluation> getComponentEvaluation(@Path("id") int id);

    @POST("powerplants")
    public Call<PowerPlant> createPowerPlant(@Body PowerPlant powerPlant);

    @DELETE("powerplants/{id}")
    public Call<PowerPlant> deletePowerPlantById(@Path("id") int id);

    @PUT("powerplants/{id}")
    public Call<PowerPlant> updatePowerPlant(@Path("id") int id, @Body PowerPlant powerPlant);

}
