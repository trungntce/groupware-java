package kr.co.hs.approval.translate.confirm.controller;

import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.approval.translate.confirm.service.ApprovalTranslateConfirmService;
import kr.co.hs.approval.translate.history.service.ApprovalTranslateHistoryService;
import kr.co.hs.code.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/approval/translate/confirm")
public class ApprovalTranslateConfirmController {

    @Autowired
    private ApprovalTranslateConfirmService approvalTranslateConfirmService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalFileService approvalFileService;

    @Autowired
    private ApprovalTranslationService approvalTranslationService;

    @Autowired
    private ApprovalTranslateHistoryService approvalTranslateHistoryService;

    @GetMapping({"", "/list.hs"})
    public String goList(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalTranslateConfirmService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalService.selectRequestApproveByEmpCd(searchDTO);
        searchDTO.setTotal(approvalService.countRequestApproveByEmpCd(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("approvalStatus", codeService.getCodeList("approval_status", LocaleContextHolder.getLocale().toString()));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        return "/approve/translate/confirm/list";
    }

    @GetMapping("/update.hs")
    public String goUpdate(Model model, ApprovalSearchDTO searchDTO){
        ApprovalSignLineSearchDTO signSearch = new ApprovalSignLineSearchDTO();
        signSearch.setApprovalId(searchDTO.getApprovalId());
        model.addAttribute("form", approvalService.selectOne(searchDTO));
        model.addAttribute("empSign", approvalSignLineService.selectList(signSearch));
        model.addAttribute("approvalFiles", approvalFileService.selectFileByApprovalId(new ApprovalFileSearchDTO(searchDTO.getApprovalId())));
        model.addAttribute("translateHistory", approvalTranslationService.selectDataTranslateByIdApproval(new ApprovalTranslationDTO(searchDTO.getApprovalId())));
        return "/approve/translate/confirm/update";
    }

    @PostMapping("/update.hs")
    public String doUpdate(HttpServletRequest request, @ModelAttribute("form") ApprovalTranslationDTO approvalTranslationDTO){
        approvalTranslateConfirmService.confirm(request, approvalTranslationDTO);
        return "redirect:/approval/translate/confirm/list.hs";
    }

    @GetMapping("/reject.hs")
    public String doReject(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        approvalTranslateConfirmService.reject(request, searchDTO);
        return "redirect:/approval/translate/confirm/list.hs";
    }
}
