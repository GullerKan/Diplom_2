package api;

import api.client.UserRequests;
import api.model.UserSerial;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class TestLoginUserNegative {

    private ValidatableResponse response;
    private UserSerial data;
    private UserRequests user;

    private String email;
    private String password;

    public TestLoginUserNegative(String email,
                                  String password){
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters(name = "Тестовые данные: email = {0} | " +
            "password = {1}")
    public  static Object [][] data () {
        return new Object[][] {
                {"nevalidnyi@email","1234"},
                {"","1234"},
                {"nevalidnyi@email",""},
                {null,"1234"},
                {"nevalidnyi@email",null},
        };
    }

    @Before
    public void setUp() {
        user = new UserRequests();
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка statusCode и тела ответа при негативных сценариях")
    public void loginUser(){
        data = new UserSerial(email,password,null);
        ValidatableResponse response = user.login(data);
        response.statusCode(401)
                .and().assertThat().body("success", is(false))
                .and().assertThat().body("message", is( "email or password are incorrect"));
    }

}
