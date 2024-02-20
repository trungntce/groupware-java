package kr.co.hs.emp.dto;

import kr.co.hs.attendace.dto.AttendanceDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class EmpDTO implements Serializable {
    private int rownum;
    private static final long serialVersionUID = -3916134908819397903L;
    private Integer empCd;
    private Integer empParentCd;
    private String empId;
    private String empName;
    private String empPw = null;
    private Integer deptCd;
    private Integer deptParentCd;
    private String deptName;
    private Integer positionCd;
    private String positionName;
    private String selectLangCd;
    private String LangCd;
    private String addr;
    private String addrDetail;
    private String zipCd;
    private String tel;
    private String phone;
    private String idNum;
    private String birthDay;
    private String mail;
    private String zaloId;
    private String translationYn;
    private String adminYn;
    private int gubun;
    private int status;
    private String enterDate;
    private String memo;
    private String useYn;
    private int deptChangeHistoryId;
    private String locationTran;
    private String locationDate;
    private String locationStatus;
    private String styleStrOpen;
    private String empStatus;
    private String empType;
    private String empDeptName;
    private String accountingYn;
    private String translationAdminYn;

    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<EmpDTO> aaData;
}
