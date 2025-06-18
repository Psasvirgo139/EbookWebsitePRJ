package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import service.UserService;
import service.impl.UserServiceImpl;
// Thêm các Service khác nếu cần
import service.EbookService;
import service.impl.EbookServiceImpl;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/admin/dashboard"})
public class DashboardServlet extends HttpServlet {

    private UserService userService;
    private EbookService ebookService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        ebookService = new EbookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Kiểm tra quyền admin
        model.User user = (model.User) session.getAttribute("user");
        if (!"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }

        // Lấy các số liệu thống kê
        int userCount = userService.selectAllUsers().size();
        int ebookCount = ebookService.getAllEbooks().size();

        // Bạn có thể thêm các thống kê khác ở đây (vd: số sách đang chờ duyệt,...)

        // Gửi data qua JSP
        request.setAttribute("userCount", userCount);
        request.setAttribute("ebookCount", ebookCount);
        // ... các thuộc tính khác

        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
