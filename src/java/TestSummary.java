import service.OpenAIContentSummaryService;
import service.IContentSummaryService;
import util.Utils;

public class TestSummary {
    public static void main(String[] args) {
        try {
            // --- Nhập đường dẫn file cần test ---
            String filePath = "C:\\Users\\Admin\\Downloads\\ilovepdf_converted\\Phim1.pdf"; // Thay đổi cho đúng file bạn muốn test
            String fileType = filePath.substring(filePath.lastIndexOf('.') + 1); // tự nhận diện loại file

            // Đọc file ra text
            String fileContent = Utils.readAnyTextFile(filePath, fileType);

            // In ra thử (nên chỉ in 500 ký tự đầu nếu file lớn)
            System.out.println("File content (first 500 chars):\n" + 
                fileContent.substring(0, Math.min(fileContent.length(), 500)));

            // Gọi OpenAI tóm tắt
            IContentSummaryService summaryService = new OpenAIContentSummaryService();
            String summary = summaryService.summarize(fileContent);

            // In kết quả
            System.out.println("\n==== TÓM TẮT AI ====");
            System.out.println(summary);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
