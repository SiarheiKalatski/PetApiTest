package logic;

import lombok.SneakyThrows;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class URLBuilder {
    private Map<String, String> params = new HashMap<>();
    private String additionalPath = "";

    @SneakyThrows
    public URL buildURL(String baseUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseUrl).append(additionalPath);
        if (params.size() != 0) {
            stringBuilder.append("?");
            params.forEach((key, value) -> stringBuilder.append(key).append("=").append(value));
        }
        return new URL(stringBuilder.toString());
    }

    public URLBuilder addParams(String key, String value) {
        params.put(key, value);
        return this;
    }

    public URLBuilder addUrlPath(String path) {
        additionalPath = additionalPath + "/" + path;
        return this;
    }
}
