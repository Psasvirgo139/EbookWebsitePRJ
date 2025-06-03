package bookDao;

import dao.DBConnection;
import model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements IBookDao {

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book b = mapResultSetToBook(rs);
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book findById(int id) {
        String sql = "SELECT * FROM Book WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Book book) {
        String sql = "INSERT INTO Book(title, author, genre, description, coverUrl, contentUrl, uploaderId, status, summary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getCoverUrl());
            ps.setString(6, book.getContentUrl());
            ps.setInt(7, book.getUploaderId());
            ps.setString(8, book.getStatus());
            ps.setString(9, book.getSummary());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                System.err.println("Không insert được bản ghi Book!");
            }
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE Book SET title=?, author=?, genre=?, description=?, coverUrl=?, contentUrl=?, uploaderId=?, status=?, summary=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getDescription());
            ps.setString(5, book.getCoverUrl());
            ps.setString(6, book.getContentUrl());
            ps.setInt(7, book.getUploaderId());
            ps.setString(8, book.getStatus());
            ps.setString(9, book.getSummary());
            ps.setInt(10, book.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Book WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Book> search(String keyword) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String q = "%" + keyword + "%";
            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = mapResultSetToBook(rs);
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm mapping để tránh lặp code khi lấy Book từ ResultSet
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setId(rs.getInt("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setGenre(rs.getString("genre"));
        b.setDescription(rs.getString("description"));
        b.setCoverUrl(rs.getString("coverUrl"));
        b.setContentUrl(rs.getString("contentUrl"));
        b.setUploaderId(rs.getInt("uploaderId"));
        b.setStatus(rs.getString("status"));
        b.setCreatedAt(rs.getString("createdAt"));
        b.setUpdatedAt(rs.getString("updatedAt"));
        b.setSummary(rs.getString("summary")); // mapping trường summary!
        return b;
    }
}
