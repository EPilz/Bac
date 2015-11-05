package revex.inso.rest;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import revex.inso.util.UtilitiesManager;


/**
 * Created by Elisabeth on 13.05.2015.
 */
public class ServiceGenerator {

    public static final String BASE_URL = "https://revex.inso.tuwien.ac.at/api";

    private ServiceGenerator() {
    }

    public static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        return restTemplate;
    }

    public static HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", UtilitiesManager.getInstance().getAuthToken().getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity(headers);
    }

}