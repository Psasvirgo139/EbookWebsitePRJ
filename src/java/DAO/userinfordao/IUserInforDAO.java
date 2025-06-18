/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO.userinfordao;

import java.util.List;
import model.UserInfor;
import java.sql.SQLException;
/**
 *
 * @author ADMIN
 */
public interface IUserInforDAO {
    void insert(UserInfor ui) throws SQLException;
    UserInfor select(int id) throws SQLException;
    List<UserInfor> selectAll() throws SQLException;
    boolean update(UserInfor ui) throws SQLException;
    boolean delete(int id) throws SQLException;
}
