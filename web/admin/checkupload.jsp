<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Duyệt sách mới upload | Admin</title>
    <style>
        body { font-family: Arial,sans-serif; background: #f6f7fa; }
        .container { max-width: 700px; margin: 40px auto; background: #fff; border-radius: 14px; box-shadow: 0 4px 18px #0002; padding: 34px; }
        h2 { color: #315eb7; margin-bottom: 24px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #dde5f1; padding: 10px; text-align: left; }
        th { background: #eaf1fa; }
        td.title { font-weight: bold; }
        .btn-approve { background: #219e4d; color: #fff; border: none; padding: 5px 13px; border-radius: 5px; cursor: pointer; margin-right: 6px;}
        .btn-reject { background: #e24a3b; color: #fff; border: none; padding: 5px 13px; border-radius: 5px; cursor: pointer;}
    </style>
</head>
<body>
    <div class="container">
        <h2>Sách chờ duyệt</h2>
        <c:choose>
            <c:when test="${empty pendingBooks}">
                <div>Không có sách nào đang chờ duyệt.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Tên sách</th>
                        <th>Mô tả</th>
                        <th>Ngôn ngữ</th>
                        <th>Ngày upload</th>
                        <th>Thao tác</th>
                    </tr>
                    <c:forEach var="book" items="${pendingBooks}">
                        <tr>
                            <td class="title">${book.title}</td>
                            <td>${book.description}</td>
                            <td>${book.language}</td>
                            <td>${book.createdAt}</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/checkupload" method="post" style="display:inline;">
                                    <input type="hidden" name="ebookId" value="${book.id}"/>
                                    <button type="submit" name="action" value="approve" class="btn-approve">Duyệt</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/admin/checkupload" method="post" style="display:inline;">
                                    <input type="hidden" name="ebookId" value="${book.id}"/>
                                    <button type="submit" name="action" value="reject" class="btn-reject">Từ chối</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
