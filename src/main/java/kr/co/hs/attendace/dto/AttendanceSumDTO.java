package kr.co.hs.attendace.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class AttendanceSumDTO extends PageDTO {
    private int rownum;
    private int empCd;
    private String empName;
    private String deptName;
    private String positionName;
    private int ngaycongty;
    private int ngaydilam;
    private int disom;
    private int dungio;
    private int dimuon;
    private int vesom;
    private int vang;

    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<AttendanceSumDTO> aaData;

}
