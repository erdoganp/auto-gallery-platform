package tests;

import clients.AuthClient;
import clients.CarClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTest {

    AuthClient authClient =new AuthClient();
    CarClient carClient = new CarClient();

    @Test
    public void succussfullyGetAllCars(){

        String accessToken;

        Map<String, String> userCredential =new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        Response authResponse = authClient.getAuthResponse(userCredential);
        JsonPath jsonPathAuth= authResponse.jsonPath();
        accessToken = jsonPathAuth.getString("payload.accessToken");

        Response clientResponse = carClient.getCars(accessToken);
        JsonPath clientJsonPath=clientResponse.jsonPath();

        assertNotNull(clientJsonPath);
    }

}
