package DAO.ebook;

import java.util.List;
import model.Ebook;

public interface IEBookDAO {
    boolean createEbook(Ebook ebook);
    List<Ebook> getAllEbooks();
    // Thêm cho chức năng duyệt sách
    List<Ebook> getEbooksByStatus(String status);
    boolean updateEbookStatus(int ebookId, String status);
}
