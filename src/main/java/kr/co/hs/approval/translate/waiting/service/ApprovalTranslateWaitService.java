package kr.co.hs.approval.translate.waiting.service;

import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ApprovalTranslateWaitService {

    @Autowired
    private ApprovalTranslationService approvalTranslationService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private EmpService empService;

    @Autowired
    private NoticeService noticeService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        searchDTO.setListApprovalStatus(Arrays.asList(9, 11));
        return searchDTO;
    }

    public boolean translate(HttpServletRequest request, ApprovalTranslationDTO approvalTranslationDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalTranslationService.addTranslationApproval(empDTO, approvalTranslationDTO);
        ApprovalDTO approvalDTO = approvalService.selectOne(new ApprovalSearchDTO(approvalTranslationDTO.getApprovalId()));
        approvalDTO.setApprovalStatus(10);
        approvalService.update(approvalDTO);

        //Notice register
        EmpDTO empSearch = new EmpDTO();
        empSearch.setTranslationAdminYn("Y");
        List<EmpDTO> translators = empService.selectList(empSearch);
        List<Integer> idEmp = new ArrayList<>();
        translators.forEach((el) -> {
            idEmp.add(el.getEmpCd());
        });
        noticeService.register(new NoticeDTO(empDTO.getEmpCd(), approvalDTO.getApprovalId(), idEmp, "Xác nhận nội dung dịch: "+approvalDTO.getTitle(), "APPROVAL_TRANSLATE_CONFIRM"));

        return true;
    }
}
