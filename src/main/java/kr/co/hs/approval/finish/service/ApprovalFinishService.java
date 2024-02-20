package kr.co.hs.approval.finish.service;

import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.deptcontrol.service.DeptControlService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ApprovalFinishService {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptControlService deptControlService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());

        //status = 5 (finished)
        searchDTO.setApprovalStatus(5);
        searchDTO.setMyApproval("Y");

        return searchDTO;
    }
}
