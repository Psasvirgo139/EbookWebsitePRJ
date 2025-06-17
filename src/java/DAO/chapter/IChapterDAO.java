package DAO.chapter;

import model.Chapter;

import java.sql.SQLException;
import java.util.List;

public interface IChapterDAO {
    Chapter getById(int id) throws SQLException;
    List<Chapter> getChaptersByEbookId(int ebookId) throws SQLException;
}
