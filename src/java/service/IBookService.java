package service;

import model.Book;
import java.sql.SQLException;
import java.util.List;

public interface IBookService {
    boolean addBook(Book book) throws SQLException;
    Book getBookById(int id) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    boolean modifyBook(Book book) throws SQLException;
    boolean removeBook(int id) throws SQLException;
}
