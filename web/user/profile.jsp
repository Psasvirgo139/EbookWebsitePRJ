<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.User" %>
<!-- Dùng trong mọi file JSP -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/app.js" defer></script>
<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">

<%
    User user = (User) request.getAttribute("user");
%>
<html>
    <head>
        <title>Thông tin cá nhân</title>
    </head>
    <body>
        <h2>Thông tin cá nhân</h2>
    <c:if test="${not empty user}">
        <form action="users?action=updateProfile" method="post">
            <label>Họ tên:</label>
            <input type="text" name="fullName" value="${user.fullName}" required /><br/>
            <label>Email:</label>
            <input type="email" name="email" value="${user.email}" required /><br/>
            <label>Tên đăng nhập:</label>
            <input type="text" value="${user.username}" readonly /><br/>
            <label>Vai trò:</label>
            <input type="text" value="${user.role}" readonly /><br/>
            <button type="submit">Cập nhật</button>
        </form>
    </c:if>
    <a href="users?action=logout">Đăng xuất</a>
</body>
</html>
