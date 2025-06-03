package ai;

public class OpenAIContentChecker {

    public String check(String text) {
        // Giả lập call API OpenAI Moderation (có thể return JSON, hoặc string message)
        // TODO: Gọi thật API, nhận kết quả, parse lại JSON hoặc format theo output chuẩn
        // Ví dụ: Nếu có vi phạm, trả về chi tiết. Nếu không, trả về "OK"
        // Dưới đây chỉ là demo:
        if (text != null && text.toLowerCase().contains("sex")) {
            return "{\"status\":\"violation\", \"reason\":\"Nội dung nhạy cảm\"}";
        }
        return "OK";
    }
}
