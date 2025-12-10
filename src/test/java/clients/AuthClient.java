package clients;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient extends BaseTest {

    public Response getAuthResponse(Map<String,String> map) {

        return given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("/authenticate")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();




    }

    public Response postAuthRegister(Map<String,String> map) {

        return given()
                .contentType(ContentType.JSON)
                .body(map)
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

    }
}
