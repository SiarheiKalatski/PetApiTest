package restApiClient;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restApiClient.model.Answer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import org.apache.http.client.methods.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import restApiClient.model.HttpMethods;

import static utils.JsonSerializer.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpClient extends BaseRestClient {
    private Logger logger = LogManager.getLogger();
    private URIBuilder builder;
    private String urlRequest;
    private URI uri;
    private StringEntity entity;
    private HttpResponse response;
    private List<Header> headersList;
    private CloseableHttpClient httpClient;

    @SneakyThrows
    @Override
    public <T> BaseRestClient buildRequest(String url, Map<String, String> path, Map<String, String> queryParams, Map<String, String> headers, T t) {
        buildUrl(url, path, queryParams);
        setHeadersAndBody(headers, t);
        return this;
    }

    @SneakyThrows
    @Override
    public Answer sendRequest(HttpMethods httpMethodName, Class<?> tClass) {
        switch (httpMethodName) {
            case GET:
                response = httpClient.execute(new HttpGet(uri));
                break;
            case POST:
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(entity);
                response = httpClient.execute(httpPost);
                break;
            case PUT:
                HttpPut httpPut = new HttpPut(uri);
                httpPut.setEntity(entity);
                response = httpClient.execute(httpPut);
                break;
            case DELETE:
                response = httpClient.execute(new HttpDelete(uri));
                break;
        }
        return buildAnswer(tClass);
    }

    @SneakyThrows
    @Override
    public Answer buildAnswer(Class<?> tClass) {
        Answer answer = new Answer();
        answer.setStatusCode(response.getStatusLine().getStatusCode());
        logger.info(response.getEntity());
        answer.setBody(convertToClass(null, EntityUtils.toString(response.getEntity()), tClass));
        answer.setHead(Arrays.asList(response.getAllHeaders()));
        return answer;
    }

    @SneakyThrows
    private HttpClient buildUrl(String url, Map<String, String> pathParams, Map<String, String> queryParams) {
        urlRequest = url;
        pathParams.forEach((k, v) -> urlRequest = urlRequest.replace("{" + k + "}", v));
        builder = new URIBuilder(urlRequest);
        queryParams.forEach(builder::addParameter);
        uri = builder.build();
        logger.info(uri);
        return this;
    }

    @SneakyThrows
    private <T> HttpClient setHeadersAndBody(Map<String, String> headers, T t) {
        headersList = new ArrayList<>();
        headers.forEach((k, v) -> headersList.add(new BasicHeader(k, v)));
        entity = new StringEntity(new ObjectMapper().writeValueAsString(t));
        httpClient = HttpClients.custom().setDefaultHeaders(headersList).build();
        logger.info(httpClient);
        return this;
    }
}
