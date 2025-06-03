package service;

import util.Utils;
import filter.FilterResult;
import org.json.JSONObject;
import java.net.http.*;
import java.net.URI;


public class OpenAIContentFilterService implements IContentFilterService {

    @Override
    public FilterResult check(String content) {
        try {
            // 1. Đảm bảo content không null, quá dài thì cắt
            if (content == null) content = "";
            if (content.length() > 10000) {
                content = content.substring(0, 10000); // OpenAI Moderation limit 10k ký tự
            }

            String apiKey = Utils.getEnv("OPENAI_API_KEY");  // Thay bằng API Key thật
            HttpClient client = HttpClient.newHttpClient();

            // 2. Build body JSON chuẩn
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("input", content);
            String json = jsonObj.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/moderations"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("OpenAI MODERATION raw response: " + response.body());

            // 3. Parse response, kiểm tra kết quả
            JSONObject body = new JSONObject(response.body());
            if (!body.has("results")) {
                // Nếu lỗi hoặc API trả về error
                System.err.println("[MODERATION][ERROR] " + body.toString());
                return FilterResult.rejected("Không thể kiểm duyệt nội dung. Vui lòng thử lại sau.");
            }
            JSONObject res = body.getJSONArray("results").getJSONObject(0);
            boolean flagged = res.getBoolean("flagged");
            JSONObject categories = res.getJSONObject("categories");

            boolean isSensitive = flagged
                    || categories.optBoolean("sexual", false)
                    || categories.optBoolean("violence", false)
                    || categories.optBoolean("hate", false);

            if (isSensitive) {
                System.out.println("[MODERATION][FLAGGED] " + categories.toString());
                return FilterResult.rejected("Nội dung bị AI đánh dấu là không phù hợp.");
            } else {
                return FilterResult.passed();
            }

        } catch (Exception e) {
            System.err.println("[MODERATION][ERROR] " + e.getMessage());
            return FilterResult.rejected("Không thể kiểm duyệt nội dung. Vui lòng thử lại sau.");
        }
    }

}
