package kr.co.hs.attendace.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class AttendanceDTO  extends PageDTO {
    private int rownum;
    private int attendanceId;
    private int empCd;
    private int deptChangeHistoryId;
    private String dateCheck;
    private String checkinTime;
    private String checkoutTime;
    private String regIp;
    private String deptName;
    private String empName;

    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<AttendanceDTO> aaData;
}
