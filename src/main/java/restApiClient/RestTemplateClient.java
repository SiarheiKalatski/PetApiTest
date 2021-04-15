package restApiClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restApiClient.model.Answer;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import restApiClient.model.HttpMethods;

import java.util.Map;
import java.util.stream.Collectors;

import static utils.JsonSerializer.*;

public class RestTemplateClient extends BaseRestClient {
    private Logger logger = LogManager.getLogger();
    private String uri;
    private HttpEntity request;
    private ResponseEntity response;

    @Override
    public <T> BaseRestClient buildRequest(String url, Map<String, String> path, Map<String, String> queryParams, Map<String, String> headers, T t) {
        buildUrl(url,path,queryParams);
        setHeadersAndBody(headers,t);
        return this;
    }

    private RestTemplateClient buildUrl(String url, Map<String, String> pathParams, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        queryParams.forEach(builder::queryParam);
        uri = builder.buildAndExpand(pathParams).toString();
        logger.info(uri);
        return this;
    }

    private <T> RestTemplateClient setHeadersAndBody(Map<String, String> headers, T t) {
        HttpHeaders header = new HttpHeaders();
        headers.forEach(header::set);
        request = new HttpEntity(t, header);
        return this;
    }

    @Override
    public Answer sendRequest(HttpMethods httpMethodName, Class<?> tClass) {
        RestTemplate restTemplate = new RestTemplate();
        switch (httpMethodName) {
            case GET:
                response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
                break;
            case POST:
                response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
                break;
            case PUT:
                response = restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);
                break;
            case DELETE:
                response = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
                break;
        }
        return buildAnswer(tClass);
    }

    @SneakyThrows
    @Override
    public Answer buildAnswer(Class<?> tClass) {
        Answer answer = new Answer();
        answer.setStatusCode(response.getStatusCodeValue());
        logger.info(response.getBody());
        answer.setBody(convertToClass(null, response.getBody().toString(), tClass));
        answer.setHead(response.getHeaders().entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));
        return answer;
    }
}
