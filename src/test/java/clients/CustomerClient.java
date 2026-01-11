package clients;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CustomerClient extends BaseTest {


    public Response getTheCar(Long id, String accessToken){

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", id)
                .when()
                .get("/rest/api/customer/{id}")
                .then()
                .log().all()
                .extract()
                .response();
    }
}
