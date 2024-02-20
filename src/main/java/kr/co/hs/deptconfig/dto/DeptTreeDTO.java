package kr.co.hs.deptconfig.dto;

import lombok.Data;

@Data
public class DeptTreeDTO {
    private Integer id;
    private Integer parent;
    private String deptName;
}
