package log_in;

public class LoginInputData {
    private String username;
    private String password;

    public LoginInputData(String name, String password){
        this.username = name;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
}
