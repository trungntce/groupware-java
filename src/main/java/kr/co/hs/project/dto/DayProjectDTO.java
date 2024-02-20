package kr.co.hs.project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DayProjectDTO {
    private int rownum;
    private int dpId;
    private int pjId;
    private String langCd;
    private int empCd;
    private int deptChangeHistoryId;
    private int deptCd;
    private int positionCd;
    private String spentType;
    private long advanceAmount;
    private long projectPrice;
    private long remainAmount;
    private String title;
    private String dayProjectStatus;
    private String regDt;
    private String useYn;
    private String projectName;
    private String deptName;
    private String positionName;
    private String empName;
    private int numOfItem;
    private MultipartFile file;
    private long differAmount;
    private String type;

    int iTotalRecords;
    int iTotalDisplayRecords;
    String txtSearch;
    int startRow;
    int recordsPerPage;
    String dateSql;
    String inputSearchSql;
    String str;
    String deptSearch;
    String translationStatus;
    String dayProjectStatusSql;
    String translationYnPara;
    String adminYnPara;

    String sEcho;
    String sColumns;
    List<DayProjectDTO> aaData;

}
