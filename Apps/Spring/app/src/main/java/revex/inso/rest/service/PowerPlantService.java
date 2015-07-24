package revex.inso.rest.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.PowerPlant;
import revex.inso.util.UtilitiesManager;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class PowerPlantService {

   /* @GET("/powerplants")
    public List<PowerPlant> getPowerPlants();

    /* @GET("/powerplants")*/
    public static List<PowerPlant> getPowerPlants(){
     RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

     URI url= UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
             .path("/powerplants")
             .build()
             .toUri();

     HttpHeaders headers = new HttpHeaders();
     headers.set("X-Auth-Token", UtilitiesManager.getInstance().getAuthToken().getToken());
     headers.setContentType(MediaType.APPLICATION_JSON);

     HttpEntity entity = new HttpEntity(headers);

     HttpEntity<PowerPlant[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PowerPlant[].class);

     return Arrays.asList(response.getBody());
    }


    /*@GET("/powerplants/{id}")
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

    @PUT("/powerplants/{id}")
    public PowerPlant updatePowerPlant(@Path("id") int id, @Body PowerPlant powerPlant);*/



}
