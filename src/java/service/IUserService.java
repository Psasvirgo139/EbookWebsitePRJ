package service;

import model.User;
import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    void addUser(User user) throws SQLException;
    User getUserById(int id) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    boolean modifyUser(User user) throws SQLException;   // <-- dùng modifyUser
    boolean removeUser(int id) throws SQLException;
    User checkLogin(String username, String password) throws SQLException;
}
