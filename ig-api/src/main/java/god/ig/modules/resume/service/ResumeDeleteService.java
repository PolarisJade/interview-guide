package god.ig.modules.resume.service;

import god.ig.common.exception.BusinessException;
import god.ig.common.exception.ErrorCode;
import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 简历删除服务
 * 处理简历删除的业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeDeleteService {

    private final ResumeRepository resumeRepository;
    private final ResumePersistenceService resumePersistenceService;

    /**
     * 删除简历
     * @param id 简历id
     */
    public void deleteResume(Long id) {
        log.info("收到删除简历请求: id={}", id);

        // 获取简历信息（用于删除存储文件）
        ResumeEntity resume = resumeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESUME_NOT_FOUND));

        // 1. 删除存储的文件（FileStorageService 已内置存在性检查）
        // 2. 删除面试会话（会自动删除面试答案）

        // 3. 删除数据库记录（包括分析记录）
        resumePersistenceService.deleteResume(id);
        log.info("简历删除完成: id={}", id);
    }
}
