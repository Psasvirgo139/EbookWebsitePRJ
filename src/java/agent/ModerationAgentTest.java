package agent;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModerationAgentTest {

    @Test
    void testModerate_OK() {
        // Mock LLM để trả về "OK"
        LLM mockLLM = mock(LLM.class);
        when(mockLLM.generateResponse(anyList())).thenReturn("OK");

        ModerationAgent agent = new ModerationAgent(mockLLM);

        ActionResult result = agent.moderate("Nội dung sạch sẽ hợp lệ.");
        assertNotNull(result);
        assertNull(result.getError());
        assertEquals("OK", result.getResult());
    }

    @Test
    void testModerate_Violated() {
        // Mock LLM để trả về lý do vi phạm
        LLM mockLLM = mock(LLM.class);
        when(mockLLM.generateResponse(anyList())).thenReturn("Nội dung vi phạm: phát hiện từ cấm sex");

        ModerationAgent agent = new ModerationAgent(mockLLM);

        ActionResult result = agent.moderate("Sex bạo lực.");
        assertNotNull(result);
        assertNull(result.getResult());
        assertTrue(result.getError().contains("vi phạm"));
    }
}
