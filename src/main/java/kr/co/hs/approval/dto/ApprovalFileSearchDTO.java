package kr.co.hs.approval.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApprovalFileSearchDTO {
    private Integer approvalId;
    private Integer fileId;
    private List<Integer> fileIds = new ArrayList<>();

    public ApprovalFileSearchDTO(){}

    public ApprovalFileSearchDTO(Integer approvalId){
        this.approvalId = approvalId;
    }
}
