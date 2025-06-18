package DAO.user;

import DAO.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import model.UserInfor;

public class UserDAO implements IUserDAO {

    /* ====================================================
       1. ĐĂNG NHẬP
       ==================================================== */
    @Override
    public User checkLogin(String usernameOrEmail, String password) {
        if (usernameOrEmail != null) usernameOrEmail = usernameOrEmail.trim();
        if (password != null)        password        = password.trim();

        String sql = "SELECT * FROM Users "
                   + "WHERE (username = ? OR email = ?) "
                   + "  AND password_hash = ? "
                   + "  AND status != 'deleted'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapUser(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ====================================================
       2. THÊM MỚI USER
       ==================================================== */
    @Override
    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO Users "
                   + "(username, email, password_hash, avatar_url, role, "
                   + " userinfor_id, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
            ps.executeUpdate();
        }
    }

    /* ====================================================
       3. TÌM KIẾM USER THEO TÊN / EMAIL (LIKE)
       ==================================================== */
    @Override
    public List<User> search(String searchName) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users "
                   + "WHERE (username LIKE ? OR email LIKE ?) "
                   + "  AND status != 'deleted'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + searchName + "%");
            ps.setString(2, "%" + searchName + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapUser(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ====================================================
       4. LẤY 1 USER THEO ID
       ==================================================== */
    @Override
    public User selectUser(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapUser(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* ====================================================
       5. LẤY TOÀN BỘ USER (CHƯA BỊ XOÁ)
       ==================================================== */
    @Override
    public List<User> selectAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE status != 'deleted'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapUser(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ====================================================
       6. CẬP NHẬT USER
       ==================================================== */
    @Override
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users "
                   + "SET username=?, email=?, avatar_url=?, role=?, status=? "
                   + "WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getAvatarUrl());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /* ====================================================
       7. “XOÁ MỀM” USER
       ==================================================== */
    @Override
    public boolean deleteUser(int id) throws SQLException {
        String sql = "UPDATE Users SET status = 'deleted' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /* ====================================================
       8. LẤY THÔNG TIN PHỤ (Tuỳ chọn – không nằm trong interface)
       ==================================================== */
    public UserInfor getUserInforById(int id) {
        String sql = "SELECT * FROM UserInfor WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

    /* ====================================================
       9. Helper: map ResultSet → User
       ==================================================== */
    private User mapUser(ResultSet rs) throws SQLException {
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
    
        @Override                              // <-- thêm chú thích
    public UserInfor getUserInforById(Integer id) {
        String sql = "SELECT * FROM UserInfor WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserInfor(
                    rs.getInt("id"),
                    rs.getString("phone"),
                    rs.getDate("birthDay") != null
                        ? rs.getDate("birthDay").toLocalDate()
                        : null,
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

    /* ====================================================
       10. (Tuỳ chọn) MAIN TEST
       ==================================================== */
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        User u = dao.checkLogin("giangtran", "123456");
        System.out.println(u == null ? "Sai tài khoản/mật khẩu" : "Login OK: " + u.getUsername());
    }
}
