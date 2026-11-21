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

        byte[] digest = MessageDigest.getInstance("SHA-256").digest(file.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));  // 每个字节转两位十六进制
        }
        String fileHash = sb.toString();

        ResumeEntity entity = new ResumeEntity();
        entity.setFileHash(fileHash);
        entity.setOriginalFilename(originalFilename);
        entity.setFileSize(fileSize);
        entity.setContentType(contentType);

        return resumeRepository.save(entity);
        //1. 验证文件

        //2. 验证文件类型


    }
}
