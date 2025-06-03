package agent;

import java.util.List;

/**
 * LLM là adapter trung gian giao tiếp với AI provider (OpenAI, Anthropic, ...).
 * Tất cả class agent khác chỉ cần truyền danh sách Message và nhận về chuỗi phản hồi.
 * Khi muốn đổi provider, chỉ cần thay đổi duy nhất class này.
 */
public class LLM {

    /**
     * Gọi AI provider để sinh phản hồi dựa trên danh sách message hội thoại.
     * (Demo: hiện chỉ trả về chuỗi ghép từ role và content để minh hoạ, không gọi API thực)
     *
     * @param messages Danh sách Message (system, user, assistant...)
     * @return Phản hồi (response) dạng text từ AI
     */
    public String generateResponse(List<Message> messages) {
        // ======== LOG PROMPT gửi đi =========
        System.out.println("[LLM] Gửi prompt tới AI: " + messages); // LOG 1

        // ======== Giả lập gọi OpenAI (có thể thay bằng HTTP call thật sau) =========
        StringBuilder prompt = new StringBuilder();
        for (Message m : messages) {
            prompt.append("[").append(m.getRole()).append("] ").append(m.getContent()).append("\n");
        }
        String response;
        // Demo response: AI luôn trả về OK nếu có từ "kiểm duyệt"
        if (prompt.toString().toLowerCase().contains("kiểm duyệt")) {
            response = "OK";
        } else {
            response = "Nội dung vi phạm: phát hiện từ cấm!";
        }

        // ======== LOG RESPONSE nhận lại =========
        System.out.println("[LLM] Nhận phản hồi từ AI: " + response); // LOG 2

        return response;
        // ===========================================================================
    }
}
