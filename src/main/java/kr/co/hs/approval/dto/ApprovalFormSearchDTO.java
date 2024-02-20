package kr.co.hs.approval.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApprovalFormSearchDTO extends PageDTO {
    private Integer formId = null;
    private String formName = null;
    private String formContents = null;
    private String useYn = null;
    private Date createDate = null;
    private String createId = null;
    private String dateFlag = null;
    private String startDate = null;
    private String endDate = null;
    private String keywordType = null;
    private String keyword = null;
    private String orderByColumn = null;
    private String orderByType = null;

}
