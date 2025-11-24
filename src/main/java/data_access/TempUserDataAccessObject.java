package data_access;
import entity.User;
import log_in.LoginDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class TempUserDataAccessObject implements LoginDataAccessInterface{
    private final Map<String, User> users = new HashMap<>();
    private String currentUser;

    @Override
    public boolean existsByName(String username) {
        return users.containsKey(username);
    }

    @Override
    public void save(User save) {
        users.put(save.getName(), save);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUser = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUser;
    }
}
