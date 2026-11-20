package god.ig.modules.resume.service;

import god.ig.modules.resume.model.ResumeEntity;
import god.ig.modules.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 简历上传服务
 * 处理简历上传、解析的业务逻辑
 * AI 分析改为异步处理，通过 Redis Stream 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeUploadService {

    private final ResumeRepository resumeRepository;

    /**
     * 上传并分析简历（异步）
     *
     * @param file 简历文件
     * @return 上传结果（分析将异步进行）
     */
    public ResumeEntity uploadAndAnalyze(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        String originalFilename = file.getOriginalFilename();
        long fileSize = file.getSize();
        String contentType = file.getContentType();

        String fileHash = Arrays.toString(MessageDigest.getInstance("SHA-256").digest(file.getBytes()));

        ResumeEntity entity = new ResumeEntity();
        entity.setFileHash(fileHash);
        entity.setOriginalFileName(originalFilename);
        entity.setFileSize(fileSize);
        entity.setContentType(contentType);

        return resumeRepository.save(entity);
        //1. 验证文件

        //2. 验证文件类型


    }
}
