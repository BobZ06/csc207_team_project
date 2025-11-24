package log_in;
import entity.User;

public interface LoginDataAccessInterface {
    boolean existsByName(String username);
    void save(User save);
    User get(String username);
    void setCurrentUsername(String name);
    String getCurrentUsername();
}
