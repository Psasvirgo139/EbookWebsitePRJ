package controller;

import model.Ebook;
import service.impl.EBookServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.http.*;
import service.EBookService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final EBookService ebookService = new EBookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("HomeServlet đang chạy");
        int page = 1;
        int size = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.matches("\\d+")) {
            page = Integer.parseInt(pageParam);
        }

        try {
            List<Ebook> ebooks = ebookService.getAll(page, size);
            request.setAttribute("ebooks", ebooks);
            request.setAttribute("page", page);

            // TODO: tổng số trang nếu cần phân trang
            request.getRequestDispatcher("/ebook/list.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot load ebooks", e);
        }
    }
}
