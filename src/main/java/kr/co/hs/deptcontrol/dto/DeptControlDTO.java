package kr.co.hs.deptcontrol.dto;

import lombok.Data;

@Data
public class DeptControlDTO {
        private int rownum;
        private int deptCd;
        private int deptParentCd;
        private int deptLevel;
        private String collapseYn;
        private String langCd;
        private int sort;
        private String deptName;
        private String useYn;
        private String deptParentName;
        private String empName;
        private Integer positionCd;
        private String positionName;
        private String publicCd;
        private String publicParentCd;
        private String subStr;
        private String positionLevel;
}