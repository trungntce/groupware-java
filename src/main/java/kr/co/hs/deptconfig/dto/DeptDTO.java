package kr.co.hs.deptconfig.dto;

import lombok.Data;

@Data
public class DeptDTO {

    private int deptCd;
    private int deptParentCd;
    private int deptLevel;
    private String collapseYn;
    private int sort;
    private String deptName;
    private String deptParentName;
    private String useYn;

    private String deptLevelName;
}
