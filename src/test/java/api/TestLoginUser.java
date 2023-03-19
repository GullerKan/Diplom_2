package api;

import api.client.UserRequests;
import api.model.UserSerial;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.util.Generate.*;
import static org.hamcrest.CoreMatchers.is;

public class TestLoginUser {
    private ValidatableResponse response;
    private UserSerial data;
    private UserRequests user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new UserRequests();
        data = new UserSerial(EMAIL,PASSWORD,NAME);
        user.create(data);

    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка statusCode и тела ответа при успешной авторизации")
    public void authUser(){
        ValidatableResponse response = user.login(data);
        response.statusCode(200)
                .and().assertThat().body("success", is(true));
        accessToken = response.extract().path("accessToken");
    }
    @After
    public void deleteUser(){
        if(accessToken != null) {
            user.delete(accessToken);
        }
    }
}
