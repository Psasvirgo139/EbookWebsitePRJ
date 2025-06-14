package service;

import java.util.List;
import model.Ebook;

public interface EbookService {
    boolean createEbook(Ebook ebook);
    List<Ebook> getAllEbooks();

    // Thêm dòng này:
    List<Ebook> getEbooksByStatus(String status);

    // Nếu có update status thì thêm luôn:
    boolean updateEbookStatus(int ebookId, String status);
}
