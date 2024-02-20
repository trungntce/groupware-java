package kr.co.hs.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectMainDTO {
    private int rownum;
    private int pjId;

    private int empCd;
    private String empName;
    private int deptChangeHistoryId;
    private String historyDeptName;
    private int deptCd;
    private String deptName;
    private int positionCd;
    private String positionName;
    private String langCd;
    private String title;
    private String projectStatus;
    private String projectStartDate;
    private String projectEndDate;
    private String regDt;
    private String useYn;
    private String translationStatus;
    private String leaderProjectName;
    private String leaderAccountingName;
    private String positionAccountingName;
    private String deptAccountingName;
    private long advanceAmount;
    private long remainAmount;
    private long useAmount;
    private String approval;
    private String registerApproval;
    private String rule;
    private String type;

    public String getProjectStartDate() {
        return projectStartDate = this.projectStartDate.substring(0,10);
    }

    public String getProjectEndDate() {
        return projectEndDate = this.projectEndDate.substring(0,10);
    }

    int iTotalRecords;
    int iTotalDisplayRecords;
    String txtSearch;
    int startRow;
    int recordsPerPage;
    String dateSql;
    String inputSearchSql;
    String str;
    String deptSearch;
    String optionSearch;
    String inputSearch;
    String iiDisplayStart;
    String iiDisplayLength;
    String ssSortDir;

    String sEcho;
    String sColumns;
    List<ProjectMainDTO> aaData;


}
