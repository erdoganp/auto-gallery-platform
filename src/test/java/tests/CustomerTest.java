package tests;

import base.BaseTest;
import clients.AuthClient;
import clients.CustomerClient;
import com.erdoganpacaci.dto.DtoCustomer;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest extends BaseTest {

    AuthClient authClient = new AuthClient();
    CustomerClient customerClient =new CustomerClient();

    @Test
    public void shouldGetTheCustumer(){

        Long id = 1L;
        String accessToken;

        Map<String,String> userCredential = new HashMap<>();

        userCredential.put("username" , "erdogan");
        userCredential.put("password" , "1234");

        Response authResponse  = authClient.getAuthResponse(userCredential);

        JsonPath authJson = authResponse.jsonPath();
        accessToken = authJson.getString("payload.accessToken");

        Response customerResponse = customerClient.getTheCustomer(id, accessToken);

        JsonPath customerJson= customerResponse.jsonPath();

        customerResponse.then()
                .body("payload.id" , equalTo(id.intValue()))
                .body("payload.createTime", notNullValue())
                .body("payload.firstName", notNullValue())
                .body("payload.lastName", notNullValue())
                .body("payload.birthOfDate", notNullValue())
                .body("payload.address.id", notNullValue())
                .body("payload.address.createTime", notNullValue())
                .body("payload.address.city", notNullValue())
                .body("payload.address.district", notNullValue())
                .body("payload.address.neighborhood", notNullValue())
                .body("payload.address.street", notNullValue())
                .body("payload.account.id", notNullValue())
                .body("payload.account.createTime", notNullValue())
                .body("payload.account.accountNo", notNullValue())
                .body("payload.account.iban", notNullValue())
                .body("payload.account.amount", notNullValue())
                .body("payload.account.currencyType", notNullValue());



    }

    @Test
    public void shouldGetallCustomer(){

        String accessToken;

        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        Response authResponse=authClient.getAuthResponse(userCredential);
        JsonPath jsonAuth=authResponse.jsonPath();
        accessToken = jsonAuth.getString("payload.accessToken");

        List<DtoCustomer> customers  = customerClient.getAllCustomer(accessToken);

        boolean customerIsValid = customers.stream()
                .allMatch(customer ->customer.getFirstName() != null &&
                                                customer.getLastName()   !=null  &&
                                                customer.getTckn()       !=null  &&
                                                customer.getAccount()    !=null  );



        assertTrue(customerIsValid, "Expected all values valid");




    }
}
