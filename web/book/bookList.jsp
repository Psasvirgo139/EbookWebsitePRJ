<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/app.js" defer></script>
<div class="logo-bar">
    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
</div>

<%
    List<Book> bookList = (List<Book>) request.getAttribute("bookList");
%>
<html>
<head>
    <title>Danh sách sách</title>
</head>
<body>
<div class="container">
    <h2>📚 Danh sách sách</h2>
    <a href="${pageContext.request.contextPath}/book?action=upload" class="btn btn-add">➕ Thêm sách mới</a>

    <form method="get" action="${pageContext.request.contextPath}/book" class="search-form">
        <input type="text" name="q" placeholder="Tìm kiếm..." value="${param.q}" />
        <button type="submit" class="btn-search">Tìm</button>
    </form>

    <c:if test="${not empty errorMsg}">
        <div class="alert-error">${errorMsg}</div>
    </c:if>

    <div class="table-wrap">
    <table class="book-table">
        <tr>
            <th>STT</th><th>Tên sách</th><th>Tác giả</th><th>Thể loại</th>
            <th>Trạng thái</th><th>Ngày upload</th><th>Nội dung</th><th>Hành động</th>
        </tr>
        <c:forEach var="b" items="${bookList}" varStatus="s">
            <tr>
                <td>${s.index + 1}</td>
                <td>${b.title}</td>
                <td>${b.author}</td>
                <td>${b.genre}</td>
                <td>
                    <span class="status status-${b.status}">
                        ${b.status}
                    </span>
                </td>
                <td>${b.createdAt}</td>
                <td>
                    <c:if test="${not empty b.contentUrl}">
                        <a href="${pageContext.request.contextPath}/${b.contentUrl}" class="btn btn-download" target="_blank">Tải</a>
                    </c:if>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/book?action=view&id=${b.id}" class="action">Xem</a>
                    <a href="${pageContext.request.contextPath}/book?action=edit&id=${b.id}" class="action">Sửa</a>
                    <a href="${pageContext.request.contextPath}/book?action=delete&id=${b.id}" class="action action-danger" onclick="return confirm('Xóa sách này?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    </div>
    <!-- Thêm phân trang ở đây nếu cần -->
</div>
</body>
</html>
