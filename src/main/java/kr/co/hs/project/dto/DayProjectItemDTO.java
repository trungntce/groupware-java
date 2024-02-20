package kr.co.hs.project.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

@Data
public class DayProjectItemDTO {
    private int rownum;
    private int dpiId;
    private int dpId;
    private int pjId;
    private String langCd;
    private int empCd;
    private int deptChangeHistoryId;
    private int deptCd;
    private int positionCd;
    private String productName;
    private long price;
    private int ea;
    private long amount;
    private String checkStatus;
    private String imgPath;
    private String regDt;
    private String useYn;
    private String title;
    private String projectName;
    private String spentType;
    private long remainAmount;
    private String deptName;
    private String positionName;
    private String empName;
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
    String dayProjectItemStatusSql;
    String translationYnPara;
    String adminYnPara;

    String sEcho;
    String sColumns;
    List<DayProjectItemDTO> aaData;



}
