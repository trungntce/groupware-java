package kr.co.hs.common.dto;

import lombok.Data;

@Data
public class NoticeReceiverDTO {
    private Integer eventNoticeReceiverId;
    private Integer eventNoticeId;
    private Integer empCd;
    private String status = "N";
    private String time;
    private String useYn = "Y";

    public NoticeReceiverDTO(){

    }

    public NoticeReceiverDTO(Integer eventNoticeId, Integer empCd) {
        this.eventNoticeId = eventNoticeId;
        this.empCd = empCd;
    }
}
