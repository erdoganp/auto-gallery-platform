package clients;

import base.BaseTest;
import com.erdoganpacaci.dto.DtoAddressUI;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AddressClient  extends BaseTest {

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

    public Response saveAddress(String accessToken ,DtoAddressUI dtoAddressUI){

        return given()
                .contentType(ContentType.JSON)
                .body(dtoAddressUI)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .post("/rest/api/address/save")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    public Response deleteAddress(Long id,String accessToken){

        return given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .delete("/rest/api/address/delete/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }
}
