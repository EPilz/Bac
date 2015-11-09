package inso.revex.androidannotations.rest.service;

import org.androidannotations.annotations.rest.Delete;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.List;

import inso.revex.androidannotations.rest.model.Component;
import inso.revex.androidannotations.rest.model.Evaluation;
import inso.revex.androidannotations.rest.model.PowerPlant;
import inso.revex.androidannotations.rest.model.ProductionLine;

@Rest(rootUrl = "https://revex.inso.tuwien.ac.at/api",
        converters = { MappingJackson2HttpMessageConverter.class },
        interceptors = { HttpBasicAuthenticatorInterceptor.class })
public interface PowerPlantService {

    @Get("/powerplants")
    public List<PowerPlant> getPowerPlants();

    @Get("/powerplants/{id}")
    public PowerPlant getPowerPlantById(int id);

    @Get("/powerplants/{id}/evaluation")
    public Evaluation getPowerPlantEvaluation(int id);

    @Get("/powerplants/{id}/productionlines")
    public List<ProductionLine> getProductionLines(int id);

    @Get("/productionlines/{id}/components")
    public List<Component> getComponentsFromProductionLines(int id);

    @Get("/productionlines/{id}/evaluation")
    public Evaluation getProductionLineEvaluation(int id);

    @Get("/components/{id}/evaluation")
    public Evaluation getComponentEvaluation(int id);

    @Post("/powerplants")
    public PowerPlant createPowerPlant(PowerPlant powerPlant);

    @Delete("/powerplants/{id}")
    public PowerPlant deletePowerPlantById(int id);

    @Put("/powerplants/{id}")
    public PowerPlant updatePowerPlant(int id, PowerPlant powerPlant);

}


