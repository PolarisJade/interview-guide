package god.ig.modules.resume;

import god.ig.common.result.Result;
import god.ig.modules.resume.model.ResumeDetailVO;
import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.model.ResumeListItemVO;
import god.ig.modules.resume.service.ResumeDeleteService;
import god.ig.modules.resume.service.ResumeHistoryService;
import god.ig.modules.resume.service.ResumeUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeUploadService resumeUploadService;
    private final ResumeHistoryService resumeHistoryService;
    private final ResumeDeleteService resumeDeleteService;

    @PostMapping("upload")
    public Result<ResumeEntity> upload(@RequestParam("file")MultipartFile file) throws NoSuchAlgorithmException, IOException {
        return Result.success(resumeUploadService.uploadAndAnalyze(file));
    }

    /**
     * 获取所有简历列表
     */
    @GetMapping
    public Result<List<ResumeListItemVO>> getAllResumes() {
        List<ResumeListItemVO> resumes = resumeHistoryService.getAllResumes();
        return Result.success(resumes);
    }

    /**
     * 获取简历详情（包含分析历史）
     */
    @GetMapping("/{id}/detail")
    public Result<ResumeDetailVO> getResumeDetail(@PathVariable Long id){
        return Result.success(resumeHistoryService.getResumeDetail(id));
    }

    /**
     * 删除简历
     *
     * @param id 简历ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteResume(@PathVariable Long id) {
        resumeDeleteService.deleteResume(id);
        return Result.success();
    }
}
