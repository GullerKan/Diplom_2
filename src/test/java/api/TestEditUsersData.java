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

public class TestEditUsersData {
    private ValidatableResponse response, responseEdit;
    private UserSerial data, dataNew;
    private UserRequests user;

    private String accessToken;
    @Before
    public void setUp() {
        user = new UserRequests();
        data = new UserSerial(EMAIL,PASSWORD,NAME);
        dataNew = new UserSerial("new@email.ru","new123","NewName");
        ValidatableResponse response = user.create(data);
        accessToken = response.extract().path("accessToken");
    }
    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Проверка statusCode и тела ответа при успешном сохранении измененных данных")
    public void editUsersDataWithAuth(){
        ValidatableResponse responseEdit = user.editWithAuth(dataNew, accessToken);
        responseEdit.statusCode(200)
                .body("success", is(true)).extract().as(UserDesserial.class);
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверка statusCode и тела ответа при ошибке авторизации")
    public void editUsersDataWithoutAuth(){
        ValidatableResponse responseEdit = user.edit(dataNew);
        responseEdit.statusCode(401)
                .body("success", is(false))
                .and().assertThat().body("message", is("You should be authorised"));;
    }

    @After
    public void deleteUser(){
        if(accessToken != null) {
            user.delete(accessToken);
        }
    }
}
