package kr.co.hs.translation.dto;

import lombok.Data;

@Data
public class TranslationDTO {
    private String	useYn;
    private int	translationId;
    private String	langCd;
    private int	empCd;
    private int	deptChangeHistoryId;
    private int	deptCd;
    private int	positionCd;
    private String	translationStatus;
    private String	title;
    private String	contents;
    private String	regDt;

    public void setRegDt(String regDt) {
        this.regDt = regDt.substring(0,19);
    }

    private int	translationScore;
    private String	selectYn;

    //them
    private String langName;
    private String empName;
    private String hisDeptName;
    private String deptName;
    private String poName;
    private String type;
    private String dayWorkCommentId;
    private String dayWorkCommentTranslation;
    private int rownum;

    private Integer signLineId;
}
