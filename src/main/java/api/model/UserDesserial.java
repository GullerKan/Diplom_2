package api.model;

import io.restassured.response.ValidatableResponse;

public class UserDesserial {
    private boolean success;
    private static User user;
    private static String accessToken;
    private String refreshToken;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static String getAccessToken(ValidatableResponse response) {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
