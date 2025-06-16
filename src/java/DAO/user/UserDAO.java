/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.user;

/**
 *
 * @author ADMIN
 */
import DAO.DBConnection;
import model.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private static final String LOGIN = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
    private static final String INSERT = "INSERT INTO Users (username, email, password_hash, avatar_url, role, status, userinfor_id, last_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM Users";
    private static final String SELECT_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Users WHERE username LIKE ?";
    private static final String DELETE = "DELETE FROM Users WHERE id = ?";
    private static final String UPDATE = "UPDATE Users SET username = ?, email = ?, password_hash = ?, avatar_url = ?, role = ?, status = ?, userinfor_id = ?, last_login = ? WHERE id = ?";

    @Override
    public User checkLogin(String username, String password) {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(LOGIN)) {

            ps.setString(1, username);
            ps.setString(2, password); // Giả sử mật khẩu chưa hash

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getAvatarUrl());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());

            if (user.getUserinforId() != null) {
                ps.setInt(7, user.getUserinforId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            if (user.getLastLogin() != null) {
                ps.setDate(8, Date.valueOf(user.getLastLogin()));
            } else {
                ps.setNull(8, Types.DATE);
            }

            ps.executeUpdate();
        }
    }

    @Override
    public List<User> search(String searchName) {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SEARCH_BY_NAME)) {

            ps.setString(1, "%" + searchName + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User selectUser(int id) {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getAvatarUrl());
            ps.setString(5, user.getRole());
            ps.setString(6, user.getStatus());

            if (user.getUserinforId() != null) {
                ps.setInt(7, user.getUserinforId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            if (user.getLastLogin() != null) {
                ps.setDate(8, Date.valueOf(user.getLastLogin()));
            } else {
                ps.setNull(8, Types.DATE);
            }

            ps.setInt(9, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // Helper method
    private User mapUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("avatar_url"),
                rs.getString("role"),
                (rs.getDate("created_at") != null) ? rs.getDate("created_at").toLocalDate() : null,
                (rs.getObject("userinfor_id") != null) ? rs.getInt("userinfor_id") : null,
                rs.getString("status"),
                (rs.getDate("last_login") != null) ? rs.getDate("last_login").toLocalDate() : null
        );
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();

        // 1. Test insert
        User newUser = new User(0, "ngockhoi", "khoi@example.com", "123456", null,
                "user", LocalDate.now(), null, "active", null);
        try {
            dao.insertUser(newUser);
            System.out.println("✅ Insert user thành công.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2. Test login
        User loginUser = dao.checkLogin("ngockhoi", "123456");
        if (loginUser != null) {
            System.out.println("✅ Đăng nhập thành công: " + loginUser);
        } else {
            System.out.println("❌ Sai tài khoản hoặc mật khẩu.");
        }

        // 3. Test select by ID
        User u = dao.selectUser(loginUser.getId());
        if (u != null) {
            System.out.println("🔎 User theo ID: " + u);
        }

        // 4. Test search
        List<User> found = dao.search("khoi");
        System.out.println("🔍 Tìm thấy " + found.size() + " user:");
        for (User user : found) {
            System.out.println(" - " + user);
        }

        // 5. Test update
        if (u != null) {
            u.setEmail("newemail@example.com");
            u.setRole("admin");
            try {
                boolean updated = dao.updateUser(u);
                System.out.println(updated ? "✅ Cập nhật thành công" : "❌ Cập nhật thất bại");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // 6. Test select all
        List<User> all = dao.selectAllUsers();
        System.out.println("📋 Danh sách tất cả user:");
        for (User user : all) {
            System.out.println(user);
        }

        // 7. Test delete
        try {
            boolean deleted = dao.deleteUser(u.getId()); // xóa user vừa insert
            System.out.println(deleted ? "🗑 Xóa user thành công" : "⚠️ Không tìm thấy user để xóa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
