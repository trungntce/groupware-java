package kr.co.hs.sboard.dto;

import lombok.Data;

@Data
public class SBoardCommentDTO {
    private int id;
    private int idEmp;
    private int idBoardMain;
    private String contentComment;
    private String createDate;
    private String modifyDate;

//    them
    private String empName;


}
