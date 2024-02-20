package kr.co.hs.menu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class MenuDTO implements Serializable {

    private int menuCd;
    private int menuParentCd;
    private String menuType;
    private String menuName;
    private String menuIcon;
    private String menuUrl;
    private String menuUrlId;
    private String collapseYn;
    private int menuLevel;
    private int sort;
    private String useYn;
}
