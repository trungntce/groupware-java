package kr.co.hs.management.dto;

import lombok.Data;

@Data
public class ManagementOrgDTO {
    private int deptCd;
    private int deptParentCd;
    private int deptLevel;
    private String collapseYn;
    private int sort;
    private String deptName;
    private String deptParentName;
    private String useYn;
}
