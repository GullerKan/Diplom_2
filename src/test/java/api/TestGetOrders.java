package api;

import api.client.OrderRequests;
import api.client.UserRequests;
import api.model.UserSerial;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class TestGetOrders {
    private ValidatableResponse response, responseLogin;
    private UserSerial data;
    private OrderRequests order;
    private UserRequests user;
    private String accessToken;
    @Before
    public void setUp() {
        order = new OrderRequests();
        user = new UserRequests();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Пользователь авторизован, есть созданные заказы")
    public void getOrdersWithAuth(){
        data = new UserSerial("test@te.st", "123456",null);
        ValidatableResponse responseLogin = user.login(data);
        accessToken = responseLogin.extract().path("accessToken");

        ValidatableResponse response = order.getOrdersWithAuth(accessToken);
        response.statusCode(200)
                .and().assertThat().body("success", is(true));
    }
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Пользователь неавторизован, список заказов не получен")
    public void getOrdersWithoutAuth(){
        ValidatableResponse response = order.getOrders();
        response.statusCode(401)
                .and().assertThat().body("success", is(false))
                .and().assertThat().body("message", is("You should be authorised"));
    }
}
