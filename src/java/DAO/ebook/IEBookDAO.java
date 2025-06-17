package DAO.ebook;

import java.sql.SQLException;
import java.util.List;
import model.Ebook;

public interface IEBookDAO {
    List<Ebook> getAll(int page, int pageSize) throws SQLException;
    Ebook getById(int id) throws SQLException;
    void insert(Ebook ebook) throws SQLException;
    void update(Ebook ebook) throws SQLException;
    void delete(int id) throws SQLException;
}
