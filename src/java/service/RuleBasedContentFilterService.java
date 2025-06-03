package service;

import filter.FilterResult;

/**
 * Service kiểm duyệt nội dung dựa vào rule/từ khóa cấm (không dùng AI).
 */
public class RuleBasedContentFilterService implements IContentFilterService {

    private static final String[] FORBIDDEN_KEYWORDS = { "sex", "bạo lực", "cấm", "thù ghét" };

    @Override
    public FilterResult check(String text) {
        if (text == null || text.isBlank()) {
            return FilterResult.rejected("Nội dung rỗng!");
        }

        String lower = text.toLowerCase();
        for (String keyword : FORBIDDEN_KEYWORDS) {
            if (lower.contains(keyword)) {
                return FilterResult.rejected("Nội dung vi phạm: phát hiện từ cấm '" + keyword + "'");
            }
        }

        return FilterResult.passed(); // Hợp lệ
    }
}
