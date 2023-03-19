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
public class TestCreateUserNegative {

    private ValidatableResponse response;
    private UserSerial data;
    private UserRequests user;
    private String email;
    private String password;
    private String name;
    private int statusCode;
    private boolean success;
    private String message;

    public TestCreateUserNegative(String email,
                                  String password,
                                  String name,
                                  int statusCode,
                                  boolean success,
                                  String message){
        this.email = email;
        this.password = password;
        this.name = name;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }
    @Parameterized.Parameters(name = "Тестовые данные: email = {0} | " +
            "password = {1} | " +
            "name = {2} | " +
            "statusCode = {3}")
    public  static Object [][] data () {
        return new Object[][] {
                {"login@ma.il", "", "Petrov", 403, false, "Email, password and name are required fields"},
                {"", "123456", "Petrov", 403, false, "Email, password and name are required fields"},
                {"login@ma.il", "123456", "", 403, false, "Email, password and name are required fields"},

                {"login@ma.il", null, "Petrov", 403, false, "Email, password and name are required fields"},
                {null, "123456", "Petrov", 403, false, "Email, password and name are required fields"},
                {"login@ma.il", "123456", null, 403, false, "Email, password and name are required fields"},

                {"test@te.st", "123456", "Test", 403, false, "User already exists"}

        };
    }
    @Before
    public void setUp() {
        user = new UserRequests();
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Проверка statusCode и тела ответа при негативных сценариях")
    public void createNewUser(){
        data = new UserSerial(email,password,name);
        ValidatableResponse response = user.create(data);
        response.statusCode(statusCode)
                .and().assertThat().body("success", is(success))
                .and().assertThat().body("message", is(message));
    }
}
