package DAO.chapter;

import model.Chapter;
import DAO.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapterDAO implements IChapterDAO {

    @Override
    public Chapter getById(int id) throws SQLException {
        String sql = "SELECT * FROM Chapters WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        }
        return null;
    }

    @Override
    public List<Chapter> getChaptersByEbookId(int ebookId) throws SQLException {
        List<Chapter> list = new ArrayList<>();
        String sql = "SELECT * FROM Chapters WHERE ebook_id = ? ORDER BY number ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ebookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    private Chapter map(ResultSet rs) throws SQLException {
        Chapter ch = new Chapter();
        ch.setId(rs.getInt("id"));
        ch.setEbookID(rs.getInt("ebook_id"));

        int volId = rs.getInt("volume_id");
        ch.setVolumeID(rs.wasNull() ? null : volId);

        ch.setTitle(rs.getString("title"));
        ch.setNumber(rs.getDouble("number"));
        ch.setContentUrl(rs.getString("content_url"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            ch.setCreatedAt(ts.toLocalDateTime());
        }

        ch.setAccessLevel(rs.getString("access_level"));
        ch.setViewCount(rs.getInt("view_count"));
        ch.setLikeCount(rs.getInt("like_count"));
        return ch;
    }
}
