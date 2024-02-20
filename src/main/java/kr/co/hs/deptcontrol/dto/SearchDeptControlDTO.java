package kr.co.hs.deptcontrol.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;

import java.util.List;

@Data
public class SearchDeptControlDTO extends PageDTO {
    private int rownum;
    private int deptCd;
    private String type;
    private int sort;
    private String keyword;
    private int deptLevel;
    private String useYn;
    private String searchDetail;
    private String langCd;
    private String deptName;
    private List<Integer> listParentCd;
}