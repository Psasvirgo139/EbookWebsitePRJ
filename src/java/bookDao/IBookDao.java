package bookDao;

import model.Book;
import java.util.List;

public interface IBookDao {
    // Lấy tất cả sách
    List<Book> findAll();

    // Tìm sách theo ID
    Book findById(int id);

    // Thêm mới sách (có trường summary)
    boolean insert(Book book);

    // Cập nhật thông tin sách (bao gồm summary)
    boolean update(Book book);

    // Xóa sách theo ID
    boolean delete(int id);

    // Tìm kiếm sách theo keyword
    List<Book> search(String keyword);
}
