<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>scroll | Đọc truyện online</title>
        <!-- Core CSS (minified bundle) -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
        <!-- Preconnect fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <meta name="description" content="Scroll – Kho truyện lớn, cập nhật nhanh, đọc mượt trên mọi thiết bị." />
        <link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" />
    </head>
    <body>
        <!-- Skip‑link for Accessibility  -->
        <a href="#main" class="skip-link">Bỏ qua và tới nội dung chính</a>
        <!-- Copy toàn bộ phần HTML bên dưới của bạn như cũ -->
        <!-- ... -->
        <!-- FOOTER ... -->
        <!-- ... -->
        <!-- DYNAMIC MODALS ... -->
        <dialog class="modal" id="modalAuth" aria-modal="true">
            <form method="dialog" class="modal-content" id="authForm" novalidate>
                <button class="close" id="closeModal" aria-label="Đóng">×</button>
                <h3 id="modalTitle">Đăng nhập</h3>
                <input type="text" id="authUser" placeholder="Tên đăng nhập" required />
                <input type="password" id="authPass" placeholder="Mật khẩu" required />
                <button type="submit" id="submitAuth">Đăng nhập</button>
                <p class="switch-auth">
                    Chưa có tài khoản?
                    <a href="#" id="switchToRegister">Đăng ký</a>
                </p>
            </form>
        </dialog>
        <!-- Core JS (bundled & deferred) -->
        <script src="${pageContext.request.contextPath}/js/app.js" defer></script>
        <!-- Quick enhancement: đóng menu mobile khi chọn link -->
        <script>
            document.querySelectorAll('.main-nav a').forEach((l) =>
                l.addEventListener('click', () => {
                    document.getElementById('mainNav').classList.remove('show-mobile');
                })
            );
        </script>
    </body>
</html>
