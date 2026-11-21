package god.ig.modules.interview.model;

import java.time.LocalDateTime;

public record InterviewHistoryItemVO(
        Long id,
        String sessionId,
        Integer totalQuestions,
        String status,
        String evaluateStatus,
        String evaluateError,
        Integer overallScore,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {}
