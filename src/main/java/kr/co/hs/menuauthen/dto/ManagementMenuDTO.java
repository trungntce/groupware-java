package kr.co.hs.menuauthen.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class ManagementMenuDTO {
    private int rownum;
    private int roleCd;
    private String roleName;
    private int menuAuthId;
    private int empCd;
    private int menuCd;
    private List<Integer> notMenus = new ArrayList<>();
    private String menuStr;
    private String empName;
    private String menuName;
    private String useYn;
    private String langCd;
    private String positionCd;
    private String positionName;
    private String translationYn = "Y";
    private String translationAdminYn;

    public void setTranslationYn(String translationYn) {
        this.translationYn = translationYn;
    }
}
