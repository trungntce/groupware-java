package kr.co.hs.project.dto;

import lombok.Data;

@Data
public class ProjectAuthDTO {
    private int rownum;
    private String langCd;
    private int projectAuthId;
    private int pjId;
    private int empCd;
    private String createDate;
    private String createId;
    private int deptChangeHistoryId;
    private int deptCd;
    private int positionCd;
    private String empName;
    private String deptName;
    private String positionName;
}
