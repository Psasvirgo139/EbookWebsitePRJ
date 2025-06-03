package service;

import filter.FilterResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAIContentFilterServiceTest {

    @Test
    void testCheck_OK() {
        OpenAIContentFilterService service = new OpenAIContentFilterService();

        FilterResult result = service.check("Đây là nội dung sách học tập phù hợp với mọi lứa tuổi.");
        assertTrue(result.isPassed(), "Nội dung hợp lệ phải pass");
        assertNull(result.getReason(), "Lý do nên null nếu hợp lệ");
    }

    @Test
    void testCheck_Violated() {
        OpenAIContentFilterService service = new OpenAIContentFilterService();

        FilterResult result = service.check("Sex bạo lực tàn nhẫn");
        assertFalse(result.isPassed(), "Nội dung vi phạm phải bị chặn");
        assertNotNull(result.getReason(), "Phải có lý do nếu bị chặn");
        assertTrue(result.getReason().toLowerCase().contains("nội dung"), "Lý do phải chứa mô tả");
    }
}
