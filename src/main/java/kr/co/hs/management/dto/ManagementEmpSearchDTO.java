package kr.co.hs.management.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class ManagementEmpSearchDTO extends PageDTO {
    private String type;
    private String keyword;
    private int empCd;
    private int deptCd;
    private int deptName;
    private int positionCd;
    private String positionName;
    private String empPw;
    private String empPwCheck;
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
    private String langCd;
    private String searchDetail;
}


