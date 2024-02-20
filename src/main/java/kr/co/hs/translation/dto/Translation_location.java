package kr.co.hs.translation.dto;

import lombok.Data;

@Data
public class Translation_location {
    private int id;
    private int empCd;
    private int deptChangeHstoryId;
    private int deptCd;
    private int positionCd;
    private String workStatus;
    private String location;
    private String note;
    private String locationStatus;
    private String empName;
    private String deptName;
    private String positionName;
    private String createDate;

}
