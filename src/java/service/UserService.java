package service;

import java.util.List;
import model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
}
