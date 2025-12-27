package clients;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CarClient extends BaseTest {

    public Response getCars(String accessToken){
        return given()
                .header("Authorization" , "Bearer "+ accessToken)
                .contentType(ContentType.JSON)
                .when()
                .get("/rest/api/car/list")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

}
