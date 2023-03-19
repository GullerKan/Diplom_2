package api.client;

import api.model.OrderSerial;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderRequests extends Client {
    private static final String ORDERS = "/orders";
    private static final String ALL_ORDERS = "/orders/all";
    private static final String INGREDIENTS = "/ingredients";

    @Step("Создание заказа")
    public ValidatableResponse create(OrderSerial order) {
        return given()
                .spec(getSpec())
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }
    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createWithAuth(OrderSerial order, String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .body(order)
                .post(ORDERS)
                .then().log().all();
    }
    @Step("Получение заказов неавторизованного пользователя")
    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS)
                .then().log().all();
    }
    @Step("Получение заказов авторизованного пользователя")
    public ValidatableResponse getOrdersWithAuth(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .get(ORDERS)
                .then().log().all();
    }
    @Step("Получение всех заказов")
    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ALL_ORDERS)
                .then().log().all();
    }
    @Step("Получение хэшей ингредиентов")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getSpec())
                .when()
                .get(INGREDIENTS)
                .then().log().all();
    }

}
