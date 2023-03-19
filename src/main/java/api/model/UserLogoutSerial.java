package api.model;

public class UserLogoutSerial {
    private String token;
    public UserLogoutSerial(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
