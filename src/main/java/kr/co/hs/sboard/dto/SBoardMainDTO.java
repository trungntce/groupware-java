package kr.co.hs.sboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SBoardMainDTO {
    private int id;
    private int idDmt;
    private String title;
    private String imgCover;
    private String content;
    private String link1;
    private String link2;
    private String attachFile;
    private String startDate;
    private String endDate;
    private int viewCount;
    private Integer idComment;
    private String createBy;
    private String createDate;
    private String modifyDate;
    private String useYn;

    //them
    private String nameDmt;
    private int idBoardType;
    private MultipartFile multipartCover;
    private MultipartFile multipartFile;
    private String noidung;
    private int idTest;


}
