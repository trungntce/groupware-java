package kr.co.hs.approval.cooperate.controller;

import kr.co.hs.approval.cooperate.service.ApprovalCooperateService;
import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.refer.service.ApprovalReferService;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.common.util.UrlUtils;
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
@RequestMapping("/approval/cooperate")
public class ApprovalCooperateController {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ApprovalCooperateService approvalCooperateService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalFileService approvalFileService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goList(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalCooperateService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalService.selectRequestApproveByEmpCd(searchDTO);
        searchDTO.setTotal(approvalService.countRequestApproveByEmpCd(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("approvalStatus", codeService.getCodeList("approval_status", LocaleContextHolder.getLocale().toString()));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        return "/approve/cooperate/list";
    }

    @TilesDynamic("base")
    @GetMapping("/detail.hs")
    public String goUpdate(Model model, ApprovalSearchDTO searchDTO){
        ApprovalSignLineSearchDTO signSearch = new ApprovalSignLineSearchDTO();
        signSearch.setApprovalId(searchDTO.getApprovalId());
        model.addAttribute("form", approvalService.selectOne(searchDTO));
        model.addAttribute("empSign", approvalSignLineService.selectList(signSearch));
        model.addAttribute("approvalFiles", approvalFileService.selectFileByApprovalId(new ApprovalFileSearchDTO(searchDTO.getApprovalId())));
        return "/approve/cooperate/detail";
    }

    @PostMapping("/update.hs")
    public String doUpdate(HttpServletRequest request, @ModelAttribute("form") ApprovalSignLineDTO approvalSignLineDTO){
        approvalCooperateService.update(request, approvalSignLineDTO);
        return "redirect:/approval/cooperate/list.hs";
    }
}
