package kr.co.hs.deptconfig.dto;

import lombok.Data;

@Data
public class DeptChangeHistory {
    private String deptVersion;
    private String deptChangeHistoryId;
    private String deptCd;
    private String deptParentCd;
    private String deptLevel;
    private String sort;
    private String collapseYn;
    private String deptName;



}
