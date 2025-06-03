<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Dùng trong mọi file JSP -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
<script src="${pageContext.request.contextPath}/js/app.js" defer></script>
<img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">

<html>
    <head>
        <title>Đăng ký tài khoản mới</title>
    </head>
    <body>
        <h2>Đăng ký tài khoản</h2>
        <form action="users?action=register" method="post">
            <label>Họ tên:</label>
            <input type="text" name="fullName" required /><br/>
            <label>Tên đăng nhập:</label>
            <input type="text" name="username" required /><br/>
            <label>Email:</label>
            <input type="email" name="email" required /><br/>
            <label>Mật khẩu:</label>
            <input type="password" name="password" required /><br/>
            <button type="submit">Đăng ký</button>
        </form>
        <p>Đã có tài khoản? <a href="users?action=login">Đăng nhập</a></p>
    </body>
</html>
