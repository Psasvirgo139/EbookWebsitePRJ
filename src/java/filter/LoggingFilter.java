package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")    // chạy cuối (đặt **sau** EncodingFilter trong build path)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        long start = System.nanoTime();

        chain.doFilter(request, response);           // gọi servlet / JSP tiếp theo

        long durationMs = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("LOG [%s] %s %d ms%n",
                req.getMethod(), req.getRequestURI(), durationMs);
    }
}
