package DAO.user;

import java.sql.SQLException;          // ⬅ thêm
import java.util.List;
import model.User;
import model.UserInfor;               // (giữ lại nếu UserInfor sẽ dùng)

public interface IUserDAO {
    public User checkLogin(String username, String password);
    public void insertUser(User user) throws SQLException;
    public List<User> search(String searchName);
    public User selectUser(int id);
    public List<User> selectAllUsers();
    public boolean deleteUser(int id) throws SQLException;
    public boolean updateUser(User user) throws SQLException;
    UserInfor getUserInforById(Integer id);  
}
