package clients;

import base.BaseTest;
import com.erdoganpacaci.dto.DtoGalleristCar;
import io.restassured.http.ContentType;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GalleristCarClient extends BaseTest {

    public List<DtoGalleristCar> getGaleristCar(String accessToken){

            return given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + accessToken)
                    .when()
                    .get("/rest/api/gallerist-car/list")
                    .then()
                    .log().all()
                    .extract()
                    .jsonPath()
                    .getList("payload", DtoGalleristCar.class);





    }
}
