/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.ebook;

/**
 *
 * @author ADMIN
 */
import DAO.DBConnection;
import model.Ebook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EbookDAO implements IEbookDAO {

    private static final String INSERT = "INSERT INTO Ebooks (title, description, release_type, language, status, visibility, uploader_id, created_at, view_count, cover_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT * FROM Ebooks WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Ebooks";
    private static final String UPDATE = "UPDATE Ebooks SET title = ?, description = ?, release_type = ?, language = ?, status = ?, visibility = ?, uploader_id = ?, created_at = ?, view_count = ?, cover_url = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Ebooks WHERE id = ?";

    @Override
    public void insert(Ebook ebook) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, ebook.getTitle());
            ps.setString(2, ebook.getDescription());
            ps.setString(3, ebook.getReleaseType());
            ps.setString(4, ebook.getLanguage());
            ps.setString(5, ebook.getStatus());
            ps.setString(6, ebook.getVisibility());
            ps.setInt(7, ebook.getUploaderId());
            ps.setDate(8, Date.valueOf(ebook.getCreatedAt().atStartOfDay().toLocalDate()));
            ps.setInt(9, ebook.getViewCount());
            ps.setString(10, ebook.getCoverUrl());
            ps.executeUpdate();
        }
    }

    @Override
    public Ebook select(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Ebook(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("release_type"),
                    rs.getString("language"),
                    rs.getString("status"),
                    rs.getString("visibility"),
                    rs.getInt("uploader_id"),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getInt("view_count"),
                    rs.getString("cover_url")
                );
            }
        }
        return null;
    }

    @Override
    public List<Ebook> selectAll() throws SQLException {
        List<Ebook> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Ebook(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("release_type"),
                    rs.getString("language"),
                    rs.getString("status"),
                    rs.getString("visibility"),
                    rs.getInt("uploader_id"),
                    rs.getDate("created_at").toLocalDate(),
                    rs.getInt("view_count"),
                    rs.getString("cover_url")
                ));
            }
        }
        return list;
    }

    @Override
    public boolean update(Ebook ebook) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, ebook.getTitle());
            ps.setString(2, ebook.getDescription());
            ps.setString(3, ebook.getReleaseType());
            ps.setString(4, ebook.getLanguage());
            ps.setString(5, ebook.getStatus());
            ps.setString(6, ebook.getVisibility());
            ps.setInt(7, ebook.getUploaderId());
            ps.setTimestamp(8, Timestamp.valueOf(ebook.getCreatedAt().atStartOfDay()));
            ps.setInt(9, ebook.getViewCount());
            ps.setString(10, ebook.getCoverUrl());
            ps.setInt(11, ebook.getId());
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
        EbookDAO dao = new EbookDAO();
        try {
            Ebook ebook = new Ebook(1, "Sách mẫu", "Mô tả sách", "chapter", "Tiếng Việt", "ongoing", "public", 1, LocalDate.now(), 0, "cover.jpg");
            dao.insert(ebook);

            List<Ebook> all = dao.selectAll();
            Ebook selected = all.getFirst();
            all.forEach(System.out::println);

            if (selected != null) {
                selected.setTitle("Sách sửa");
                dao.update(selected);
                dao.delete(selected.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
