<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Ebook, model.Chapter"%>
<%@page import="java.util.List"%>

<%
    Ebook selectedBook = (Ebook) request.getAttribute("ebook");
    List<Chapter> chapterList = (List<Chapter>) request.getAttribute("chapters");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Ebook</title>
</head>
<body>
<% if (selectedBook != null) { %>
    <h1>📖 <%= selectedBook.getTitle() %></h1>
    <p><strong>Ngôn ngữ:</strong> <%= selectedBook.getLanguage() %></p>
    <p><strong>Mô tả:</strong> <%= selectedBook.getDescription() %></p>

    <h2>📄 Danh sách chương</h2>
    <% if (chapterList != null && !chapterList.isEmpty()) { %>
        <ul>
            <% for (Chapter chap : chapterList) { %>
                <li>
                    <a href="../book/read?chapter_id=<%= chap.getId() %>">
                        Chương <%= chap.getNumber() %>: <%= chap.getTitle() %>
                    </a>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>Chưa có chương nào.</p>
    <% } %>
<% } else { %>
    <p>Sách không tồn tại.</p>
<% } %>

<a href="../home">⬅️ Quay lại danh sách</a>
</body>
</html>
