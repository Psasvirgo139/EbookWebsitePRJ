/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import util.Utils;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenAIContentSummaryService implements IContentSummaryService {

    String apiKey = Utils.getEnv("OPENAI_API_KEY");
    
    @Override
    public String summarize(String content) throws Exception {
        // Nếu content quá dài, nên cắt (ví dụ lấy 8000 ký tự đầu)
        if (content.length() > 8000) {
            content = content.substring(0, 8000);
        }
        String prompt = "Đọc nội dung dưới đây và tóm tắt lại thành một đoạn ngắn (80-200 từ), dễ hiểu, nổi bật ý chính, không spoil chi tiết kết thúc. Giữ văn phong tự nhiên, hấp dẫn, phù hợp hiển thị trên website giới thiệu sách.\nNội dung: \"" + content + "\"\nKết quả tóm tắt:";

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o");
        requestBody.put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                        .put("role", "user")
                        .put("content", prompt)
                ));
        requestBody.put("max_tokens", 512);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("OpenAI Response: " + response.body()); // Thêm dòng này để log

        JSONObject obj = new JSONObject(response.body());
        if (obj.has("choices")) {
            String summary = obj.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            return summary.trim();
        } else if (obj.has("error")) {
            String msg = obj.getJSONObject("error").optString("message", "Unknown error from OpenAI.");
            throw new RuntimeException("OpenAI API error: " + msg);
        } else {
            throw new RuntimeException("OpenAI API: Unexpected response: " + response.body());
        }
    }
}
