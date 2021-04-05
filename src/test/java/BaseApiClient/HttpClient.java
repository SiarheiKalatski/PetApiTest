package BaseApiClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpClient<T> implements BaseApiClient<T> {
    @SneakyThrows
    @Override
    public Map<String, Object> get(URL Url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(Url.toURI());
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return buildMap(httpResponse);
    }

    @SneakyThrows
    @Override
    public Map<String, Object> post(URL Url, T t) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(Url.toURI());
        ObjectMapper objectMapper = new ObjectMapper();
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(t));
        httpPost.setEntity(entity);
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE,"application/json");
        return buildMap(httpClient.execute(httpPost));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> put(URL Url, T t) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(Url.toURI());
        ObjectMapper objectMapper = new ObjectMapper();
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(t));
        httpPut.setEntity(entity);
        httpPut.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPut.setHeader(HttpHeaders.CONTENT_TYPE,"application/json");
        return buildMap(httpClient.execute(httpPut));
    }

    @SneakyThrows
    @Override
    public Map<String, Object> delete(URL Url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(Url.toURI());
        return buildMap(httpClient.execute(httpDelete));
    }

    @SneakyThrows
    private Map<String, Object> buildMap(HttpResponse response){
        Map<String, Object> responseParts = new HashMap<>();
        responseParts.put(bodyKey, EntityUtils.toString(response.getEntity()));
        responseParts.put(headKey, Arrays.asList(response.getAllHeaders()));
        responseParts.put(statusCodeKey, response.getStatusLine().getStatusCode());
        return responseParts;
    }

}
