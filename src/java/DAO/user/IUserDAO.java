package DAO.user;

import java.util.List;
import model.User;
import model.UserInfor;

public interface IUserDAO {
    List<User> getAllUsers();
    User getUserById(int id);
    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id); // Soft delete: status = 'deleted'
    User findByUsernameOrEmailAndPassword(String usernameOrEmail, String password);
UserInfor getUserInforById(int id);
}
