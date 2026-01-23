package clients;

import base.BaseTest;
import com.erdoganpacaci.dto.DtoCustomer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CustomerClient extends BaseTest {


    public Response getTheCustomer(Long id, String accessToken){

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

    public List<DtoCustomer> getAllCustomer(String accessToken){

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization","Bearer " + accessToken)
                .when()
                .get("/rest/api/customer/all")
                .then()
                .log().all()
                .extract()
                .jsonPath()
                .getList("payload", DtoCustomer.class);


    }
}
