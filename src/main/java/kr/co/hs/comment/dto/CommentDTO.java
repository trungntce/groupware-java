package kr.co.hs.comment.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer rownum;
    private Integer dayWorkCommentId;
    private Integer dayWorkId;
    private Integer empCd;
    private String empName;
    private Integer deptChangeHistoryId;
    private Integer deptCd;
    private Integer positionCd;
    private String langCd;
    private String contents;
    private String regDt;
    private String useYn;;
    private String langName;
    private String cmtTranslation;
    private String type;

    public void setRegDt(String regDt) {
        this.regDt = regDt.substring(0,19);
    }
}
