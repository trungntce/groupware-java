package kr.co.hs.board.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class BoardControlDTO extends PageDTO {
    private String notice;
    private Integer rownum;
    private Integer boardId;
    private String boardTypeCd;
    private Integer boardParentId;
    private Integer deptChangeHistoryId;
    private Integer deptCd;
    private Integer positionCd;
    private String deptName;
    private String positionName;
    private Integer empCd;
    private String empName;
    private String title;
    private String contents;
    private String regDt;
    private String notiYn;
    private String notiStartDt;
    private String notiEndDt;
    private String useYn;
    private String keyword;
    private String type;
    private String langName;
    private String boardKindCd;
    private String idNum;
    private String adminYn;
    private String translationYn;
    private String translationStatus;

    public void setRegDt(String regDt) {
        this.regDt = regDt.substring(0,19);;
    }

    String txtSearch;
    int startRow;
    int recordsPerPage;
    String dateSql;
    String deptSearch;
    String inputSearchSql;
    String str;

    int iTotalRecords;
    int iTotalDisplayRecords;
    String sEcho;
    String sColumns;
    List<BoardControlDTO> aaData;
}

