package inso.revex.androidannotations.rest.service;


import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

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
    List<PowerPlant> getPowerPlants();

    @Get("/powerplants/{id}")
    PowerPlant getPowerPlantById(@Path int id);

    @Get("/powerplants/{id}/evaluation")
    Evaluation getPowerPlantEvaluation(@Path int id);

    @Get("/powerplants/{id}/productionlines")
    List<ProductionLine> getProductionLines(@Path int id);

    @Get("/productionlines/{id}/components")
    List<Component> getComponentsFromProductionLines(@Path int id);

    @Get("/productionlines/{id}/evaluation")
    Evaluation getProductionLineEvaluation(@Path int id);

    @Get("/components/{id}/evaluation")
    Evaluation getComponentEvaluation(@Path int id);

    @Post("/powerplants")
    PowerPlant createPowerPlant(@Body PowerPlant powerPlant);

    @Delete("/powerplants/{id}")
    PowerPlant deletePowerPlantById(@Path int id);

    @Put("/powerplants/{id}")
    PowerPlant updatePowerPlant(@Path int id, @Body PowerPlant powerPlant);

}


