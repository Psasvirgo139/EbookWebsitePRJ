package controller;

import java.io.IOException;
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

@WebServlet(name = "UploadServlet", urlPatterns = {"/user/upload"})
public class UploadServlet extends HttpServlet {
    private EbookService ebookService;

    @Override
    public void init() throws ServletException {
        ebookService = new EbookServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/user/upload.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin từ form
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String releaseType = request.getParameter("releaseType");
        String language = request.getParameter("language");

        // Gán các giá trị mặc định khác nếu cần
        Ebook ebook = new Ebook();
        ebook.setTitle(title);
        ebook.setDescription(description);
        ebook.setReleaseType(releaseType);
        ebook.setLanguage(language);
        ebook.setUploaderId(user.getId()); // gán id user hiện tại làm uploader
        ebook.setStatus("ongoing");
        ebook.setVisibility("public");

        boolean success = ebookService.createEbook(ebook);

        if (success) {
            request.setAttribute("success", "Đã upload sách mới thành công!");
        } else {
            request.setAttribute("error", "Lỗi khi upload sách. Vui lòng thử lại!");
        }
        request.getRequestDispatcher("/user/upload.jsp").forward(request, response);
    }
}
