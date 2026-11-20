package god.ig.modules.resume;

import god.ig.common.result.Result;
import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.service.ResumeUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeUploadService resumeUploadService;

    @PostMapping("upload")
    public Result<ResumeEntity> upload(@RequestParam("file")MultipartFile file) throws NoSuchAlgorithmException, IOException {
        return Result.success(resumeUploadService.uploadAndAnalyze(file));
    }
}
