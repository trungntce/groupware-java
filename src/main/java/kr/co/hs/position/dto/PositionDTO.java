package kr.co.hs.position.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class PositionDTO {
    private String langCd;
    private int positionCd;
    private String positionName;
    private String positionLevel;
    private String useYn;
}
