<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Upload sách mới</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/app.js" defer></script>
    <script>
        function checkModerationAndSubmit(event) {
            event.preventDefault();
            const content = document.getElementById('bookContent').value;
            const statusDiv = document.getElementById('moderationStatus');
            statusDiv.innerHTML = "<span style='color:blue'>Đang kiểm duyệt...</span>";

            fetch('${pageContext.request.contextPath}/moderation', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: 'content=' + encodeURIComponent(content)
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "OK") {
                    statusDiv.innerHTML = "<span style='color:green'>Nội dung hợp lệ! Đang upload...</span>";
                    document.getElementById('uploadForm').onsubmit = null;
                    document.getElementById('uploadForm').submit();
                } else if (data.status === "violate") {
                    statusDiv.innerHTML = "<span style='color:red'>Phát hiện vi phạm: " + data.message + "</span>";
                } else {
                    statusDiv.innerHTML = "<span style='color:orange'>Lỗi kiểm duyệt: " + (data.message || "Không xác định") + "</span>";
                }
            })
            .catch(error => {
                statusDiv.innerHTML = "<span style='color:orange'>Không thể kiểm duyệt: Lỗi mạng hoặc server!</span>";
            });
        }
    </script>
</head>
<body>
<div class="logo-bar">
    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo">
</div>

<div class="container">
    <h2><span style="color:#2dc262;">📚</span> Upload sách mới</h2>

    <%-- ✅ Hiển thị lỗi nếu có từ controller --%>
    <c:if test="${not empty error}">
    <div class="alert-error">${error}</div>
</c:if>


    <form id="uploadForm"
          action="${pageContext.request.contextPath}/book?action=upload"
          method="post"
          enctype="multipart/form-data"
          class="form-book"
          onsubmit="checkModerationAndSubmit(event)">
        <div class="form-row">
            <label>Tiêu đề:</label>
            <input type="text" name="title" required />
        </div>
        <div class="form-row">
            <label>Tác giả:</label>
            <input type="text" name="author" required />
        </div>
        <div class="form-row">
            <label>Thể loại:</label>
            <input type="text" name="genre" />
        </div>
        <div class="form-row">
            <label>Mô tả (sẽ kiểm duyệt sơ bộ):</label>
            <textarea name="description" id="bookContent" rows="5" required placeholder="Nhập mô tả hoặc nội dung tóm tắt sách..."></textarea>
        </div>
        <div class="form-row">
            <label>Ảnh bìa:</label>
            <input type="file" name="cover" accept="image/*" />
        </div>
        <div class="form-row">
            <label>File nội dung:
                <span style="color:gray;font-size:0.95em;">(Hỗ trợ: .txt, .doc, .docx, .pdf)</span>
            </label>
            <input type="file" name="contentFile" accept=".txt,.doc,.docx,.pdf" required />
        </div>
        <button type="submit" class="btn btn-add">Upload</button>

        <c:if test="${not empty filterError}">
            <div class="alert-error">${filterError}</div>
        </c:if>
    </form>

    <div id="moderationStatus" style="margin-top:10px"></div>

    <div class="action-links" style="margin-top:18px;">
        <a href="${pageContext.request.contextPath}/book?action=list" class="btn">Quay lại danh sách sách</a>
    </div>
</div>
</body>
</html>
