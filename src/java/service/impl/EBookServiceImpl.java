package service.impl;

import DAO.ebook.EBookDAO;
import DAO.ebook.IEBookDAO;
import model.Ebook;

import java.sql.SQLException;
import java.util.List;
import service.EBookService;

public class EBookServiceImpl implements EBookService {
    private final IEBookDAO dao = new EBookDAO();

    @Override
    public List<Ebook> getAll(int page, int size) throws SQLException {
        return dao.getAll(page, size);
    }

    @Override
    public Ebook getById(int id) throws SQLException {
        return dao.getById(id);
    }

    @Override
    public void create(Ebook ebook) throws SQLException {
        dao.insert(ebook);
    }

    @Override
    public void update(Ebook ebook) throws SQLException {
        dao.update(ebook);
    }

    @Override
    public void delete(int id) throws SQLException {
        dao.delete(id);
    }
}
