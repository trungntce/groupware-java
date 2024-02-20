package kr.co.hs.approval.cooperate.service;

import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.dto.ApprovalSignLineDTO;
import kr.co.hs.approval.dto.ApprovalSignLineSearchDTO;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.deptcontrol.service.DeptControlService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ApprovalCooperateService {

    @Autowired
    private EmpService empService;

    @Autowired
    private DeptControlService deptControlService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());
        searchDTO.setApprovalRole(3);
        searchDTO.setListApprovalStatus(Arrays.asList(1,2));
        searchDTO.setListStatus(Collections.singletonList(1));
        return searchDTO;
    }

    public boolean update(HttpServletRequest request, ApprovalSignLineDTO approvalSignLineDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);

        approvalSignLineDTO.setApprovalType(1);
        approvalSignLineDTO.setMemo("");
        approvalSignLineDTO.setEmpCd(empDTO.getEmpCd());
        approvalSignLineService.updateApprovalStatus(empDTO, approvalSignLineDTO);

        /*ApprovalSignLineSearchDTO approvalSignLineSearch = new ApprovalSignLineSearchDTO();
        approvalSignLineSearch.setApprovalId(approvalSignLineDTO.getApprovalId());
        approvalSignLineSearch.setLstApprovalRole(Arrays.asList(1,3));
        approvalSignLineSearch.setListStatus(Collections.singletonList(2));
        List<ApprovalSignLineDTO> approvalSignLines = approvalSignLineService.selectList(approvalSignLineSearch);
        if(approvalSignLines.size() > 0){
            approvalSignLines.get(0).setApprovalStatus(1);
            approvalSignLineService.updateOne(approvalSignLines.get(0));
        }*/
        return true;
    }
}
