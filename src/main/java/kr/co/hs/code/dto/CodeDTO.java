package kr.co.hs.code.dto;

import lombok.Data;

@Data
public class CodeDTO {
    private Integer cCodeId;
    private String langCd;
    private String cCode;
    private String cCodeName;
    private String cCodeValue;
    private String sort;
    private String useYn;
}