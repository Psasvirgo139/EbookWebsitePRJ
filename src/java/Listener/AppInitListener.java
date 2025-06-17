package listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

@WebListener
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Ứng dụng EbookWebsitePRJ đã KHỞI ĐỘNG! ===");
        // Bạn có thể preload dữ liệu, khởi tạo pool, preload tag-list ở đây
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Ứng dụng EbookWebsitePRJ đã DỪNG! ===");
    }
}
