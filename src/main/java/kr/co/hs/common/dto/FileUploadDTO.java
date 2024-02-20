package kr.co.hs.common.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDTO {
    private int rownum;
    private int fileId;
    private int deptChangeHistoryId;
    private int deptCd;
    private int positionCd;
    private int empCd;
    private String fileType;
    private String filePath;
    private String fileName;
    private String fileHashName;
    private String fileExt;
    private String fileSize;
    private String regDt;
    private String fileStatus;
    private MultipartFile file;
}

