package service;

import bookDao.BookDao;
import bookDao.IBookDao;
import model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements IBookService {

    private final IBookDao bookDao;

    public BookServiceImpl() {
        this.bookDao = new BookDao();
    }

    @Override
    public boolean addBook(Book book) throws SQLException {
        return bookDao.insert(book);
    }

    @Override
    public Book getBookById(int id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        return bookDao.findAll();
    }

    @Override
    public boolean modifyBook(Book book) throws SQLException {
        return bookDao.update(book);
    }

    @Override
    public boolean removeBook(int id) throws SQLException {
        return bookDao.delete(id);
    }
    
}
