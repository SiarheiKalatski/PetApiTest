package BaseApiClient;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestTemplateClient<T> implements BaseApiClient<T> {
    @SneakyThrows
    @Override
    public Map<String, Object> get(URL Url) {
        RestTemplate restTemplate = new RestTemplate();
        return buildMap(restTemplate.getForEntity(Url.toURI(), String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> post(URL Url, T t) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<T> request = new HttpEntity<>(t);
        return buildMap(restTemplate.postForEntity(Url.toURI(), request, String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> put(URL Url, T t) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<T> requestUpdate = new HttpEntity<>(t,headers);
        return buildMap(restTemplate.exchange(Url.toURI(), HttpMethod.PUT, requestUpdate, String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> delete(URL Url) {
        RestTemplate restTemplate =new RestTemplate();
        return buildMap(restTemplate.exchange(Url.toURI(), HttpMethod.DELETE, null, String.class));
    }
    @SneakyThrows
    private Map<String, Object> buildMap(ResponseEntity<String> response){
        Map<String, Object> responseParts = new HashMap<>();
        responseParts.put(bodyKey, response.getBody());
        responseParts.put(headKey, response.getHeaders().
                entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        responseParts.put(statusCodeKey, response.getStatusCodeValue());
        return responseParts;
    }
}
