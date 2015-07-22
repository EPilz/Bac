package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Collection;

import inso.rest.model.AuthToken;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class ServiceGenerator {

    private static final String BASE_URL = "https://revex.inso.tuwien.ac.at/api";

    private ServiceGenerator() {
    }

    private static RestAdapter.Builder getBuilder() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter())
                .setDateFormat("yyyy-MM-dd")
                .create();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson));

        return builder;
    }

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter.Builder builder = getBuilder();

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    public static <S> S createServiceWithAuthToken(Class<S> serviceClass, final AuthToken token) {
        RestAdapter.Builder builder = getBuilder();

        if (token != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("X-Auth-Token", token.getToken());
                }
            });
        }

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    static class CollectionAdapter implements JsonSerializer<Collection<?>> {
        @Override
        public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.isEmpty()) {
                return null;
            }

            JsonArray array = new JsonArray();

            for (Object child : src) {
                JsonElement element = context.serialize(child);
                array.add(element);
            }

            return array;
        }
    }
}