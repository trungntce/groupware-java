package kr.co.hs.approval.refer.service;

import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.dto.ApprovalSignLineDTO;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.deptcontrol.service.DeptControlService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ApprovalReferService {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptControlService deptControlService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());
        searchDTO.setApprovalRole(2);
        return searchDTO;
    }

    public boolean read(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);

        ApprovalSignLineDTO approvalSignLineDTO = new ApprovalSignLineDTO();
        approvalSignLineDTO.setApprovalId(searchDTO.getApprovalId());
        approvalSignLineDTO.setEmpCd(empDTO.getEmpCd());
        approvalSignLineDTO.setMemo("");
        approvalSignLineDTO.setApprovalType(1);
        approvalSignLineDTO.setApprovalStatus(8);
        approvalSignLineService.sign(approvalSignLineDTO);
        return true;
    }
}
