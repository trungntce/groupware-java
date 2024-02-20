package kr.co.hs.work.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkDTO {
    private int rownum;
    private String useYn;
    private int	workId;
    private String langCd;
    private int	empCd;
    private int	deptChangeHistoryId;
    private int	deptCd;
    private int	positionCd;
    private String workStatus;
    private String title;
    private String contents;
    private String importantYn;
    private String workStartDt;
    private String workEndDt;
    private String regDt;
    private String empName;
    private String deptName;
    private String positionName;
    private String historyDeptName;
    private String status;
    private String translationStatus;
    private String empList;


    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<WorkDTO> aaData;
}
