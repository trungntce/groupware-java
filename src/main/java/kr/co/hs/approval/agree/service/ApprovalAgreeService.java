package kr.co.hs.approval.agree.service;

import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
public class ApprovalAgreeService {

    @Autowired
    private EmpService empService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());
        searchDTO.setListStatus(Arrays.asList(1,2));
        searchDTO.setMyApproval("Y");
        return searchDTO;
    }

}
