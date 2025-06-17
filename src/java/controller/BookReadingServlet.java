package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Chapter;
import DAO.chapter.ChapterDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/book/read")
public class BookReadingServlet extends HttpServlet {
    private final ChapterDAO chapterDAO = new ChapterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chapterIdRaw = request.getParameter("chapter_id");
        if (chapterIdRaw == null || !chapterIdRaw.matches("\\d+")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int chapterId = Integer.parseInt(chapterIdRaw);
        try {
            Chapter chapter = chapterDAO.getById(chapterId);
            if (chapter == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            request.setAttribute("chapter", chapter);
            request.getRequestDispatcher("/ebook/read.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Lỗi khi tải chương", e);
        }
    }
}
