package kr.co.hs.management.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
public class ManagementOrgSearchDTO extends PageDTO {
    private int deptCd;
    private String type;
    private String keyword;
    private int deptLevel;
    private String useYn;
    private String searchDetail;
    private String langCd;
}
