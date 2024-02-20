package kr.co.hs.oldwork.dto;

import lombok.Data;

@Data
public class CoperationDTO {
    private Integer workCooperationId;
    private Integer workId;
    private Integer empCd;
    private int deptChangeHistoryId;
    private Integer deptCd;
    private Integer positionCd;
    private String workCheckYn;
    private String workCheckDt;
    private String regDt;

    private String empName;
    private String deptName;
    private String positionName;
    private int rownum;


}
