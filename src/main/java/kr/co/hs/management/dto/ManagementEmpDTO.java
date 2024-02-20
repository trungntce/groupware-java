package kr.co.hs.management.dto;

import lombok.Data;

@Data
public class ManagementEmpDTO {
    private int rownum;
    private int empCd;
    private int empParentCd;
    private int deptCd;
    private String deptName;
    private String empPw;
    private String empPwCheck;
    private int positionCd;
    private String positionName;
    private String selectLangCd;
    private String empId;
    private String empName;
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
}
