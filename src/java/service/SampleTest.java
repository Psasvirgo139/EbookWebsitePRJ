import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SampleTest {
    @Test
    void testMock() {
        MyService mockService = mock(MyService.class);
        when(mockService.sayHello()).thenReturn("hello");
        assertEquals("hello", mockService.sayHello());
    }

    // Ví dụ class giả lập để test mock
    static class MyService {
        public String sayHello() { return "real"; }
    }
}
