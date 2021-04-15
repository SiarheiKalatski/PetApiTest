package restApiClient;

import restApiClient.model.Answer;

import java.util.Map;

public interface BaseApiClient {
    String bodyKey = "Body";
    String headKey = "Head";
    String statusCodeKey = "StatusCode";

    <T> Answer get(String url, Map<String, String> path, Map<String,
            String> queryParams, Map<String, String> headers, T t, Class<?> clas);
    <T> Answer post(String url, Map<String, String> path, Map<String,
            String> queryParams, Map<String, String> headers, T t, Class<?> kClass);
    <T> Answer put(String url, Map<String, String> path, Map<String,
            String> queryParams, Map<String, String> headers, T t, Class<?> kClass);
    <T> Answer delete(String url, Map<String, String> path, Map<String,
            String> queryParams, Map<String, String> headers, T t, Class<?> kClass);
}
