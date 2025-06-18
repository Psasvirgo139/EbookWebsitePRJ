package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.Ebook;
import service.EbookService;
import service.impl.EbookServiceImpl;

@WebServlet(name = "CheckUploadServlet", urlPatterns = {"/admin/checkupload"})
public class CheckUploadServlet extends HttpServlet {
    private EbookService ebookService;

    @Override
    public void init() throws ServletException {
        ebookService = new EbookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Kiểm tra đăng nhập & quyền admin
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy danh sách sách đang chờ duyệt
        List<Ebook> pendingBooks = ebookService.getEbooksByStatus("pending");
        request.setAttribute("pendingBooks", pendingBooks);

        request.getRequestDispatcher("/admin/checkupload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Kiểm tra quyền admin như trên (bảo mật)
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin duyệt từ form
        String action = request.getParameter("action"); // "approve" hoặc "reject"
        String ebookIdStr = request.getParameter("ebookId");
        if (ebookIdStr != null && action != null) {
            int ebookId = Integer.parseInt(ebookIdStr);
            if ("approve".equals(action)) {
                ebookService.updateEbookStatus(ebookId, "approved");
            } else if ("reject".equals(action)) {
                ebookService.updateEbookStatus(ebookId, "rejected");
            }
        }
        // Quay lại trang duyệt
        response.sendRedirect(request.getContextPath() + "/admin/checkupload");
    }
}
