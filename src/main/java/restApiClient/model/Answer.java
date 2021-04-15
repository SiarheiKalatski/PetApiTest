package restApiClient.model;

import lombok.Data;

import java.util.List;

@Data
public class Answer<T> {
    List<String> head;
    int statusCode;
    T body;
}
