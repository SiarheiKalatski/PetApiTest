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
    public Map<String, Object> get(URL Url, Map <String,String> headersMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headersMap.forEach(headers::set);
        HttpEntity request = new HttpEntity(headers);
        return buildMap(restTemplate.exchange(Url.toURI(), HttpMethod.GET, request, String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> post(URL Url, T t, Map <String,String> headersMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headersMap.forEach(headers::set);
        HttpEntity<T> request = new HttpEntity<>(t, headers);
        return buildMap(restTemplate.postForEntity(Url.toURI(), request, String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> put(URL Url, T t, Map <String,String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        headersMap.forEach(headers::set);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<T> requestUpdate = new HttpEntity<>(t,headers);
        return buildMap(restTemplate.exchange(Url.toURI(), HttpMethod.PUT, requestUpdate, String.class));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> delete(URL Url, Map<String, String> headersMap) {
        RestTemplate restTemplate =new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headersMap.forEach(headers::set);
        HttpEntity request = new HttpEntity(headers);
        return buildMap(restTemplate.exchange(Url.toURI(), HttpMethod.DELETE, request, String.class));
    }
    @SneakyThrows
    private Map<String, Object> buildMap(ResponseEntity<String> response){
        Map<String, Object> responseParts = new HashMap<>();
        responseParts.put(bodyKey, response.getBody());
        responseParts.put(headKey, response.getHeaders()
                .entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        responseParts.put(statusCodeKey, response.getStatusCodeValue());
        return responseParts;
    }
}
