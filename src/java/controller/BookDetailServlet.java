package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Ebook;
import model.Chapter;
import service.EBookService;
import service.impl.EBookServiceImpl;
import DAO.chapter.ChapterDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/book/detail")
public class BookDetailServlet extends HttpServlet {
    private final EBookService ebookService = new EBookServiceImpl();
    private final ChapterDAO chapterDAO = new ChapterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idRaw = request.getParameter("id");
        if (idRaw == null || !idRaw.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int ebookId = Integer.parseInt(idRaw);
        try {
            Ebook ebook = ebookService.getById(ebookId);
            List<Chapter> chapters = chapterDAO.getChaptersByEbookId(ebookId);

            request.setAttribute("ebook", ebook);
            request.setAttribute("chapters", chapters);
            request.getRequestDispatcher("/ebook/detail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi khi lấy chi tiết sách", e);
        }
    }
}
