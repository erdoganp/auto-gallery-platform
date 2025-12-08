package tests;

import clients.AuthClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTest {
    private AuthClient  authClient = new AuthClient();

    @Test
    void getAuth(){

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


       Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPath = response.jsonPath();

        String accessToken=jsonPath.getString("payload.accessToken");

        assertNotNull(accessToken, "access Token bos olamaz");


    }
}
