package kr.co.hs.approval.dto;

import lombok.Data;

@Data
public class ApprovalTranslationDTO {
    private Integer approvalId = null;
    private Integer signLineId = null;
    private String arrSignLineId = null;
    private String langCd = null;
    private Integer translationId = null;
    private String contents = null;
    private String title = null;

    public ApprovalTranslationDTO() {
    }

    public ApprovalTranslationDTO(Integer approvalId) {
        this.approvalId = approvalId;
    }
}
