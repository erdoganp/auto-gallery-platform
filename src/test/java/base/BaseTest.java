package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class BaseTest {

    @BeforeAll
   static void setup(){

        RestAssured.baseURI="http://backend:8080";
        System.out.println(">>> USING BASE_URL = " + RestAssured.baseURI);

     
        //RestAssured.baseURI = System.getenv("BASE_URL");
       // RestAssured.baseURI="http://localhost:8080";

        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
}
