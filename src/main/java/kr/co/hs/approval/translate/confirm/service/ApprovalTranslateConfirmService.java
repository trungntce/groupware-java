package kr.co.hs.approval.translate.confirm.service;

import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.translation.dto.TranslationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ApprovalTranslateConfirmService {

    @Autowired
    private ApprovalTranslationService approvalTranslationService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private EmpService empService;

    @Autowired
    private NoticeService noticeService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        searchDTO.setListApprovalStatus(Collections.singletonList(10));
        return searchDTO;
    }

    public boolean confirm(HttpServletRequest request, ApprovalTranslationDTO approvalTranslationDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalTranslationService.addTranslationApproval(empDTO, approvalTranslationDTO);

        ApprovalDTO approvalDTO = approvalService.selectOne(new ApprovalSearchDTO(approvalTranslationDTO.getApprovalId()));
        approvalDTO.setApprovalStatus(1);
        approvalService.update(approvalDTO);

        List<ApprovalSignLineDTO> approvalSignLines = approvalSignLineService.selectList(new ApprovalSignLineSearchDTO(approvalTranslationDTO.getApprovalId()));
        if(approvalSignLines.size() > 0){
            approvalSignLines.get(0).setApprovalStatus(1);
            approvalSignLineService.updateOne(approvalSignLines.get(0));

            noticeService.register(new NoticeDTO(approvalDTO.getEmpCd(), approvalTranslationDTO.getApprovalId(), Collections.singletonList(approvalSignLines.get(0).getEmpCd()), "Xác nhận đơn phê duyệt: "+approvalDTO.getTitle(), "APPROVAL_CONFIRM"));
        }
        return true;
    }

    public boolean reject(HttpServletRequest request, ApprovalSearchDTO approvalSearchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        if("Y".equals(empDTO.getTranslationAdminYn())){
            ApprovalDTO approvalDTO = approvalService.selectOne(new ApprovalSearchDTO(approvalSearchDTO.getApprovalId()));
            approvalDTO.setApprovalStatus(11);
            approvalService.update(approvalDTO);

            List<TranslationDTO> translations = approvalTranslationService.selectDataTranslateByIdApproval(new ApprovalTranslationDTO(approvalSearchDTO.getApprovalId()));
            Integer translateEmpCd = 0;
            for(int i = translations.size()-1; i > 0; i--){
                if(translations.get(i).getEmpCd() != empDTO.getEmpCd()){
                    translateEmpCd = translations.get(i).getEmpCd();
                    break;
                }
            }
            noticeService.register(new NoticeDTO(empDTO.getEmpCd(), approvalSearchDTO.getApprovalId(), Collections.singletonList(translateEmpCd), "Kiểm tra lại bản dịch: "+approvalDTO.getTitle(), "APPROVAL_TRANSLATE"));
        }
        return true;
    }
}
