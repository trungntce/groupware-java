package kr.co.hs.files.dto;

import kr.co.hs.emp.dto.EmpDTO;
import lombok.Data;

import java.util.Date;

@Data
public class FilesDTO {
    private Integer fileId;
    private Integer deptCd = 0;
    private Integer positionCd;
    private Integer deptChangeHistoryId;
    private Integer empCd;
    private String fileType;
    private String filePath;
    private String fileName;
    private String fileHashName;
    private String fileExt;
    private String fileSize;
    private String fileStatus = "real";
    private Date regDt;

    public FilesDTO(){}

    public FilesDTO(EmpDTO empDTO){
        this.deptCd = empDTO.getDeptCd();
        this.positionCd = empDTO.getPositionCd();
        this.empCd = empDTO.getEmpCd();
    }
}
