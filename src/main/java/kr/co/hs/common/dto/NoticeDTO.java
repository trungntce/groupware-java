package kr.co.hs.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class NoticeDTO {
    private int eventNoticeId;
    private int empCd;
    private int noticeCd;
    private int deptCd;
    private int positionCd;
    private Integer deptChangeHistoryId;
    private String message;
    private String type;
    private String status;
    private String empName;
    private String link;
    private String htmlStrOpen;
    private String htmlStrClose;
    private String regDt;
    private String content;
    private String preStr;
    private String timeUp;
    private String deptLevelName;
    private String positionName;

    public void setTimeUp(String timeUp) {
        this.timeUp = timeUp.substring(0,19);
    }

    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<NoticeDTO> aaData;
    List<Integer> toEmpCd;

    public NoticeDTO(){}

    public NoticeDTO(Integer empCd, Integer noticeCd, List<Integer> toEmpCd, String message, String type){
        this.empCd = empCd;
        this.noticeCd = noticeCd;
        this.toEmpCd = toEmpCd;
        this.message = message;
        this.type = type;
    }
}
