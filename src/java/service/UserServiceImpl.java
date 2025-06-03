package service;

import userDao.IUserDao;
import userDao.UserDao;
import model.User;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {

    private final IUserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDao();
    }

    @Override
    public void addUser(User user) throws SQLException {
        userDao.insert(user);
    }

    @Override
    public User getUserById(int id) throws SQLException {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.findAll();
    }

    @Override
    public boolean modifyUser(User user) throws SQLException {
        return userDao.update(user);
    }

    @Override
    public boolean removeUser(int id) throws SQLException {
        return userDao.delete(id);
    }

    @Override
    public User checkLogin(String username, String password) throws SQLException {
        return userDao.login(username, password);
    }
}
