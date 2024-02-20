package kr.co.hs.menu.dto;

import kr.co.hs.common.dto.PageDTO;
import lombok.Data;

@Data
public class MenuSearchDTO extends PageDTO {

    Integer empCd = null;
    Integer menuLevel = null;
}
