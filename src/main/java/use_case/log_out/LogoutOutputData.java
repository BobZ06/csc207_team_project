package use_case.log_out;

public class LogoutOutputData {
    private final String username;

    public LogoutOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
