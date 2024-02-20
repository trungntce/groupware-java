package kr.co.hs.approval.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ApprovalSettingsSearchDTO {

    private Integer approvalSettingsId;
    private String signatureImagePath;
    private Date createDate;
    private String createId;
    private String useYn;

    public ApprovalSettingsSearchDTO(){}

    public ApprovalSettingsSearchDTO(String createId, String useYn){
        this.createId = createId;
        this.useYn = useYn;
    }
}
