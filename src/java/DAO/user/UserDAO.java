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

    private static final String LOGIN = "SELECT * FROM Users WHERE (username = ? OR email = ?) AND password_hash = ? AND status != 'deleted'";
    private static final String INSERT = "INSERT INTO Users (username, email, password_hash, avatar_url, role, status, userinfor_id, last_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM Users";
    private static final String SELECT_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Users WHERE username LIKE ?";
    private static final String DELETE = "DELETE FROM Users WHERE id = ?";
    private static final String UPDATE = "UPDATE Users SET username = ?, email = ?, password_hash = ?, avatar_url = ?, role = ?, status = ?, userinfor_id = ?, last_login = ? WHERE id = ?";

    @Override
    public User checkLogin(String username, String password) {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(LOGIN)) {

            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);

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
}
