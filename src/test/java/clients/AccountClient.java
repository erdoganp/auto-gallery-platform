package clients;

import base.BaseTest;
import com.erdoganpacaci.dto.DtoAccountUI;
import com.erdoganpacaci.model.Account;
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

    public Response createAccount(String accessToken, DtoAccountUI dtoAccountUI) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+accessToken)
                .body(dtoAccountUI)
                .when()
                .post("/rest/api/account/save")
                .then()
                .log().all()
                .extract().response();


    }

    public Response updateAccount(String accessToken, Long accountNo, DtoAccountUI dtoAccountUI){
        return given()
                .header("Authorization","Bearer " + accessToken)
                .pathParam("accountNo", accountNo)
                .body(dtoAccountUI)
                .contentType(ContentType.JSON)
                .when()
                .put("/rest/api/account/update/{accountNo}")
                .then()
                .log().all()
                .extract().response();


    }

    public Response deleteAccount(String accessToken, Long id){
        return given()
                .header("Authorization" , "Bearer " + accessToken)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .when()
                .delete("/rest/api/account/delete/{id}")
                .then()
                .log().all()
                .extract().response();



    }
}
