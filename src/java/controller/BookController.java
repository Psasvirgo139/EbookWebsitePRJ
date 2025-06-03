package controller;

import model.Book;
import model.User;
import service.BookServiceImpl;
import service.IBookService;
import service.IContentFilterService;
import service.OpenAIContentFilterService;
import util.Utils;
import filter.FilterResult;
import service.IContentSummaryService;
import service.OpenAIContentSummaryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "BookController", urlPatterns = {"/book"})
@MultipartConfig
public class BookController extends HttpServlet {

    private IBookService bookService;
    private IContentFilterService contentFilterService;

    @Override
    public void init() {
        bookService = new BookServiceImpl();
        contentFilterService = new OpenAIContentFilterService();  // Hoặc ContentFilterFactory.getContentFilterService()
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    List<Book> bookList = bookService.getAllBooks();
                    request.setAttribute("bookList", bookList);
                    request.getRequestDispatcher("/book/bookList.jsp").forward(request, response);
                    break;
                case "upload":
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    break;
                case "edit":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Book editBook = bookService.getBookById(id);
                    request.setAttribute("book", editBook);
                    request.getRequestDispatcher("/book/bookEdit.jsp").forward(request, response);
                    break;
                case "view":
                    int viewId = Integer.parseInt(request.getParameter("id"));
                    Book viewBook = bookService.getBookById(viewId);
                    request.setAttribute("book", viewBook);
                    request.getRequestDispatcher("/book/bookDetail.jsp").forward(request, response);
                    break;
                case "editSummary":
                    // Chỉ cho admin sửa tóm tắt
                    HttpSession session = request.getSession();
                    User currentUser = (User) session.getAttribute("user");
                    if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getRole())) {
                        response.sendRedirect("book?action=view&id=" + request.getParameter("id"));
                        return;
                    }
                    int editSummaryId = Integer.parseInt(request.getParameter("id"));
                    Book bookToEditSummary = bookService.getBookById(editSummaryId);
                    request.setAttribute("book", bookToEditSummary);
                    request.getRequestDispatcher("/editSummary.jsp").forward(request, response);
                    break;
                case "delete":
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    bookService.removeBook(deleteId);
                    response.sendRedirect("book?action=list");
                    break;
                default:
                    response.sendRedirect("book?action=list");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            String uploadsDir = getServletContext().getRealPath("/") + "uploads";
            File uploadsFolder = new File(uploadsDir);
            if (!uploadsFolder.exists()) {
                uploadsFolder.mkdirs();
            }

            if ("upload".equals(action)) {
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String genre = request.getParameter("genre");
                String description = request.getParameter("description");

                Part coverPart = request.getPart("cover");
                String coverUrl = null;
                if (coverPart != null && coverPart.getSize() > 0) {
                    String coverFileName = coverPart.getSubmittedFileName();
                    coverUrl = "uploads/" + coverFileName;
                    coverPart.write(uploadsDir + File.separator + coverFileName);
                }

                Part contentPart = request.getPart("contentFile");
                String contentUrl = null;
                String contentFileName = contentPart.getSubmittedFileName();
                if (contentFileName == null || contentFileName.isEmpty()) {
                    request.setAttribute("filterError", "Bạn chưa chọn file nội dung sách!");
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    return;
                }

                String ext = "";
                if (contentFileName.contains(".")) {
                    ext = contentFileName.substring(contentFileName.lastIndexOf('.') + 1).toLowerCase();
                }
                List<String> allowedExts = Arrays.asList("txt", "doc", "docx", "pdf");
                if (!allowedExts.contains(ext)) {
                    request.setAttribute("filterError", "Chỉ chấp nhận file .txt, .doc, .docx, .pdf cho nội dung sách!");
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    return;
                }

                String contentFilePath = uploadsDir + File.separator + contentFileName;
                contentPart.write(contentFilePath);

                String bookText;
                try {
                    bookText = Utils.readAnyTextFile(contentFilePath, ext);
                    System.out.println("[DEBUG][BookController] Nội dung file đọc được gửi đi kiểm duyệt:\n" + bookText);
                } catch (Exception ex) {
                    new File(contentFilePath).delete();
                    request.setAttribute("filterError", "Không đọc được nội dung file! (" + ex.getMessage() + ")");
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    return;
                }

                FilterResult filter = contentFilterService.check(bookText);
                if (!filter.isPassed()) {
                    new File(contentFilePath).delete();
                    request.setAttribute("filterError", filter.getMessage());
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    return;
                }

                contentUrl = "uploads/" + contentFileName;
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                int uploaderId = user != null ? user.getId() : 1;

                // ---------- TÍCH HỢP AI CONTENT SUMMARY + KIỂM DUYỆT SUMMARY ----------
                String summary = "";
                try {
                    IContentSummaryService summaryService = new OpenAIContentSummaryService();
                    summary = summaryService.summarize(bookText);

                    // Kiểm duyệt lại phần summary do AI sinh ra
                    FilterResult summaryFilter = contentFilterService.check(summary);
                    System.out.println("[DEBUG][BookController] Summary AI: " + summary);

                    if (!summaryFilter.isPassed()) {
                        // Nếu summary bị flag, hiển thị lỗi cho admin và không lưu sách
                        request.setAttribute("filterError", "AI tóm tắt chưa hợp lệ: " + summaryFilter.getMessage()
                                + "<br>Bạn hãy kiểm tra lại nội dung sách hoặc liên hệ admin để chỉnh sửa tóm tắt.");
                        request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                        // Xóa file vừa upload cho sạch
                        if (coverUrl != null) {
                            new File(uploadsDir + File.separator + coverUrl.substring(8)).delete();
                        }
                        if (contentUrl != null) {
                            new File(uploadsDir + File.separator + contentUrl.substring(8)).delete();
                        }
                        return;
                    }
                } catch (Exception e) {
                    summary = "Không thể sinh tóm tắt tự động.";
                    e.printStackTrace();
                }

                // --- Tạo Book, set summary (nếu constructor chưa có thì dùng setSummary như dưới)
                Book book = new Book(0, title, author, genre, description, coverUrl, contentUrl, uploaderId, "pending", null, null, summary);

                book.setSummary(summary);

                boolean added = bookService.addBook(book);
                if (!added) {
                    if (coverUrl != null) {
                        new File(uploadsDir + File.separator + coverUrl.substring(8)).delete();
                    }
                    if (contentUrl != null) {
                        new File(uploadsDir + File.separator + contentUrl.substring(8)).delete();
                    }
                    request.setAttribute("filterError", "Không thể lưu sách vào database!");
                    request.getRequestDispatcher("/book/bookUpload.jsp").forward(request, response);
                    return;
                }
                response.sendRedirect("book?action=list");
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String genre = request.getParameter("genre");
                String description = request.getParameter("description");

                Book book = bookService.getBookById(id);
                book.setTitle(title);
                book.setAuthor(author);
                book.setGenre(genre);
                book.setDescription(description);

                Part coverPart = request.getPart("cover");
                if (coverPart != null && coverPart.getSize() > 0) {
                    String coverFileName = coverPart.getSubmittedFileName();
                    String coverUrl = "uploads/" + coverFileName;
                    coverPart.write(uploadsDir + File.separator + coverFileName);
                    book.setCoverUrl(coverUrl);
                }

                Part contentPart = request.getPart("contentFile");
                if (contentPart != null && contentPart.getSize() > 0) {
                    String contentFileName = contentPart.getSubmittedFileName();
                    String ext = "";
                    if (contentFileName.contains(".")) {
                        ext = contentFileName.substring(contentFileName.lastIndexOf('.') + 1).toLowerCase();
                    }
                    if (!Arrays.asList("txt", "doc", "docx", "pdf").contains(ext)) {
                        request.setAttribute("filterError", "Chỉ chấp nhận file .txt, .doc, .docx, .pdf cho nội dung sách!");
                        request.setAttribute("book", book);
                        request.getRequestDispatcher("/book/bookEdit.jsp").forward(request, response);
                        return;
                    }

                    String contentFilePath = uploadsDir + File.separator + contentFileName;
                    contentPart.write(contentFilePath);

                    String bookText;
                    try {
                        bookText = Utils.readAnyTextFile(contentFilePath, ext);
                        System.out.println("[DEBUG][BookController] Nội dung file sửa đọc được gửi đi kiểm duyệt:\n" + bookText);
                    } catch (Exception ex) {
                        new File(contentFilePath).delete();
                        request.setAttribute("filterError", "Không đọc được nội dung file! (" + ex.getMessage() + ")");
                        request.setAttribute("book", book);
                        request.getRequestDispatcher("/book/bookEdit.jsp").forward(request, response);
                        return;
                    }

                    FilterResult filter = contentFilterService.check(bookText);
                    if (!filter.isPassed()) {
                        new File(contentFilePath).delete();
                        request.setAttribute("filterError", filter.getMessage());
                        request.setAttribute("book", book);
                        request.getRequestDispatcher("/book/bookEdit.jsp").forward(request, response);
                        return;
                    }

                    book.setContentUrl("uploads/" + contentFileName);
                }

                boolean updated = bookService.modifyBook(book);
                if (!updated) {
                    request.setAttribute("filterError", "Không thể cập nhật sách vào database!");
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("/book/bookEdit.jsp").forward(request, response);
                    return;
                }

                response.sendRedirect("book?action=list");
            } else if ("editSummary".equals(action)) {
                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getRole())) {
                    response.sendRedirect("book?action=view&id=" + request.getParameter("id"));
                    return;
                }
                int id;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (Exception ex) {
                    request.setAttribute("filterError", "ID sách không hợp lệ!");
                    request.getRequestDispatcher("/editSummary.jsp").forward(request, response);
                    return;
                }
                String summary = request.getParameter("summary");
                FilterResult summaryFilter = contentFilterService.check(summary);
                if (!summaryFilter.isPassed()) {
                    Book book = bookService.getBookById(id);
                    book.setSummary(summary);
                    request.setAttribute("book", book);
                    request.setAttribute("filterError", "Tóm tắt không hợp lệ: " + summaryFilter.getMessage());
                    request.getRequestDispatcher("/editSummary.jsp").forward(request, response);
                    return;
                }
                Book book = bookService.getBookById(id);
                book.setSummary(summary);
                bookService.modifyBook(book);
                response.sendRedirect("book?action=view&id=" + id);
                return;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
