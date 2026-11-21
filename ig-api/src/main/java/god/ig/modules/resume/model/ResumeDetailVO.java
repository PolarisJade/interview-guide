package god.ig.modules.resume.model;

import god.ig.common.model.AsyncTaskStatus;
import god.ig.modules.interview.model.InterviewHistoryItemVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 简历详情DTO
 */
public record ResumeDetailVO(
        Long id,
        String filename,
        Long fileSize,
        String contentType,
        String storageUrl,
        LocalDateTime uploadedAt,
        Integer accessCount,
        String resumeText,
        AsyncTaskStatus analyzeStatus,
        String analyzeError,
        List<AnalysisHistoryVO> analyses,
        List<InterviewHistoryItemVO> interviews
) {
    /**
     * 分析历史VO
     */
    public record AnalysisHistoryVO(
            Long id,
            Integer overallScore,
            Integer contentScore,
            Integer structureScore,
            Integer skillMatchScore,
            Integer expressionScore,
            Integer projectScore,
            String summary,
            LocalDateTime analyzedAt,
            List<String> strengths,
            List<Object> suggestions
    ){}
}
