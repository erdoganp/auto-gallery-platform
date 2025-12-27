package tests;

import base.BaseTest;
import clients.AccountClient;
import clients.AuthClient;
import com.erdoganpacaci.dto.DtoAccountUI;
import com.erdoganpacaci.enums.CurrencyType;
import com.erdoganpacaci.model.Account;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountTest extends BaseTest {

    AccountClient accountClient= new AccountClient();
    AuthClient authClient= new AuthClient();

    @Test
    public void shouldGetAllAccountTest(){


        String accessToken;

        Map<String, String> userCredential = new HashMap<>();

        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");



        Response response= authClient.getAuthResponse(userCredential);

        JsonPath jsonPathToken = response.jsonPath();
        accessToken=jsonPathToken.getString("payload.accessToken");

        Response responseAccount =accountClient.getAllAccounts(accessToken);

        JsonPath jsonPath=responseAccount.getBody().jsonPath();

        assertNotNull(jsonPath.getString("payload[0].id"), "id null olamaz");
        assertNotNull(jsonPath.getString("payload[0].createTime"), "createTime null olamaz");
        assertNotNull(jsonPath.getString("payload[0].accountNo"), "accountNo null olamaz");
        assertNotNull(jsonPath.getString("payload[0].iban"), "iban null olamaz");
        assertNotNull(jsonPath.getString("payload[0].amount"), "amount null olamaz");
        assertNotNull(jsonPath.getString("payload[0].currencyType"), "currency null olamaz");
    }

    @Test
    public void shouldCreateAccountSuccussfully(){

        AccountClient accountClient= new AccountClient();
        DtoAccountUI dtoAccountUI=new DtoAccountUI();

        String accessToken;

       Map<String,String> userCredential = new HashMap<>();
       userCredential.put("username","erdogan");
       userCredential.put("password","1234");
       Response response= authClient.getAuthResponse(userCredential);
       JsonPath jsonPathToken = response.jsonPath();

       accessToken=jsonPathToken.getString("payload.accessToken");

        dtoAccountUI.setAccountNo("123456");
        dtoAccountUI.setIban("123456789");
        dtoAccountUI.setAmount(BigDecimal.valueOf(789556));
        dtoAccountUI.setCurrencyType(CurrencyType.EUR);

       Response responseAccount= accountClient.createAccount(accessToken,dtoAccountUI);

       JsonPath jsonPath=responseAccount.getBody().jsonPath();

       assertNotNull(jsonPath, "Response null olamaz");
       assertEquals(jsonPath.getString("payload.accountNo"), dtoAccountUI.getAccountNo());
       assertEquals(jsonPath.getString("payload.iban"), dtoAccountUI.getIban());
       assertEquals(jsonPath.getInt("payload.amount"), dtoAccountUI.getAmount().toBigInteger().intValue());
       assertEquals(jsonPath.getString("payload.currencyType"), dtoAccountUI.getCurrencyType().toString());

    }
    @Test
    public void shouldDeleteAccountDeleteSuccessfully(){

        AccountClient accountClient=new AccountClient();


        Long id=8L;


        Map<String,String> userCredential =new HashMap<>();
        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        Response responseaAuth= authClient.getAuthResponse(userCredential);

        JsonPath  jsonPathAuth=responseaAuth.jsonPath();
        String accessToken=jsonPathAuth.getString("payload.accessToken");

        Response responseDelete= accountClient.deleteAccount(accessToken,id);

        JsonPath jsonPathAccount=responseDelete.jsonPath();

        assertNotNull(jsonPathAccount);
        assertEquals("200" , jsonPathAccount.getString("status"));

    }

    @Test
    public void shouldUpdateAccountSuccesfully(){

        Long accountNo=123456L;
        String accessToken;
        DtoAccountUI dtoAccountUI =new DtoAccountUI();
        dtoAccountUI.setAccountNo("89888889999");
        dtoAccountUI.setIban("TR04222299988899");
        dtoAccountUI.setAmount(BigDecimal.valueOf(89755));
        dtoAccountUI.setCurrencyType(CurrencyType.EUR);


        Map<String , String> userCredential =new HashMap<>();
        userCredential.put("username", "erdogan");
        userCredential.put("password", "1234");

        Response autResponse=authClient.getAuthResponse(userCredential);
        JsonPath authJson=autResponse.jsonPath();
        accessToken=authJson.getString("payload.accessToken");

       Response responseAccount= accountClient.updateAccount(accessToken, accountNo, dtoAccountUI);

       JsonPath jsonpathAccount =responseAccount.jsonPath();

       assertNotNull(jsonpathAccount);






    }

}
