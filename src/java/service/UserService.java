package service;

import java.util.List;
import model.User;
import model.UserInfor;

/**
 * Service-layer “hợp đồng” đã đổi tên để khớp 100 % với IUserDAO / UserDAO.
 */
public interface UserService {

    /* ========== CRUD chính ========== */
    List<User> selectAllUsers();                // lấy toàn bộ user (chưa bị xoá)
    User selectUser(int id);                    // lấy 1 user theo ID
    boolean insertUser(User user);              // thêm mới
    boolean updateUser(User user);              // cập nhật
    boolean deleteUser(int id);                 // “xoá mềm”

    /* ========== Đăng nhập ========== */
    User checkLogin(String usernameOrEmail,
                    String password);

    /* ========== Tìm kiếm ========== */
    List<User> search(String keyword);          // tìm theo tên / email (LIKE)

    /* ========== Thông tin phụ ========== */
    UserInfor getUserInforById(Integer id);     // lấy bảng UserInfor
}
