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
import revex.inso.rest.model.Component;
import revex.inso.rest.model.Evaluation;
import revex.inso.rest.model.PowerPlant;
import revex.inso.rest.model.ProductionLine;
import revex.inso.util.UtilitiesManager;

/**
 * Created by Elisabeth on 13.05.2015.
 */

public class PowerPlantService {

    public static List<PowerPlant> getPowerPlants() {
        RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/powerplants")
                .build()
                .toUri();

        HttpEntity entity = ServiceGenerator.getHttpEntity();

        HttpEntity<PowerPlant[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, PowerPlant[].class);

        return Arrays.asList(response.getBody());
    }

    public static PowerPlant getPowerPlantById(int id) {
        RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/powerplants/" + id)
                .build()
                .toUri();

        HttpEntity entity = ServiceGenerator.getHttpEntity();

        HttpEntity<PowerPlant> response = restTemplate.exchange(url, HttpMethod.GET, entity, PowerPlant.class);

        return response.getBody();
    }

    public static Evaluation getPowerPlantEvaluation(int id) {
        RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/powerplants/" + id + "/evaluation")
                .build()
                .toUri();

        HttpEntity entity = ServiceGenerator.getHttpEntity();

        HttpEntity<Evaluation> response = restTemplate.exchange(url, HttpMethod.GET, entity, Evaluation.class);

        return response.getBody();
    }

    /*@GET("/powerplants/{id}/productionlines")*/
    public List<ProductionLine> getProductionLines(int id) {
        RestTemplate restTemplate = ServiceGenerator.getRestTemplate();

        URI url = UriComponentsBuilder.fromUriString(ServiceGenerator.BASE_URL)
                .path("/powerplants/" + id + "/productionlines")
                .build()
                .toUri();

        HttpEntity entity = ServiceGenerator.getHttpEntity();

        HttpEntity<ProductionLine[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProductionLine[].class);

        return Arrays.asList(response.getBody());
    }

   /* @GET("/productionlines/{id}/components")
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
