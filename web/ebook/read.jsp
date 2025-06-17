<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Chapter"%>

<%
    Chapter chapter = (Chapter) request.getAttribute("chapter");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đọc chương</title>
</head>
<body>
<% if (chapter != null) { %>
    <h1>📘 Chương <%= chapter.getNumber() %>: <%= chapter.getTitle() %></h1>
    <p><strong>Truy cập:</strong> <%= chapter.getAccessLevel() %></p>
    <p><strong>Lượt xem:</strong> <%= chapter.getViewCount() %> |
       <strong>Thích:</strong> <%= chapter.getLikeCount() %></p>
    <hr>
    <p>
        <em>Nội dung chương:</em><br>
        <a href="<%= request.getContextPath() + "/" + chapter.getContentUrl() %>">Xem nội dung</a>
    </p>
<% } else { %>
    <p>Không tìm thấy chương.</p>
<% } %>

<a href="javascript:history.back()">⬅️ Quay lại</a>
</body>
</html>
