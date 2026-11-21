package god.ig.modules.resume.model;

import god.ig.common.model.AsyncTaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 简历实体
 * Resume Entity for deduplication and persistence
 */
@Data
@Entity
@Table(name = "resumes",
        //声明数据库中的索引
        indexes = {@Index(name = "idx_resume_hash", columnList = "fileHash", unique = true)})
public class ResumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 文件内容的SHA-256哈希值，用于去重
    @Column(nullable = false, unique = true, length = 64)
    private String fileHash;

    // 原始文件名
    @Column(nullable = false)
    private String originalFilename;

    // 文件大小（字节）
    private Long fileSize;

    //文件类型
    private String contentType;

    // RustFS存储的文件Key
    @Column(length = 500)
    private String storageKey;

    // RustFS存储的文件URL
    @Column(length = 1000)
    private String storageUrl;

    // 解析后的简历文本
    @Column(columnDefinition = "TEXT")
    private String resumeText;

    // 上传时间
    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    // 最后访问时间
    private LocalDateTime lastAccessedAt;

    // 访问次数
    private Integer accessCount = 0;

    // 分析状态（新上传时为 PENDING，异步分析完成后变为 COMPLETED）
    @Enumerated(EnumType.STRING)    //枚举字段以字符串的形式存储在数据库中
    @Column(length = 20)
    private AsyncTaskStatus analyzeStatus = AsyncTaskStatus.PENDING;

    //最后一次有效进展事件（状态变化与心跳都推进）用于卡住任务回复判定
    @Column(name = "analyze_updated_at")
    private LocalDateTime analyzeUpdatedAt;

    //恢复调度器自动补投次数（手动重试清零），与Stream retryCount 独立
    @Column(name = "analyze_recovery_count", nullable = false)
    private int analyzeRecoveryCount = 0;

    //当前 PROCESSING 执行次数，条件心跳和最终状态写入必须同时匹配该值
    @Column(name = "analyze_attempt_id", length = 36)
    private String analyzeAttemptId;

    //分析错误信息（失败时记录）
    @Column(length = 500)
    private String analyzeError;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
        lastAccessedAt = LocalDateTime.now();
        accessCount = 1;
    }
}
