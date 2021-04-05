package BaseApiClient;

import java.net.URL;
import java.util.Map;

public interface BaseApiClient<T> {
    String bodyKey = "Body";
    String headKey = "Head";
    String statusCodeKey = "StatusCode";

    Map<String,Object> get(URL Url, Map <String,String> headers);
    Map<String,Object> post(URL Url, T t, Map <String,String> headers);
    Map <String, Object> put(URL  Url, T t, Map <String,String> headers);
    Map <String, Object> delete(URL Url, Map <String,String> headers);
}
