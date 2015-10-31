package inso.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import inso.rest.model.AuthToken;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Elisabeth on 13.05.2015.
 */
public class ServiceGenerator {

    private static final String BASE_URL = "https://revex.inso.tuwien.ac.at/api/";

    private ServiceGenerator() {
    }

    private static Gson getGson() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter())
                .setDateFormat("yyyy-MM-dd")
                .create();

        return gson;
    }

    public static <S> S createService(Class<S> serviceClass) {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceWithAuthToken(Class<S> serviceClass, final AuthToken token) {
        if (token != null) {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("X-Auth-Token", token.getToken())
                            .build();

                    return chain.proceed(newRequest);
                }
            };

            HttpLoggingInterceptor httpInterceptor = new HttpLoggingInterceptor();
            httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(interceptor);
            client.interceptors().add(httpInterceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            return retrofit.create(serviceClass);
        } else {
            return createService(serviceClass);
        }
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