<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Đăng nhập | EBook Website</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f4f7fa;
                margin: 0;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .login-container {
                background: #fff;
                padding: 30px 28px 24px 28px;
                border-radius: 14px;
                box-shadow: 0 6px 32px #0002;
                min-width: 320px;
            }
            .login-container h2 {
                margin: 0 0 18px 0;
                color: #3257a8;
                text-align: center;
            }
            .form-group {
                margin-bottom: 16px;
            }
            label {
                display: block;
                margin-bottom: 7px;
                font-weight: 600;
            }
            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 10px;
                border-radius: 6px;
                border: 1px solid #b9c5dd;
                background: #f6f8fb;
                font-size: 1rem;
            }
            input[type="text"]:focus, input[type="password"]:focus {
                border: 1.5px solid #6389f5;
                outline: none;
            }
            .btn-login {
                width: 100%;
                padding: 11px 0;
                background: #6389f5;
                color: #fff;
                border: none;
                border-radius: 6px;
                font-size: 1.09rem;
                font-weight: 600;
                cursor: pointer;
                margin-top: 4px;
                transition: background 0.2s;
            }
            .btn-login:hover {
                background: #3257a8;
            }
            .error-message {
                color: #d83232;
                background: #ffeaea;
                border: 1px solid #eebcbc;
                border-radius: 5px;
                padding: 10px;
                margin-bottom: 16px;
                text-align: center;
                font-size: 1rem;
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Đăng nhập hệ thống</h2>
            <form action="${pageContext.request.contextPath}/login" method="post" autocomplete="off">
                <div class="form-group">
                    <label for="username">Tên đăng nhập hoặc Email</label>

                    <input type="text" name="usernameOrEmail" id="usernameOrEmail" required
                           value="<c:out value='${param.usernameOrEmail != null ? param.usernameOrEmail : (username != null ? username : "")}'/>"


                           </div>
                    <div class="form-group">
                        <label for="password">Mật khẩu</label>
                        <input type="password" name="password" id="password" required />
                    </div>
                    <c:if test="${not empty error}">
                        <div class="error-message">${error}</div>
                    </c:if>
                    <button type="submit" class="btn-login">Đăng nhập</button>
            </form>
        </div>
    </body>
</html>
