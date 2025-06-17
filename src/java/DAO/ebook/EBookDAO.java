package DAO.ebook;

import DAO.DBConnection;
import model.Ebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EBookDAO implements IEBookDAO {

    @Override
    public List<Ebook> getAll(int page, int pageSize) throws SQLException {
        List<Ebook> list = new ArrayList<>();
        String sql = "SELECT * FROM Ebooks ORDER BY created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    @Override
    public Ebook getById(int id) throws SQLException {
        String sql = "SELECT * FROM Ebooks WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        }
        return null;
    }

    @Override
    public void insert(Ebook ebook) throws SQLException {
        String sql = "INSERT INTO Ebooks(title, description, release_type, language, uploader_id, cover_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ebook.getTitle());
            ps.setString(2, ebook.getDescription());
            ps.setString(3, ebook.getReleaseType());
            ps.setString(4, ebook.getLanguage());
            ps.setInt(5, ebook.getUploaderId());
            ps.setString(6, ebook.getCoverUrl());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Ebook ebook) throws SQLException {
        String sql = "UPDATE Ebooks SET title=?, description=?, language=?, status=?, visibility=?, cover_url=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ebook.getTitle());
            ps.setString(2, ebook.getDescription());
            ps.setString(3, ebook.getLanguage());
            ps.setString(4, ebook.getStatus());
            ps.setString(5, ebook.getVisibility());
            ps.setString(6, ebook.getCoverUrl());
            ps.setInt(7, ebook.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Ebooks WHERE id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Ebook map(ResultSet rs) throws SQLException {
        Ebook e = new Ebook();
        e.setId(rs.getInt("id"));
        e.setTitle(rs.getString("title"));
        e.setDescription(rs.getString("description"));
        e.setLanguage(rs.getString("language"));
        e.setStatus(rs.getString("status"));
        e.setReleaseType(rs.getString("release_type"));
        e.setCoverUrl(rs.getString("cover_url"));
        e.setUploaderId(rs.getInt("uploader_id"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            e.setCreatedAt(ts.toLocalDateTime());
        }
        return e;
    }
}
