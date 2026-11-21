package god.ig.modules.resume.model;

import god.ig.common.model.AsyncTaskStatus;

import java.time.LocalDateTime;

/**
 * 简历列表项DTO
 */
public record ResumeListItemVO(
        Long id,
        String originalFilename,
        Long fileSize,
        LocalDateTime uploadedAt,
        Integer accessCount,
        Integer latestScore,
        LocalDateTime lastAnalyzedAt,
        Integer interviewCount,
        AsyncTaskStatus analyzeStatus,
        String analyzeError
) {}
