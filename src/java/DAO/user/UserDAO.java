package DAO.user;

import DAO.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.UserInfor;

public class UserDAO implements IUserDAO {

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE status != 'deleted'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("avatar_url"),
                        rs.getString("role"),
                        rs.getDate("created_at").toLocalDate(),
                        (rs.getObject("userinfor_id") != null ? rs.getInt("userinfor_id") : null),
                        rs.getString("status"),
                        rs.getDate("last_login") != null ? rs.getDate("last_login").toLocalDate() : null
                );
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("avatar_url"),
                        rs.getString("role"),
                        rs.getDate("created_at").toLocalDate(),
                        (rs.getObject("userinfor_id") != null ? rs.getInt("userinfor_id") : null),
                        rs.getString("status"),
                        rs.getDate("last_login") != null ? rs.getDate("last_login").toLocalDate() : null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (username, email, password_hash, avatar_url, role, userinfor_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getAvatarUrl());
            ps.setString(5, user.getRole());
            if (user.getUserinforId() != null) {
                ps.setInt(6, user.getUserinforId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setString(7, user.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET username=?, email=?, avatar_url=?, role=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getAvatarUrl());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        String sql = "UPDATE Users SET status = 'deleted' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

 public User findByUsernameOrEmailAndPassword(String usernameOrEmail, String password) {
    if (usernameOrEmail != null) usernameOrEmail = usernameOrEmail.trim();
    if (password != null) password = password.trim();

    // Log thông tin nhận vào từ servlet
    System.out.println("=====================================");
    System.out.println("DEBUG: username/email nhập vào: [" + usernameOrEmail + "]");
    System.out.println("DEBUG: password nhập vào: [" + password + "]");
    System.out.println("DEBUG: Chuỗi SQL: SELECT * FROM Users WHERE (username = ? OR email = ?) AND password_hash = ? AND status != 'deleted'");
    System.out.println("=====================================");

    String sql = "SELECT * FROM Users WHERE (username = ? OR email = ?) AND password_hash = ? AND status != 'deleted'";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usernameOrEmail);
        ps.setString(2, usernameOrEmail);
        ps.setString(3, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("DEBUG: Đăng nhập thành công với user: " + rs.getString("username") + ", pass: " + rs.getString("password_hash"));
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("avatar_url"),
                rs.getString("role"),
                rs.getDate("created_at").toLocalDate(),
                (rs.getObject("userinfor_id") != null ? rs.getInt("userinfor_id") : null),
                rs.getString("status"),
                rs.getDate("last_login") != null ? rs.getDate("last_login").toLocalDate() : null
            );
        } else {
            System.out.println("DEBUG: Không tìm thấy user nào!");
        }
    } catch (Exception e) {
        System.out.println("DEBUG: Lỗi khi thực thi SQL trong findByUsernameOrEmailAndPassword:");
        e.printStackTrace();
    }
    return null;
}



    @Override
    public UserInfor getUserInforById(int id) {
        String sql = "SELECT * FROM UserInfor WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserInfor(
                        rs.getInt("id"),
                        rs.getString("phone"),
                        rs.getDate("birthDay") != null ? rs.getDate("birthDay").toLocalDate() : null,
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("introduction")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
    UserDAO dao = new UserDAO();
    // Thay đổi giá trị ở đây để test nhiều trường hợp khác nhau
    String usernameOrEmail = "giangtran"; // hoặc "giangtran@example.com"
    String password = "123456";

    System.out.println("===== TEST LOGIN =====");
    User user = dao.findByUsernameOrEmailAndPassword(usernameOrEmail, password);
    if (user != null) {
        System.out.println("KẾT QUẢ: Đăng nhập thành công!");
        System.out.println("User: " + user.getUsername() + " | Email: " + user.getEmail());
    } else {
        System.out.println("KẾT QUẢ: Sai tài khoản hoặc mật khẩu!");
    }
}


}
