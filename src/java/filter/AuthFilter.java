package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/user/*", "/admin/*"})   // chỉ áp cho folder user & admin
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest)  request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Lấy thông tin user từ session (đã set ở LoginServlet)
        Object userObj = (session != null) ? session.getAttribute("user") : null;

        // Nếu chưa đăng nhập -> về trang login
        if (userObj == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền khi vào khu /admin/*
        boolean isAdminArea = req.getRequestURI().startsWith(req.getContextPath() + "/admin/");
        if (isAdminArea) {
            model.User user = (model.User) userObj;
            if (!"admin".equalsIgnoreCase(user.getRole())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không đủ quyền truy cập!");
                return;
            }
        }
        chain.doFilter(request, response);   // hợp lệ -> đi tiếp
    }
}
