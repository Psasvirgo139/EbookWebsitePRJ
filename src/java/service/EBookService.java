package service;

import model.Ebook;
import java.sql.SQLException;
import java.util.List;

public interface EBookService {
    List<Ebook> getAll(int page, int size) throws SQLException;
    Ebook getById(int id) throws SQLException;
    void create(Ebook ebook) throws SQLException;
    void update(Ebook ebook) throws SQLException;
    void delete(int id) throws SQLException;
}
