package kr.co.hs.approval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApprovalFormDTO {
    private Integer formId = null;
    private String formName = null;
    private String formContents = null;
    private String useYn = null;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate = null;
    private String createId = null;

}
