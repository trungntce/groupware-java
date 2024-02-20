package kr.co.hs.sboard.dto;

import lombok.Data;

@Data
public class SBoardDmtDTO {
    private int id;
    private String nameDmt;
    private int idBoardType;
    private int team;
    private int dept;
    private int company;
    private int general;
    private String createDate;
    private String str;
}
