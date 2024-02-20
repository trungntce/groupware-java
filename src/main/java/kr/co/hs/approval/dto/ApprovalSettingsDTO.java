package kr.co.hs.approval.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ApprovalSettingsDTO {

    private Integer approvalSettingsId;
    private String signatureImagePath;
    private Date createDate;
    private String createId;
    private String useYn;
}
