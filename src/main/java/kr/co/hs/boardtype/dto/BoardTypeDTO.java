package kr.co.hs.boardtype.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class BoardTypeDTO  extends PageDTO {
    private String boardTypeCd;
    private String boardKindCd;
    private int sort;
    private String imageYn;
    private String attachYn;
    private String replyYn;
    private String useYn;


}
