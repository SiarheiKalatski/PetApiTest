package BaseApiClient;

import io.restassured.response.ResponseOptions;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class RestAssuredClient<T> implements BaseApiClient<T> {
    @Override
    public Map<String, Object> get(URL Url, Map <String,String> headers) {

        return buildMap(given().headers(headers).get(Url));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> post(URL Url, T t, Map <String,String> headers) {
        return buildMap(given().headers(headers).body(t).post(Url.toURI()));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> put(URL Url, T t, Map <String,String> headers) {
        return buildMap(given().body(t).headers(headers).put(Url.toURI()));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> delete(URL Url, Map <String,String> headers) {

        return buildMap(given().headers(headers).delete(Url.toURI()));
    }

    @SneakyThrows
    private Map<String, Object> buildMap(ResponseOptions response){
        Map<String, Object> responseParts = new HashMap<>();
        responseParts.put(bodyKey, response.getBody().print());
        responseParts.put(headKey, response.getHeaders().asList());
        responseParts.put(statusCodeKey, response.statusCode());
        return responseParts;
    }
}
