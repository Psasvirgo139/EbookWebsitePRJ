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
        <title>Sửa tóm tắt sách</title>
    </head>
    <body>
        <div class="container">
            <h2><span style="color:#2772fa;">🤖</span> Sửa tóm tắt nội dung (AI)</h2>
            <form id="editSummaryForm"
                  action="${pageContext.request.contextPath}/book?action=editSummary"
                  method="post"
                  class="form-book">
                <input type="hidden" name="id" value="${book.id}" />

                <div class="form-row">
                    <label for="summary"><b>Tóm tắt nội dung:</b></label>
                    <textarea name="summary" id="summary" rows="8" required>${book.summary}</textarea>
                </div>

                <button type="submit" class="btn btn-add">Lưu tóm tắt</button>
                <c:if test="${not empty filterError}">
                    <div class="alert-error">${filterError}</div>
                </c:if>
            </form>
            <div class="action-links" style="margin-top:22px;">
                <a href="${pageContext.request.contextPath}/book?action=view&id=${book.id}" class="btn">Quay lại chi tiết sách</a>
            </div>
        </div>
    </body>
</html>
