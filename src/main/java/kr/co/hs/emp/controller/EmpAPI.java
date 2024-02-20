package kr.co.hs.emp.controller;

import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/API")
@RestController
public class EmpAPI {
    @Autowired
    EmpService empService;

    @GetMapping(value = "/selectEmpWithDept", produces = "application/json; charset=UTF-8")
    public Object selectEmpWithDept(){
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("raw", empService.selectLstEmpWithDept());
        result.put("result", empService.selectEmpWithDept());
        result.put("resultAccounting", empService.selectEmpWithDeptAccounting());
        return result;
    }
}
