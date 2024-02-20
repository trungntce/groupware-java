package kr.co.hs.project.dto;

import lombok.Data;

@Data
public class ProjectLeaderAuthDTO {
    private int projectLeaderAuthId;
    private int pjId;
    private String leaderType;
    private int empCd;
    private int deptChangeHistoryId;
    private int deptCd;
    private int positionCd;
    private String currentYn;
    private int historyNum;
    private String createDate;
    private String createId;
    private String empName;
    private String deptName;
    private String createBy;
    private String langCd;

    public String getCreateDate() {
        return createDate = this.createDate.substring(0,19);
    }
}
