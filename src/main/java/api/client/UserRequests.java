package api.client;

import api.model.UserLogoutSerial;
import api.model.UserSerial;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserRequests extends Client{
    private static final String PASSWORD_RESET = "/password-reset";
    private static final String CREATE = "/auth/register";
    private static final String AUTH = "/auth/login";
    private static final String LOGOUT = "/auth/logout";
    private static final String REFRESH_TOKEN = "/auth/token";
    private static final String USER = "/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse create(UserSerial user) {
        return given()
                .spec(getSpec())
                .when()
                .body(user)
                .post(CREATE)
                .then().log().all();
    }
    @Step("Авторизация пользователя")
    public ValidatableResponse login(UserSerial user) {
        return given()
                .spec(getSpec())
                .when()
                .body(user)
                .post(AUTH)
                .then().log().all();
    }
    @Step("Выход пользователя из системы")
    public ValidatableResponse logout(UserLogoutSerial user) {
        return given()
                .spec(getSpec())
                .when()
                .body(user)
                .post(LOGOUT)
                .then().log().all();
    }
    @Step("Получение данных пользователя")
    public ValidatableResponse getUser() {
        return given()
                .spec(getSpec())
                .when()
                .get(USER)
                .then().log().all();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .delete(USER)
                .then().log().all();
    }
    @Step("Обновление данных пользователя без авторизации")
    public ValidatableResponse edit(UserSerial user) {
        return given()
                .spec(getSpec())
                .when()
                .body(user)
                .patch(USER)
                .then().log().all();
    }
    @Step("Обновление данных авторизованного пользователя")
    public ValidatableResponse editWithAuth(UserSerial user, String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .body(user)
                .patch(USER)
                .then().log().all();
    }

}
