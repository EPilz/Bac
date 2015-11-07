package revex.inso.rest.service;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import revex.inso.rest.ServiceGenerator;
import revex.inso.rest.model.Component;
import revex.inso.rest.model.Evaluation;
import revex.inso.rest.model.PowerPlant;
import revex.inso.rest.model.ProductionLine;
import revex.inso.rest.model.User;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class PowerPlantService {

    public static List<PowerPlant> getPowerPlants() {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants");

        PowerPlant[] powerPlants = builder.accept(MediaType.APPLICATION_JSON).get(PowerPlant[].class);

        return Arrays.asList(powerPlants);
    }

    public static PowerPlant getPowerPlantById(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants/" + id);
        PowerPlant powerPlant = builder.accept(MediaType.APPLICATION_JSON).get(PowerPlant.class);

        return powerPlant;
    }

    public static Evaluation getPowerPlantEvaluation(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants/" + id + "/evaluation");
        Evaluation evaluation = builder.accept(MediaType.APPLICATION_JSON).get(Evaluation.class);
        return evaluation;
    }

    public static List<ProductionLine> getProductionLines(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants/" + id + "/productionlines");

        ProductionLine[] productionLines = builder.accept(MediaType.APPLICATION_JSON).get(ProductionLine[].class);

        return Arrays.asList(productionLines);
    }

    public static List<Component> getComponentsFromProductionLines(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/productionlines/" + id + "/components");

        Component[] components = builder.accept(MediaType.APPLICATION_JSON).get(Component[].class);

        return Arrays.asList(components);
    }

    public static Evaluation getProductionLineEvaluation(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/productionlines/" + id + "/evaluation");
        Evaluation evaluation = builder.accept(MediaType.APPLICATION_JSON).get(Evaluation.class);
        return evaluation;
    }

    public static Evaluation getComponentEvaluation(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/components/" + id + "/evaluation");
        Evaluation evaluation = builder.accept(MediaType.APPLICATION_JSON).get(Evaluation.class);
        return evaluation;
    }
    
    public static PowerPlant createPowerPlant(PowerPlant powerPlant) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants");

        PowerPlant newPowerPlant = builder.accept(MediaType.APPLICATION_JSON).
                post(Entity.entity(powerPlant, MediaType.APPLICATION_JSON), PowerPlant.class);

        return newPowerPlant;
    }

    public static PowerPlant deletePowerPlantById(int id) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants/" + id);

        PowerPlant removedPowerPlant = builder.accept(MediaType.APPLICATION_JSON).delete(PowerPlant.class);

        return removedPowerPlant;
    }

    public static PowerPlant updatePowerPlant(int id, PowerPlant powerPlant) {
        Builder builder = ServiceGenerator.getBuilderFromUrlWithAuthToken("/powerplants/" + id);

        PowerPlant updatedPowerPlant = builder.accept(MediaType.APPLICATION_JSON).
                put(Entity.entity(powerPlant, MediaType.APPLICATION_JSON), PowerPlant.class);

        return updatedPowerPlant;
    }

}
