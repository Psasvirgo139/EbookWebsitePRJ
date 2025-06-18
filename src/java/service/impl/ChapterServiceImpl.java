package service.impl;

import DAO.chapter.ChapterDAO;
import DAO.chapter.IChapterDAO;
import model.Chapter;
import service.ChapterService;

import java.sql.SQLException;
import java.util.List;

public class ChapterServiceImpl implements ChapterService {
    private final IChapterDAO chapterDAO = new ChapterDAO();

    @Override
    public Chapter getById(int id) throws SQLException {
        return chapterDAO.getById(id);
    }

    @Override
    public List<Chapter> getChaptersByEbookId(int ebookId) throws SQLException {
        return chapterDAO.getChaptersByEbookId(ebookId);
    }
}
