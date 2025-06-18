/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.ebook;

/**
 *
 * @author ADMIN
 */
import model.Ebook;
import java.sql.SQLException;
import java.util.List;

public interface IEbookDAO {
    void insert(Ebook ebook) throws SQLException;
    Ebook select(int id) throws SQLException;
    List<Ebook> selectAll() throws SQLException;
    boolean update(Ebook ebook) throws SQLException;
    boolean delete(int id) throws SQLException;
}
