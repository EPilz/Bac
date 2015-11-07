package revex.inso.rest;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import  javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import revex.inso.util.UtilitiesManager;

public class ServiceGenerator {

    private static final String BASE_URL = "https://revex.inso.tuwien.ac.at/api";

    private ServiceGenerator() {
    }


    public static WebTarget getWebTarget() {
        ClientConfig clientConfig = new ClientConfig().
                register(JacksonFeature.class);

        Client client = ClientBuilder.newClient(clientConfig).
                register(new LoggingFilter()).
                register(AndroidFriendlyFeature.class);

        return client.target(BASE_URL);
    }

    public static Builder getBuilderFromUrl(String path) {
        return getWebTarget().path(path).request(MediaType.APPLICATION_JSON);
    }

    public static Builder getBuilderFromUrlWithAuthToken(String path) {
        return getBuilderFromUrl(path).header("X-Auth-Token", UtilitiesManager.getInstance().getAuthToken().getToken());
    }

    public static class AndroidFriendlyFeature implements Feature {

        @Override
        public boolean configure(FeatureContext context) {
            context.register(new AbstractBinder() {
                @Override
                protected void configure() {
                    addUnbindFilter(new Filter() {
                        @Override
                        public boolean matches(Descriptor d) {
                            String implClass = d.getImplementation();
                            return implClass.startsWith(
                                    "org.glassfish.jersey.message.internal.DataSource")
                                    || implClass.startsWith(
                                    "org.glassfish.jersey.message.internal.RenderedImage");
                        }
                    });
                }
            });
            return true;
        }
    }
}