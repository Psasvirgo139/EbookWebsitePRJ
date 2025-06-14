/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.UserInfor;
import service.UserService;
import service.impl.UserServiceImpl;

/**
 *
 * @author Admin
 */
@WebServlet(name="ProfileServlet", urlPatterns={"/profile"})
public class ProfileServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user == null) {
            // Chưa login thì về lại trang login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy chi tiết UserInfor nếu có
        UserInfor userInfor = null;
        if (user.getUserinforId() != null) {
            userInfor = userService.getUserInforById(user.getUserinforId());
        }

        request.setAttribute("user", user);
        request.setAttribute("userInfor", userInfor);
        request.getRequestDispatcher("user/profile.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị trang cá nhân người dùng";
    }
}
