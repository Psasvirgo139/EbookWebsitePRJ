<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/app.js" defer></script>

<div class="logo-bar">
    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
</div>

<%
    Book book = (Book) request.getAttribute("book");
%>
<html>
<head>
    <title>Chi tiết sách</title>
</head>
<body>
<div class="container">
    <h2>📖 Chi tiết sách</h2>
    <c:if test="${not empty book}">
        <!-- Box Tóm tắt nội dung AI (thêm ngay sau tiêu đề, trước mô tả sách) -->
        <c:if test="${not empty book.summary}">
            <div class="ai-summary-box">
                <i class="fa fa-robot" style="font-size:1.2em;"></i>
                <b>Tóm tắt nội dung (AI tạo):</b>
                <div class="ai-summary">${book.summary}</div>
                <c:if test="${sessionScope.user != null && sessionScope.user.role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/book?action=editSummary&id=${book.id}" class="btn btn-edit-summary">Sửa tóm tắt</a>
                </c:if>
            </div>
        </c:if>
        <!-- End AI summary box -->

        <div class="book-detail-card">
            <div class="book-cover-wrap">
                <c:if test="${not empty book.coverUrl}">
                    <img src="${pageContext.request.contextPath}/${book.coverUrl}" alt="Cover" class="book-cover-img">
                </c:if>
            </div>
            <div class="book-info">
                <div class="book-title"><b>${book.title}</b></div>
                <div><b>Tác giả:</b> ${book.author}</div>
                <div><b>Thể loại:</b> ${book.genre}</div>
                <div><b>Mô tả:</b> <span class="book-desc">${book.description}</span></div>
                <div>
                    <b>Trạng thái:</b>
                    <span class="status status-${book.status}">${book.status}</span>
                </div>
                <div><b>Ngày upload:</b> ${book.createdAt}</div>
                <c:if test="${not empty book.contentUrl}">
                    <div style="margin:14px 0;">
                        <a href="${pageContext.request.contextPath}/${book.contentUrl}" class="btn btn-download" target="_blank">Tải file nội dung</a>
                    </div>
                </c:if>
                <div class="action-links">
                    <a href="${pageContext.request.contextPath}/book?action=list" class="btn">Quay lại danh sách</a>
                    <a href="${pageContext.request.contextPath}/book?action=edit&id=${book.id}" class="btn btn-add">Sửa sách này</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
