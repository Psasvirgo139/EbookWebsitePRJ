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
<!DOCTYPE html>
<html>
    <head>
        <title>Sửa sách</title>
    </head>
    <body>
        <div class="container">
            <h2><span style="color:#2359d4;">✏️</span> Sửa sách</h2>
            <form id="editForm"
                  action="${pageContext.request.contextPath}/book?action=edit"
                  method="post"
                  enctype="multipart/form-data"
                  class="form-book">
                <input type="hidden" name="id" value="${book.id}" />

                <div class="form-row">
                    <label>Tiêu đề:</label>
                    <input type="text" name="title" value="${book.title}" required />
                </div>
                <div class="form-row">
                    <label>Tác giả:</label>
                    <input type="text" name="author" value="${book.author}" required />
                </div>
                <div class="form-row">
                    <label>Thể loại:</label>
                    <input type="text" name="genre" value="${book.genre}" />
                </div>
                <div class="form-row">
                    <label>Mô tả:</label>
                    <textarea name="description" rows="5" required>${book.description}</textarea>
                </div>
                <div class="form-row">
                    <label>Ảnh bìa mới:</label>
                    <input type="file" name="cover" accept="image/*" />
                    <c:if test="${not empty book.coverUrl}">
                        <img src="${pageContext.request.contextPath}/${book.coverUrl}" class="book-edit-cover" style="max-width:90px;margin-top:8px;">
                    </c:if>
                </div>
                <div class="form-row">
                    <label>File nội dung mới:</label>
                    <input type="file" name="contentFile" />
                    <c:if test="${not empty book.contentUrl}">
                        <a href="${pageContext.request.contextPath}/${book.contentUrl}" target="_blank" style="margin-left:12px;font-size:0.95em;color:#2563eb;">Xem file hiện tại</a>
                    </c:if>
                </div>

                <button type="submit" class="btn btn-add">Lưu sửa</button>
                <c:if test="${not empty filterError}">
                    <div class="alert-error">${filterError}</div>
                </c:if>
            </form>
            <div class="action-links" style="margin-top:22px;">
                <a href="${pageContext.request.contextPath}/book?action=list" class="btn">Quay lại danh sách</a>
            </div>
        </div>
    </body>
</html>
