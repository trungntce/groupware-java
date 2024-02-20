package kr.co.hs.approval.translate.history.controller;

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
@RequestMapping("/approval/translate/history")
public class ApprovalTranslateHistoryController {

    @Autowired
    private ApprovalTranslateHistoryService approvalTranslateHistoryService;

    @GetMapping({"", "/list.hs"})
    public String goList(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalTranslateHistoryService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalTranslateHistoryService.selectList(searchDTO);
        searchDTO.setTotal(approvalTranslateHistoryService.count(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        return "/approve/translate/history/list";
    }
}
