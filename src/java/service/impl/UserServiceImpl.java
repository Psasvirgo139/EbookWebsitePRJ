package service.impl;

import DAO.user.IUserDAO;
import DAO.user.UserDAO;
import java.util.List;
import model.User;
import model.UserInfor;
import service.UserService;

public class UserServiceImpl implements UserService {

    private IUserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public boolean createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }
    // Trong UserServiceImpl.java
@Override
public User login(String usernameOrEmail, String password) {
    // Dùng UserDAO để kiểm tra thông tin đăng nhập
    return userDAO.findByUsernameOrEmailAndPassword(usernameOrEmail, password);
}


    @Override
public UserInfor getUserInforById(Integer id) {
    return userDAO.getUserInforById(id);
}

}
