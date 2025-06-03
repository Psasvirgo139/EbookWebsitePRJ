package controller;

import model.User;
import service.IUserService;
import service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {

    private IUserService userService;

    @Override
    public void init() {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }

        switch (action) {
            case "register":
                request.getRequestDispatcher("/user/register.jsp").forward(request, response);
                break;
            case "login":
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
                break;
            case "profile":
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
                } else {
                    response.sendRedirect("users?action=login");
                }
                break;
            case "logout":
                request.getSession().invalidate();
                response.sendRedirect("users?action=login");
                break;
            default:
                response.sendRedirect("users?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("register".equals(action)) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            User newUser = new User(0, username, email, password, fullName, "user", true, "", null, null);
            try {
                userService.addUser(newUser);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            response.sendRedirect("users?action=login");

        } else if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = null;
            try {
                user = userService.checkLogin(username, password);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            if (user != null && user.isStatus()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                response.sendRedirect("book?action=list");
            } else {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
                request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            }

        } else if ("updateProfile".equals(action)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                user.setFullName(fullName);
                user.setEmail(email);
                try {
                    userService.modifyUser(user);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
                session.setAttribute("user", user);
                response.sendRedirect("users?action=profile");
            } else {
                response.sendRedirect("users?action=login");
            }
        }
    }
}
