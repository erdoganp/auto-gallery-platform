package clients;

import base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountClient  extends BaseTest {


    public Response getAllAccounts(String accessToken) {

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization" ,"Bearer " + accessToken)
                .when()
                .get("/rest/api/account/getAllAccounts")
                .then()
                .extract().response();
    }
}
