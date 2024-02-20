package kr.co.hs.approval.wait.process.service;

import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.deptcontrol.service.DeptControlService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Service
public class ApprovalWaitProcessService {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptControlService deptControlService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());
        searchDTO.setListStatus(Collections.singletonList(3));
        searchDTO.setApprovalRole(1);
        searchDTO.setListApprovalStatus(Arrays.asList(1,2));
        return searchDTO;
    }
}
