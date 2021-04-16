package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;

public class JsonSerializer {

    @SneakyThrows
    public static <T> T convertToClass(File file, String json, Class<T> t) {
        ObjectMapper objectMapper = new ObjectMapper();
        return file != null ? objectMapper.readValue(file, t) : objectMapper.readValue(json, t);
    }

    @SneakyThrows
    public static <T> String convertToJson(T t) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(t);
    }
}
