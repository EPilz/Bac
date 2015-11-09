package inso.revex.androidannotations.rest.service;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import inso.revex.androidannotations.util.UtilitiesManager;


public class HttpBasicAuthenticatorInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("X-Auth-Token", UtilitiesManager.getInstance().getAuthToken().getToken());
        return execution.execute(request, data);
    }
}