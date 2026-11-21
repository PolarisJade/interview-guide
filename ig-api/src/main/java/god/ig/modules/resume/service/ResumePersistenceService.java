package god.ig.modules.resume.service;

import god.ig.common.exception.BusinessException;
import god.ig.common.exception.ErrorCode;
import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 简历历史服务
 * 简历历史和导出简历分析报告
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumePersistenceService {

    private final ResumeRepository resumeRepository;

    /**
     * 获取所有简历列表
     */
    public List<ResumeEntity> getAllResumes() {
        return resumeRepository.findAll();
    }

    public Optional<ResumeEntity> findById(Long id) {
        return resumeRepository.findById(id);
    }

    /**
     * 删除简历及其所有关联数据
     * 包括：简历分析记录、面试会话（会自动删除面试答案）
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteResume(Long id) {
        Optional<ResumeEntity> resumeOpt = resumeRepository.findById(id);
        if (resumeOpt.isEmpty()) {
            throw new BusinessException(ErrorCode.RESUME_NOT_FOUND);
        }

        ResumeEntity resume = resumeOpt.get();
        //TODO 1. 删除所有的简历分析记录

        //2. 删除简历实体
        resumeRepository.delete(resume);
        log.info("简历删除成功: id={}， filename={}", id, resume.getOriginalFilename());
    }
}
