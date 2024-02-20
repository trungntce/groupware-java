package kr.co.hs.approval.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class ApprovalSignLineSearchDTO extends PageDTO {
    private Integer signLineId = null;
    private Integer approvalId = null;
    private Integer approvalType = null;
    private Integer approvalRole = null;
    private List<Integer> lstApprovalRole = null;
    private List<Integer> listStatus = null;
    private Integer step = null;
    private Integer approvalStatus = null;
    private String empId = null;
    private Integer empCd = null;
    private String memo = null;
    private Date createDate = null;
    private Date approvalDate = null;
    private String createId = null;
    private String orderByColumn = null;
    private String orderByType = null;

    public ApprovalSignLineSearchDTO() {
    }

    public ApprovalSignLineSearchDTO(Integer approvalId) {
        this.approvalId = approvalId;
    }
}
