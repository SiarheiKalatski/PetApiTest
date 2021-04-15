package restApiClient;


import restApiClient.model.Answer;
import restApiClient.model.HttpMethods;

import java.util.Map;

public abstract class BaseRestClient implements BaseApiClient {
    @Override
    public <T> Answer get(String url, Map<String, String> path, Map<String, String> queryParams,
                          Map<String, String> headers, T t, Class<?> kClass) {
        buildRequest(url, path, queryParams, headers, t);
        return sendRequest(HttpMethods.GET, kClass);
    }

    @Override
    public <T> Answer post(String url, Map<String, String> path, Map<String, String> queryParams,
                           Map<String, String> headers, T t, Class<?> kClass) {
        buildRequest(url, path, queryParams, headers, t);
        return sendRequest(HttpMethods.POST, kClass);
    }

    @Override
    public <T> Answer put(String url, Map<String, String> path, Map<String, String> queryParams,
                          Map<String, String> headers, T t, Class<?> kClass) {
        buildRequest(url, path, queryParams, headers, t);
        return sendRequest(HttpMethods.PUT, kClass);
    }

    @Override
    public <T> Answer delete(String url, Map<String, String> path, Map<String, String> queryParams,
                             Map<String, String> headers, T t, Class<?> kClass) {
        buildRequest(url, path, queryParams, headers, t);
        return sendRequest(HttpMethods.DELETE, kClass);
    }

    public abstract <T> BaseRestClient buildRequest(String url, Map<String, String> path, Map<String, String> queryParams,
                                                    Map<String, String> headers, T t);

    public abstract Answer sendRequest(HttpMethods methods, Class<?> tClass);

    public abstract Answer buildAnswer(Class<?> tClass);
}
