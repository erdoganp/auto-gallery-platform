package tests;

import base.BaseTest;
import clients.AccountClient;
import clients.AuthClient;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountTest extends BaseTest {

    AccountClient accountClient= new AccountClient();
    AuthClient authClient= new AuthClient();

    @Test
    public void getAllAccountTest(){


        String accessToken;

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        

        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAccount =accountClient.getAllAccounts(accessToken);

        JsonPath jsonPath=responseAccount.getBody().jsonPath();

        assertNotNull(jsonPath.getString("payload[0].id"), "id null olamaz");
        assertNotNull(jsonPath.getString("payload[0].createTime"), "createTime null olamaz");
        assertNotNull(jsonPath.getString("payload[0].accountNo"), "accountNo null olamaz");
        assertNotNull(jsonPath.getString("payload[0].iban"), "iban null olamaz");
        assertNotNull(jsonPath.getString("payload[0].amount"), "amount null olamaz");
        assertNotNull(jsonPath.getString("payload[0].currencyType"), "currency null olamaz");
    }


}
