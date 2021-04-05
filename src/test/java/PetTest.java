import BaseApiClient.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.*;
import logic.URLBuilder;
import lombok.SneakyThrows;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URL;
import java.util.Map;

import static io.restassured.path.json.JsonPath.reset;

public class PetTest {
    private final int statusCode = 200;
    private SoftAssert softAssert;
    private BaseApiClient<Pet> baseApiClient;
    private final String url = "https://petstore.swagger.io/v2/pet";
    private final String serverName = "Jetty(9.2.9.v20150224)";

    @BeforeMethod
    public void setup() {
        baseApiClient = new HttpClient<Pet>();
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void afterMethod() {
        reset();
    }

    @Parameters("status")
    @SneakyThrows
    @Test
    public void getPetByStatusApiTest(String status) {
        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder.addUrlPath("findByStatus");
        urlBuilder.addParams("status", status);
        URL getPet = urlBuilder.buildURL(url);
        Map<String, Object> responseMap = baseApiClient.get(getPet);

        softAssert.assertTrue(responseMap.get(BaseApiClient.statusCodeKey).equals(statusCode));
        softAssert.assertTrue(responseMap.get(BaseApiClient.bodyKey).toString().contains(status));
        softAssert.assertTrue(responseMap.get(BaseApiClient.headKey).toString().contains(serverName));
        softAssert.assertAll();
    }

    @Parameters("pet")
    @SneakyThrows
    @Test
    public void postPetApiTest(String petJson) {
        URLBuilder urlBuilder = new URLBuilder();
        URL postPet = urlBuilder.buildURL(url);
        Pet pet = new ObjectMapper().readValue(petJson, Pet.class);
        Map<String, Object> responseMap = baseApiClient.post(postPet, pet);

        softAssert.assertTrue(responseMap.get(BaseApiClient.statusCodeKey).equals(statusCode));
        softAssert.assertTrue(responseMap.get(BaseApiClient.bodyKey).toString().contains(pet.getStatus()));
        softAssert.assertTrue(responseMap.get(BaseApiClient.headKey).toString().contains(serverName));
        softAssert.assertAll();
    }

    @Parameters("pet")
    @Test
    @SneakyThrows
    public void putPetApiTest(String petJson) {
        URLBuilder urlBuilder = new URLBuilder();
        URL putPet = urlBuilder.buildURL(url);
        Pet pet = new ObjectMapper().readValue(petJson, Pet.class);
        Map<String, Object> responseMap = baseApiClient.put(putPet, pet);

        softAssert.assertTrue(responseMap.get(BaseApiClient.statusCodeKey).equals(statusCode));
        softAssert.assertTrue(responseMap.get(BaseApiClient.bodyKey).toString().contains(pet.getStatus()));
        softAssert.assertTrue(responseMap.get(BaseApiClient.headKey).toString().contains(serverName));
        softAssert.assertAll();
    }

    @Parameters("id")
    @Test
    @SneakyThrows
    public void deleteByIdPetApiTest(String id) {
        URLBuilder urlBuilder = new URLBuilder();
        urlBuilder.addUrlPath(id);
        URL deletePet = urlBuilder.buildURL(url);
        Map<String, Object> responseMap = baseApiClient.delete(deletePet);

        softAssert.assertTrue(responseMap.get(BaseApiClient.statusCodeKey).equals(statusCode));
        softAssert.assertTrue(responseMap.get(BaseApiClient.bodyKey).toString().contains(id));
        softAssert.assertTrue(responseMap.get(BaseApiClient.headKey).toString().contains(serverName));
        softAssert.assertAll();
    }
}
