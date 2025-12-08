package tests;

import clients.AddressClient;
import clients.AuthClient;
import com.erdoganpacaci.dto.DtoAddressUI;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressTest {

    AddressClient  addressClient = new AddressClient();
    AuthClient authClient = new AuthClient();

    @Test
    public void getTheAddress(){

        Long addressId=14L;
        String accessToken;

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAddress=  addressClient.getAdress(addressId,accessToken);

        JsonPath jsonPath=responseAddress.getBody().jsonPath();

        assertNotNull(jsonPath, "Response null olamaz");
        assertEquals("200",jsonPath.getString("status"));
        assertEquals(addressId.toString(),jsonPath.getString("payload.id"));
        assertNotNull(jsonPath.getString("payload.createTime"));
        assertNotNull(jsonPath.getString("payload.district"));
        assertNotNull(jsonPath.getString("payload.neighborhood"));
        assertNotNull(jsonPath.getString("payload.street"));



    }


    //@Test
    public void getTheAddressNegativeTest(){

        Long addressId=1L;
        String accessToken;

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAddress=  addressClient.getAdress(addressId,accessToken);

        JsonPath jsonPath=responseAddress.getBody().jsonPath();

        assertEquals("500",jsonPath.getString("status"));
        assertEquals("kayıt bulunamadı",jsonPath.getString("exception.message"));

    }
    @Test
    public void saveTheAddressTest(){
        String accessToken;
        DtoAddressUI dtoAddressUI = new DtoAddressUI();
        dtoAddressUI.setCity("İzmir");
        dtoAddressUI.setDistrict("güzelçamlı");
        dtoAddressUI.setNeighborhood("aydın");
        dtoAddressUI.setStreet("merkez");


        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAddress=  addressClient.saveAddress(accessToken,dtoAddressUI);
        JsonPath jsonPath=responseAddress.getBody().jsonPath();
        assertEquals("200",jsonPath.getString("status"));
        assertEquals(dtoAddressUI.getCity(),jsonPath.getString("payload.city"));
        assertEquals(dtoAddressUI.getDistrict(),jsonPath.getString("payload.district"));
        assertEquals(dtoAddressUI.getNeighborhood(),jsonPath.getString("payload.neighborhood"));
        assertEquals(dtoAddressUI.getStreet(),jsonPath.getString("payload.street"));


    }

    @Test
    public  void deleteAddressTest(){

        Long addressId=15L;
        String accessToken;

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAddress=  addressClient.deleteAddress(addressId,accessToken);

        JsonPath jsonPath=responseAddress.getBody().jsonPath();

        assertEquals("200",jsonPath.getString("status"));


    }

}
