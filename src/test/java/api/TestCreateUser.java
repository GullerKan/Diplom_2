package api;

import api.client.UserRequests;
import api.model.UserDesserial;
import api.model.UserSerial;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.util.Generate.*;
import static org.hamcrest.CoreMatchers.is;

public class TestCreateUser {

    private ValidatableResponse response;
    private UserSerial data;
    private UserRequests user;

    private String accessToken;

    @Before
    public void setUp() {
        user = new UserRequests();
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Создание нового пользователя с уникальными данными")
    public void createNewUser(){
        data = new UserSerial(EMAIL,PASSWORD,NAME);
        ValidatableResponse response = user.create(data);
        response.statusCode(200)
                .body("success", is(true)).extract().as(UserDesserial.class);
        accessToken = response.extract().path("accessToken");
    }

    @After
    public void deleteUser(){
        if(accessToken != null) {
            user.delete(accessToken);
        }
    }
}
