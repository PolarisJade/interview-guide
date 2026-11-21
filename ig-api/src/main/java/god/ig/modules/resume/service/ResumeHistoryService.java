package god.ig.modules.resume.service;

import god.ig.common.exception.BusinessException;
import god.ig.common.exception.ErrorCode;
import god.ig.modules.resume.model.ResumeDetailVO;
import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.model.ResumeListItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 简历历史服务
 * 简历历史和导出简历分析报告
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeHistoryService {

    private final ResumePersistenceService resumePersistenceService;

    /**
     * 获取所有简历列表
     */
    public List<ResumeListItemVO> getAllResumes() {
        List<ResumeEntity> resumes = resumePersistenceService.getAllResumes();

        return resumes.stream().map(resume -> {

            //使用Mapstruct 映射
            return new ResumeListItemVO(
                    resume.getId(),
                    resume.getOriginalFilename(),
                    resume.getFileSize(),
                    resume.getUploadedAt(),
                    resume.getAccessCount(),
                    null,
                    null,
                    null,
                    resume.getAnalyzeStatus(),
                    resume.getAnalyzeError()
            );
        }).toList();
    }

    /**
     * 获取简历详情（包含分析历史）
     */
    public ResumeDetailVO getResumeDetail(Long id) {
        Optional<ResumeEntity> resumeOpt = resumePersistenceService.findById(id);
        if (resumeOpt.isEmpty()){
            throw new BusinessException(ErrorCode.RESUME_NOT_FOUND);
        }
        ResumeEntity resume = resumeOpt.get();

        //TODO 获取所有分析记录，使用 MapStruct 批量转换
        //TODO 使用 InterviewMapper 转换面试历史
        return new ResumeDetailVO(
                resume.getId(),
                resume.getOriginalFilename(),
                resume.getFileSize(),
                resume.getContentType(),
                resume.getStorageUrl(),
                resume.getUploadedAt(),
                resume.getAccessCount(),
                resume.getResumeText(),
                resume.getAnalyzeStatus(),
                resume.getAnalyzeError(),
                null,
                null
        );
    }
}
