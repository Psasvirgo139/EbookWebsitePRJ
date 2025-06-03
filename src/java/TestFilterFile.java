import service.OpenAIContentFilterService;
import service.IContentFilterService;
import filter.FilterResult;
import util.Utils;

public class TestFilterFile {
    public static void main(String[] args) {
        String path = "C:\\Users\\Admin\\Downloads\\ilovepdf_converted\\Phim3.txt"; // đường dẫn file test
        String ext = "txt"; // đổi cho đúng file

        // --- Đọc file ---
        String content;
        try {
            content = Utils.readAnyTextFile(path, ext);
        } catch (Exception e) {
            System.err.println("Không đọc được file: " + e.getMessage());
            return;
        }

        // --- Chuẩn hóa text ---
        String cleaned = Utils.normalizeText(content);

        // --- In kiểm tra ---
        System.out.println("\nFile content (first 500 chars):\n" + cleaned.substring(0, Math.min(500, cleaned.length())) + "\n");

        // --- Kiểm duyệt ---
        IContentFilterService filterService = new OpenAIContentFilterService();
        FilterResult result = filterService.check(cleaned);

        System.out.println("===> Kết quả kiểm duyệt: " + (result.isPassed() ? "HỢP LỆ" : "VI PHẠM"));
        System.out.println("Message: " + result.getMessage());
    }
}
