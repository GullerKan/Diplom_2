package api;

import api.client.OrderRequests;
import api.client.UserRequests;
import api.model.OrderSerial;
import api.model.UserLogoutSerial;
import api.model.UserSerial;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class TestCreateOrder {

    private ValidatableResponse response, responseLogin;
    private OrderSerial data;
    private UserSerial dataAuth;
    private UserLogoutSerial dataLogout;
    private OrderRequests order;
    private UserRequests user;
    private String accessToken;
    private String refreshToken;
    private List<String> ingredients;
    private int statusCode;
    private boolean success;
    private String message;

    public TestCreateOrder(List<String> ingredients, int statusCode,boolean success, String message){
        this.ingredients = ingredients;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters(name = "Тестовые данные: ingredients = {0} | " +
            "statusCode = {1} | success = {2} | message = {3} |")
    public  static Object [][] data () {
        return new Object[][] {
                {List.of("61c0c5a71d1f82001bdaaa6d"), 200, true, null},
                {List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa7a"),
                        200, true, null},
                {List.of("60d3b41abdacab0026a73"), 500, false, null},
                {null, 400, false, "Ingredient ids must be provided"},
                {List.of("60d3b41abdacab0026a733c6"), 400, false, "One or more ids provided are incorrect"},
        };
    }
    @Before
    public void setUp() {
        order = new OrderRequests();
        user = new UserRequests();
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Проверка различных тестовых сценариев создания заказа")
    public void createOrderWithAuth(){
        dataAuth = new UserSerial("test@te.st", "123456",null);
        ValidatableResponse responseLogin = user.login(dataAuth);
        accessToken = responseLogin.extract().path("accessToken");
        refreshToken = responseLogin.extract().path("refreshToken");

        data = new OrderSerial(ingredients);
        ValidatableResponse response = order.createWithAuth(data,accessToken);
        if(response.extract().statusCode() != 500) {
            response.statusCode(statusCode)
                    .and().assertThat().body("success", is(success))
                    .and().assertThat().body("message", is(message));
        } else{
            response.assertThat().statusCode(statusCode)
                    .contentType(ContentType.HTML);
        }
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    @Description("Проверка различных тестовых сценариев создания заказа")
    public void createOrderWithoutAuth(){
        data = new OrderSerial(ingredients);
        ValidatableResponse response = order.create(data);
       if(response.extract().statusCode() != 500){
        response.statusCode(statusCode)
                .and().assertThat().body("success", is(success))
                .and().assertThat().body("message", is(message));
        } else {
            response.assertThat().statusCode(statusCode)
                    .contentType(ContentType.HTML);
        }
    }
    @After
    public void deleteUser(){
        if(refreshToken != null) {
            dataLogout = new UserLogoutSerial(refreshToken);
            user.logout(dataLogout);
        }
    }

}
