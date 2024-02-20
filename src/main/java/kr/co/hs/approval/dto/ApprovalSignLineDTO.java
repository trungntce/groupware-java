package kr.co.hs.approval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApprovalSignLineDTO {
    private Integer signLineId = null;
    private Integer approvalId = null;
    private Integer approvalType = 1;
    private Integer step = null;
    private Integer approvalStatus = null;
    private Integer approvalRole = null;
    private String empId = null;
    private Integer empCd = null;
    private String memo = null;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = null;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approvalDate = null;
    private String createId = null;
    private String contents = null;
    private String signatureImagePath = "";
    private String deptName = "";
    private String approvalRoleName = "";
    private String approvalStatusName = "";
    private String positionName;
    private String empName;

    public ApprovalSignLineDTO(){}
}
