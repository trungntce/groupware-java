package kr.co.hs.approval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ApprovalDTO {
    private int approvalId;
    private int formId;
    private String title;
    private String contents;
    private int approvalStatus;
    private int approvalStep;
    private String useYn;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String createId;
    private int empCd;
    private String empName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedDate;
    private int deptChangeHistoryId;
    private int deptCd;
    private String deptName;
    private int positionCd;
    private String positionName;
    private List<Integer> empSign;
    private List<Integer> empSignRole;
    private Integer signLineId;
    private int approvalRole;
    private String signEmpId;
    private int signEmpCd;
    private int signStep;
    private int approvalStatusSign;
    private Integer translationId = null;
    private List<Integer> idFiles = new ArrayList<>();
    private Integer translatorEmpCd;
    private String translatorName;

    public ApprovalDTO(){}

    public ApprovalDTO(ApprovalFormDTO approvalFormDTO){
        this.formId = approvalFormDTO.getFormId();
        this.title = approvalFormDTO.getFormName();
        this.contents = approvalFormDTO.getFormContents();
    }
}
