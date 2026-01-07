package clients;

import base.BaseTest;
import com.erdoganpacaci.dto.DtoCarUI;
import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

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

    public Response createCar(String accessToken, DtoCarUI dtoCarUI){

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+ accessToken)
                .body(dtoCarUI)
                .when()
                .post("/rest/api/car/save")
                .then()
                .log().all()
                .extract()
                .response();

    }

    public Response deleteCar(String accessToken,Long id){
      /*  return given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", id)
                .when()
                .request(Method.DELETE,"rest/api/car/delete/{id}")
                .andReturn();
*/

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .pathParam("id", id)
                .expect()
                .statusCode(400)
                .when()
                .delete("rest/api/car/delete/{id}");

    }



    public Response updateCar(String token, Long id,DtoCarUI dtoCarUI){

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization" , "Bearer " + token)
                .pathParam("id", id)
                .body(dtoCarUI)
                .when()
                .put("/rest/api/car/update/{id}")
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response();


    }

}
