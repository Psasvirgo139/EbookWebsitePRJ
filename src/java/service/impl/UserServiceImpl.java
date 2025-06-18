package service.impl;

import DAO.user.IUserDAO;
import DAO.user.UserDAO;
import java.sql.SQLException;
import java.util.List;
import model.User;
import model.UserInfor;
import service.UserService;

/**
 * Triển khai UserService khớp hoàn toàn với IUserDAO & UserService (mới).
 */
public class UserServiceImpl implements UserService {

    private final IUserDAO userDAO = new UserDAO();   // khởi tạo DAO

    /* ====================== CRUD CHÍNH ====================== */

    @Override
    public List<User> selectAllUsers() {
        return userDAO.selectAllUsers();
    }

    @Override
    public User selectUser(int id) {
        return userDAO.selectUser(id);
    }

    @Override
    public boolean insertUser(User user) {
        try {
            userDAO.insertUser(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ====================== TÌM KIẾM & ĐĂNG NHẬP ====================== */

    @Override
    public List<User> search(String keyword) {
        return userDAO.search(keyword);
    }

    @Override
    public User checkLogin(String usernameOrEmail, String password) {
        return userDAO.checkLogin(usernameOrEmail, password);
    }

    /* ====================== THÔNG TIN PHỤ ====================== */

    @Override
    public UserInfor getUserInforById(Integer id) {
        return userDAO.getUserInforById(id);
    }
}
