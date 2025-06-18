/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.userinfordao;

import DAO.DBConnection;
import java.time.LocalDate;
import java.util.List;
import model.UserInfor;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class UserInforDAO implements IUserInforDAO {
    private static final String INSERT = "INSERT INTO UserInfor (phone, birthday, gender, address, introduction) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM UserInfor WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM UserInfor";
    private static final String UPDATE = "UPDATE UserInfor SET phone = ?, birthday = ?, gender = ?, address = ?, introduction = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM UserInfor WHERE id = ?";

    @Override
    public void insert(UserInfor ui) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, ui.getPhone());
            ps.setDate(2, Date.valueOf(ui.getBirthDay()));
            ps.setString(3, ui.getGender());
            ps.setString(4, ui.getAddress());
            ps.setString(5, ui.getIntroduction());
            ps.executeUpdate();
        }
    }

    @Override
    public UserInfor select(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserInfor(
                    rs.getInt("id"),
                    rs.getString("phone"),
                    rs.getDate("birthday").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("introduction")
                );
            }
        }
        return null;
    }

    @Override
    public List<UserInfor> selectAll() throws SQLException {
        List<UserInfor> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserInfor(
                    rs.getInt("id"),
                    rs.getString("phone"),
                    rs.getDate("birthday").toLocalDate(),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("introduction")
                ));
            }
        }
        return list;
    }

    @Override
    public boolean update(UserInfor ui) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, ui.getPhone());
            ps.setDate(2, Date.valueOf(ui.getBirthDay()));
            ps.setString(3, ui.getGender());
            ps.setString(4, ui.getAddress());
            ps.setString(5, ui.getIntroduction());
            ps.setInt(6, ui.getId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public static void main(String[] args) {
        UserInforDAO dao = new UserInforDAO();
        try {
            UserInfor ui = new UserInfor(1, "0909090909", LocalDate.of(1995, 1, 1), "Nam", "TP.HCM", "Giới thiệu...");
            dao.insert(ui);

            List<UserInfor> all = dao.selectAll();
            UserInfor fetched = all.getFirst();
            System.out.println("All: " + all);

            fetched.setPhone("0123456789");
            dao.update(fetched);

            dao.delete(fetched.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}