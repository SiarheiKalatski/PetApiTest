import restApiClient.*;
import restApiClient.model.Answer;
import models.*;
import lombok.SneakyThrows;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static utils.JsonSerializer.*;

import java.io.File;
import java.util.*;

import static io.restassured.path.json.JsonPath.reset;

public class PetTest {
    private final int statusCode = 200;
    private SoftAssert softAssert;
    private BaseApiClient baseApiClient;
    private final String baseUrl = "https://petstore.swagger.io";
    private final String type = "/{type}";
    private final String command = "/{animal}";
    private final String id = "/{PetId}";
    private final String serverName = "Jetty(9.2.9.v20150224)";
    private final File postJson = new File("src/test/resources/PostJson.json");
    private final File putJson = new File("src/test/resources/PutJson.json");
    private static final Map<String, String> headers = new HashMap<>();
    private static final Map<String, String> pathForGetDelete = new LinkedHashMap<>();
    private static final Map<String, String> pathForPostPut = new LinkedHashMap<>();
    private static final Map<String, String> queryParams = new HashMap<>();

    static {
        pathForGetDelete.put("type", "v2");
        pathForGetDelete.put("animal", "pet");
        pathForGetDelete.put("PetId", "12");
        pathForPostPut.put("type", "v2");
        pathForPostPut.put("animal", "pet");
        headers.put("Content-Type", "application/json");
    }

    @BeforeMethod
    public void setup() {
        baseApiClient = new HttpClient();
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void afterMethod() {
        reset();
    }


    @SneakyThrows
    @Test(dependsOnMethods = {"postPetApiTest"})
    public void getPetByStatusApiTest() {
        Answer answer = baseApiClient.get(baseUrl + type + command + id, pathForGetDelete, queryParams, headers,
                null, Pet.class);
        softAssert.assertTrue(answer.getStatusCode() == (statusCode));
        softAssert.assertTrue(((Pet) answer.getBody()).getId().toString().contains(pathForGetDelete.get("PetId")));
        softAssert.assertTrue(answer.getHead().toString().contains(serverName));
        softAssert.assertAll();
    }

    @SneakyThrows
    @Test
    public void postPetApiTest() {
        Pet pet = convertToClass(postJson, null, Pet.class);
        Map<String, String> queryParams = new HashMap<>();
        Answer answer = baseApiClient.post(baseUrl + type + command, pathForPostPut, queryParams, headers,
                pet, Pet.class);
        softAssert.assertTrue(answer.getStatusCode() == (statusCode));
        softAssert.assertTrue(((Pet) answer.getBody()).getName().contains(pet.getName()));
        softAssert.assertTrue(answer.getHead().toString().contains(serverName));
        softAssert.assertAll();
    }

    @Test
    public void putPetApiTest() {
        Pet pet = convertToClass(putJson, null, Pet.class);
        Answer answer = baseApiClient.put(baseUrl + type + command, pathForPostPut, queryParams, headers,
                pet, Pet.class);
        softAssert.assertTrue(answer.getStatusCode() == (statusCode));
        softAssert.assertTrue(((Pet) answer.getBody()).getId() == pet.getId());
        softAssert.assertTrue(answer.getHead().toString().contains(serverName));
        softAssert.assertAll();

    }

    @Test(dependsOnMethods = {"getPetByStatusApiTest"})
    @SneakyThrows
    public void deleteByIdPetApiTest() {
        Pet pet = convertToClass(putJson, null, Pet.class);
        Answer answer = baseApiClient.delete(baseUrl + type + command + id, pathForGetDelete, queryParams, headers,
                pet, DeletePet.class);
        softAssert.assertTrue(answer.getStatusCode() == (statusCode));
        softAssert.assertTrue(((DeletePet) answer.getBody()).getMessage().contains("12"));
        softAssert.assertTrue(answer.getHead().toString().contains(serverName));
        softAssert.assertAll();
    }
}
