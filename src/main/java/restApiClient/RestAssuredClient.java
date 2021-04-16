package restApiClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import restApiClient.model.Answer;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import restApiClient.model.HttpMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class RestAssuredClient extends BaseRestClient {
    private Logger logger = LogManager.getLogger();
    private RequestSpecification requestSpecification;
    private Response response;
    private String requestUrl;

    public <T> RestAssuredClient buildRequest(String url, Map<String, String> path,
                                              Map<String, String> queryParams, Map<String, String> headers, T t) {
        requestSpecification = given();
        buildUrl(url, path, queryParams);
        setHeaderAndBody(headers, t);
        return this;
    }

    private RestAssuredClient buildUrl(String url, Map<String, String> pathParams, Map<String, String> queryParams) {
        requestUrl = url;
        pathParams.forEach((k, v) -> requestUrl = requestUrl.replace("{" + k + "}", v));
        baseURI = requestUrl;
        requestSpecification = given().queryParams(queryParams);
        logger.info(requestSpecification);
        return this;
    }

    private <T> RestAssuredClient setHeaderAndBody(Map<String, String> headers, T t) {
        requestSpecification.headers(headers);
        if (t != null) {
            requestSpecification.body(t);
        }
        return this;
    }

    @Override
    public Answer sendRequest(HttpMethods httpMethodName, Class<?> tClass) {
        switch (httpMethodName) {
            case GET:
                response = requestSpecification.get();
                break;
            case POST:
                response = requestSpecification.post();
                break;
            case PUT:
                response = requestSpecification.put();
                break;
            case DELETE:
                response = requestSpecification.delete();
                break;
        }
        return buildAnswer(tClass);
    }

    public Answer buildAnswer(Class<?> t) {
        Answer answer = new Answer();
        answer.setStatusCode(response.statusCode());
        logger.info(response.getBody());
        answer.setBody(response.then().extract().as(t));
        List<String> list = new ArrayList<>();
        for (Header header : response.getHeaders()) {
            list.add(header.getName());
            list.add(header.getValue());
        }
        answer.setHead(list);
        return answer;
    }
}
