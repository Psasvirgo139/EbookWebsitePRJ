package DAO.user;

import java.sql.SQLException;          // ⬅ thêm
import java.util.List;
import model.User;
import model.UserInfor;               // (giữ lại nếu UserInfor sẽ dùng)

public interface IUserDAO {
    User checkLogin(String username, String password);    // trả về null nếu sai
    void insertUser(User user) throws SQLException;
    List<User> search(String searchName);
    User selectUser(int id);
    List<User> selectAllUsers();
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
      UserInfor getUserInforById(Integer id);  
}
