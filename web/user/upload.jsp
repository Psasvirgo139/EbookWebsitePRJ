<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Sách Mới</title>
    <style>
        body {font-family: Arial,sans-serif; background: #f7faff;}
        .upload-container {background: #fff; max-width: 420px; margin: 40px auto; box-shadow: 0 4px 16px #0002; border-radius: 12px; padding: 30px;}
        .form-group {margin-bottom: 18px;}
        label {display: block; font-weight: bold; margin-bottom: 7px;}
        input[type="text"], textarea, select {
            width: 100%; padding: 10px; border-radius: 6px; border: 1px solid #b9c5dd; font-size: 1rem;
        }
        .btn-upload {padding: 10px 25px; border: none; background: #4e99e6; color: #fff; font-weight: 600; border-radius: 6px; cursor: pointer;}
        .success {color: #198754; margin-bottom: 12px;}
        .error {color: #d32f2f; margin-bottom: 12px;}
    </style>
</head>
<body>
    <div class="upload-container">
        <h2>Upload Sách Mới</h2>
        <c:if test="${not empty success}">
            <div class="success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/user/upload" method="post">
            <div class="form-group">
                <label for="title">Tên sách</label>
                <input type="text" name="title" id="title" required />
            </div>
            <div class="form-group">
                <label for="description">Mô tả</label>
                <textarea name="description" id="description" rows="3"></textarea>
            </div>
            <div class="form-group">
                <label for="releaseType">Kiểu phát hành</label>
                <select name="releaseType" id="releaseType" required>
                    <option value="novel">Truyện dài</option>
                    <option value="short-story">Truyện ngắn</option>
                </select>
            </div>
            <div class="form-group">
                <label for="language">Ngôn ngữ</label>
                <input type="text" name="language" id="language" required />
            </div>
            <button type="submit" class="btn-upload">Upload</button>
        </form>
    </div>
</body>
</html>
