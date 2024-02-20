package kr.co.hs.daywork.dto;

import lombok.Data;

import java.util.List;

@Data
public class DayWorkDTO {
    private int rownum;
    private int dayWorkId;
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
    private String contents;
    private String type;

    public void setCreateDate(String createDate) {
        this.createDate = createDate.substring(0,19);
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate.substring(0,19);
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt.substring(0,19);
    }

    private String regDt;
    private String useYn;
    private String ROWNUM;
    private String status;
    private String createDate;
    private String modifyDate;
    private int id;
    private String workStatus;
    private String translationStatus;
    private int childrenDayWorkId;


    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<DayWorkDTO> aaData;
}
