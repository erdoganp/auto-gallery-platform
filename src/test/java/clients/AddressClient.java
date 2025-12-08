package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AddressClient {

    public Response getAdress(Long id,String accessToken){

        return given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/rest/api/address/getTheAddress/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}
