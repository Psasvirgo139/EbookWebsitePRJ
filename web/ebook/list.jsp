<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Ebook"%>
<%@page import="java.util.List"%>

<%
    List<Ebook> ebooks = (List<Ebook>) request.getAttribute("ebooks");
    Integer pageNum = (Integer) request.getAttribute("page");
    int currentPage = (pageNum != null) ? pageNum : 1;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách Ebook</title>
</head>
<body>
    <h1>📚 Danh sách Ebook (Trang <%= currentPage %>)</h1>

    <% if (ebooks != null && !ebooks.isEmpty()) { %>
        <ul>
            <% for (Ebook book : ebooks) { %>
                <li>
                    <a href="book/detail?id=<%= book.getId() %>">
                        <strong><%= book.getTitle() %></strong>
                    </a>
                </li>
            <% } %>
        </ul>

        <div>
            <% if (currentPage > 1) { %>
                <a href="home?page=<%= currentPage - 1 %>">⬅️ Trước</a>
            <% } %>
            |
            <a href="home?page=<%= currentPage + 1 %>">Tiếp ➡️</a>
        </div>
    <% } else { %>
        <p>Không có ebook nào để hiển thị.</p>
    <% } %>
</body>
</html>
