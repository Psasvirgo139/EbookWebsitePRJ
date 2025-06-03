<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Dùng trong mọi file JSP -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/app.js" defer></script>
<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">

<html>
    <head>
        <title>Đăng nhập hệ thống</title>
    </head>
    <body>
        <h2>Đăng nhập</h2>
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    <form action="users?action=login" method="post">
        <label>Tên đăng nhập:</label>
        <input type="text" name="username" required /><br/>
        <label>Mật khẩu:</label>
        <input type="password" name="password" required /><br/>
        <button type="submit">Đăng nhập</button>
    </form>
    <p>Chưa có tài khoản? <a href="users?action=register">Đăng ký</a></p>
</body>
</html>
