package kr.co.hs.deptconfig.dto;

import lombok.Data;

@Data
public class DeptSearchDTO {

    private int deptCd;
    private int deptParentCd;
    private int deptLevel;
    private String collapseYn;
    private int sort;
    private String deptName;
    private String deptParentName;
    private String useYn;
    private String langCd;
    private String deptLevelName;
    private String translationSpecific;
    private String adminSpecitfic;
    private int empCd;
}
