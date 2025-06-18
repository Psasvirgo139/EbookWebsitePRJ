package service.impl;

import DAO.ebook.EBookDAO;
import model.Ebook;
import service.EbookService;

import java.util.List;

public class EbookServiceImpl implements EbookService {

    private EBookDAO ebookDAO = new EBookDAO();

    @Override
    public boolean createEbook(Ebook ebook) {
        return ebookDAO.createEbook(ebook);
    }

    @Override
    public List<Ebook> getAllEbooks() {
        return ebookDAO.getAllEbooks();
    }

    @Override
public List<Ebook> getEbooksByStatus(String status) {
    return ebookDAO.getEbooksByStatus(status);
}

@Override
public boolean updateEbookStatus(int ebookId, String status) {
    return ebookDAO.updateEbookStatus(ebookId, status);
}

}
