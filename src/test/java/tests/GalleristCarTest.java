package tests;

import base.BaseTest;
import clients.AuthClient;
import clients.GalleristCarClient;
import com.erdoganpacaci.dto.DtoGalleristCar;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GalleristCarTest extends BaseTest {

    AuthClient authClient = new AuthClient();
    GalleristCarClient galleristCarClient= new GalleristCarClient();

    @Test
    public void getAllGalleristCarTest(){

        String accessToken ;

        Map<String, String> userCredential= new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        Response authResponse = authClient.getAuthResponse(userCredential);
        JsonPath  jsonAuth= authResponse.jsonPath();
        accessToken = jsonAuth.getString("payload.accessToken");

        List<DtoGalleristCar> galleristCars = galleristCarClient.getGaleristCar(accessToken);

        boolean isResponseValid = galleristCars.stream().
                                    allMatch(galleristCar ->
                                            galleristCar.getGallerist().getAddress()   !=null  &&
                                            galleristCar.getGallerist().getFirstName() != null &&
                                            galleristCar.getCar().getModel()         !=null  &&
                                            galleristCar.getCar().getProductionYear()    >0  &&
                                            galleristCar.getCar().getPrice().intValue()  >0  &&
                                            galleristCar.getCar().getPlaka()         !=null

                                    );

        assertTrue(isResponseValid, "all values should be valid");

    }

    @Test
    public void getGalleristCarById(){

        String accessToken;

        Long gallerisId=1L;

        Map<String, String> userCredential= new HashMap<>();
        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        Response authClientResponse = authClient.getAuthResponse(userCredential);
        JsonPath jsonAuth = authClientResponse.jsonPath();
        accessToken = jsonAuth.getString("payload.accessToken");

        DtoGalleristCar dtoGalleristCar = galleristCarClient.getGalleristCarById(accessToken, gallerisId );

        assertNotNull(dtoGalleristCar);
        assertNotNull(dtoGalleristCar.getGallerist().getAddress());
        assertNotNull(dtoGalleristCar.getGallerist().getFirstName());
        assertNotNull(dtoGalleristCar.getCar().getModel());
        assertNotNull(dtoGalleristCar.getCar().getPlaka());
        assertNotNull(dtoGalleristCar.getCar().getPrice());
        assertNotNull(dtoGalleristCar.getCar().getBrand());
        assertNotNull(dtoGalleristCar.getCar().getProductionYear());
        assertNotNull(dtoGalleristCar.getCar().getDamagePrice());





    }
}
