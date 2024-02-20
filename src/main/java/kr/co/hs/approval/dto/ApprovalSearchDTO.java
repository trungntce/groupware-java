package kr.co.hs.approval.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class ApprovalSearchDTO extends PageDTO {
    private Integer approvalId = null;
    private Integer formId = null;
    private Integer approvalStatus = null;
    private Integer approvalRole = null;
    private Integer empCd = null;
    private List<Integer> listStatus = null;
    private List<Integer> listApprovalStatus = null;
    private String orderByColumn = null;
    private String orderByType = null;
    private String startDate = null;
    private String endDate = null;
    private Integer levelDept = null;
    private String keywordType = null;
    private String keyword = null;
    private String dateFlag = null;
    private String myApproval = null;
    private List<Integer> listDeptCd = null;
    private String fullView = null;
    private String translationId = null;
    private String original = null;
    private boolean isTranslator = false;
    private Integer translateEmpCd = null;

    public ApprovalSearchDTO(){}

    public ApprovalSearchDTO(Integer approvalId){
        this.approvalId = approvalId;
    }
}
