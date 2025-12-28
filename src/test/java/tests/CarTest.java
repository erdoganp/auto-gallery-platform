package tests;

import clients.AuthClient;
import clients.CarClient;
import com.erdoganpacaci.dto.DtoCarUI;
import com.erdoganpacaci.enums.CurrencyType;
import com.erdoganpacaci.model.CarStatusType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarTest {

    AuthClient authClient =new AuthClient();
    CarClient carClient = new CarClient();

    @Test
    public void shouldSuccussfullyGetAllCars(){

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
        assertNotNull(clientJsonPath.getString("payload.plaka"));
        assertNotNull(clientJsonPath.getString("payload.brand"));
        assertNotNull(clientJsonPath.getString("payload.model"));
        assertNotNull(clientJsonPath.get("payload[0].productionYear"));
        assertNotNull(clientJsonPath.get("payload[0].price"));
        assertNotNull(clientJsonPath.get("payload[0].currencyType"));
        assertNotNull(clientJsonPath.get("payload[0].carStatusType"));

    }

    @Test
    public void shouldSuccessfullyCreateCar(){


        Map<String, String> userCredential= new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");


        String accessToken;
        DtoCarUI dtoCarUI = new DtoCarUI();
        dtoCarUI.setCarStatusType(CarStatusType.SALABLE);
        dtoCarUI.setBrand("cherry");
        dtoCarUI.setPlaka("34 ECM 04");
        dtoCarUI.setModel("tiggo pro9");
        dtoCarUI.setPrice(BigDecimal.valueOf(100000));
        dtoCarUI.setCurrencyType(CurrencyType.TL);
        dtoCarUI.setDamagePrice(BigDecimal.valueOf(5000));
        dtoCarUI.setProductionYear(2026);

        Response authResponse=authClient.getAuthResponse(userCredential);
        JsonPath authJson=authResponse.jsonPath();
        accessToken= authJson.getString("payload.accessToken");

        Response responseCarClient=  carClient.createCar(accessToken, dtoCarUI);
       JsonPath jsonPathCar= responseCarClient.jsonPath();

        assertEquals(dtoCarUI.getCarStatusType().toString(), jsonPathCar.get("payload.carStatusType"));
        assertEquals(dtoCarUI.getPlaka(), jsonPathCar.getString("payload.plaka"));
        assertEquals(dtoCarUI.getModel(), jsonPathCar.getString("payload.model"));
        assertEquals(dtoCarUI.getBrand(), jsonPathCar.getString("payload.brand"));
        assertEquals(dtoCarUI.getPrice().intValue(), (int)jsonPathCar.get("payload.price"));
        assertEquals(dtoCarUI.getCurrencyType().toString(), jsonPathCar.get("payload.currencyType"));
        assertEquals(dtoCarUI.getDamagePrice().intValue(), (int)(jsonPathCar.get("payload.damagePrice")));
        assertEquals(dtoCarUI.getProductionYear(), jsonPathCar.getInt("payload.productionYear"));



    }

}
