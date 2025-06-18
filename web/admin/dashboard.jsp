<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin | EBook Website</title>
    <style>
        body {font-family: Arial,sans-serif; background: #f6f7fa;}
        .dashboard-container {background: #fff; max-width: 520px; margin: 40px auto; box-shadow: 0 4px 16px #0002; border-radius: 14px; padding: 36px;}
        h2 {text-align: center; color: #405ed6;}
        .stats {margin: 30px 0;}
        .stat-box {background: #eaf0fb; border-radius: 8px; padding: 22px; margin-bottom: 18px; font-size: 1.2rem;}
        .stat-label {color: #5d87b3;}
        .stat-value {font-size: 2rem; font-weight: bold; color: #415eaf;}
    </style>
</head>
<body>
    <div class="dashboard-container">
        <h2>Dashboard Admin</h2>
        <div class="stats">
            <div class="stat-box">
                <div class="stat-label">Số lượng người dùng</div>
                <div class="stat-value">${userCount}</div>
            </div>
            <div class="stat-box">
                <div class="stat-label">Số lượng sách</div>
                <div class="stat-value">${ebookCount}</div>
            </div>
            <!-- Thêm các thống kê khác tại đây nếu muốn -->
        </div>
    </div>
</body>
</html>
