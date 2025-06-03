package userDao;

import model.User;
import java.util.List;

public interface IUserDao {
    List<User> findAll();
    User findById(int id);
    User findByEmail(String email);
    User findByUsername(String username);
    boolean insert(User user);
    boolean update(User user);
    boolean delete(int id);
    User login(String username, String password);
}
