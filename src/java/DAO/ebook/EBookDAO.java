package DAO.ebook;

import DAO.DBConnection;
import model.Ebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EBookDAO implements IEBookDAO {

    @Override
    public boolean createEbook(Ebook ebook) {
        String sql = "INSERT INTO Ebooks (title, description, release_type, language, uploader_id, status, visibility, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ebook.getTitle());
            ps.setString(2, ebook.getDescription());
            ps.setString(3, ebook.getReleaseType());
            ps.setString(4, ebook.getLanguage());
            ps.setInt(5, ebook.getUploaderId());
            ps.setString(6, ebook.getStatus());
            ps.setString(7, ebook.getVisibility());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ebook> getAllEbooks() {
        List<Ebook> list = new ArrayList<>();
        String sql = "SELECT * FROM Ebooks";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ebook ebook = new Ebook();
                ebook.setId(rs.getInt("id"));
                ebook.setTitle(rs.getString("title"));
                ebook.setDescription(rs.getString("description"));
                ebook.setReleaseType(rs.getString("release_type"));
                ebook.setLanguage(rs.getString("language"));
                ebook.setUploaderId(rs.getInt("uploader_id"));
                ebook.setStatus(rs.getString("status"));
                ebook.setVisibility(rs.getString("visibility"));
                ebook.setCreatedAt(rs.getDate("created_at").toLocalDate());

                // Có thể set thêm các field khác nếu cần
                list.add(ebook);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
public List<Ebook> getEbooksByStatus(String status) {
    List<Ebook> list = new ArrayList<>();
    String sql = "SELECT * FROM Ebooks WHERE status = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Ebook ebook = new Ebook();
            ebook.setId(rs.getInt("id"));
            ebook.setTitle(rs.getString("title"));
            ebook.setDescription(rs.getString("description"));
            ebook.setReleaseType(rs.getString("release_type"));
            ebook.setLanguage(rs.getString("language"));
            ebook.setUploaderId(rs.getInt("uploader_id"));
            ebook.setStatus(rs.getString("status"));
            ebook.setVisibility(rs.getString("visibility"));
            ebook.setCreatedAt(rs.getDate("created_at").toLocalDate());
            list.add(ebook);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

@Override
public boolean updateEbookStatus(int ebookId, String status) {
    String sql = "UPDATE Ebooks SET status = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, ebookId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
