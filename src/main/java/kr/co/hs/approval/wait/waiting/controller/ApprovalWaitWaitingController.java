package kr.co.hs.approval.wait.waiting.controller;

import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.approval.wait.waiting.service.ApprovalWaitWaitingService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.common.util.UrlUtils;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.lang.service.LangService;
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
@RequestMapping("/approval/wait/waiting")
public class ApprovalWaitWaitingController {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ApprovalWaitWaitingService approvalWaitWaitingService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private LangService langService;

    @Autowired
    private ApprovalFileService approvalFileService;

    @Autowired
    private EmpService empService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goList(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalWaitWaitingService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalService.selectRequestApproveByEmpCd(searchDTO);
        searchDTO.setTotal(approvalService.countRequestApproveByEmpCd(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("approvalStatus", codeService.getCodeList("approval_status", LocaleContextHolder.getLocale().toString()));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        return "/approve/wait/waiting/list";
    }

    @TilesDynamic("base")
    @GetMapping("/detail.hs")
    public String goDetail(Model model, ApprovalSearchDTO searchDTO){
        ApprovalSignLineSearchDTO signSearch = new ApprovalSignLineSearchDTO();
        signSearch.setApprovalId(searchDTO.getApprovalId());
        model.addAttribute("form", approvalService.selectOne(searchDTO));
        model.addAttribute("empSign", approvalSignLineService.selectList(signSearch));
        model.addAttribute("lstLang", langService.getLangList());
        model.addAttribute("approvalFiles", approvalFileService.selectFileByApprovalId(new ApprovalFileSearchDTO(searchDTO.getApprovalId())));
        return "/approve/wait/waiting/detail";
    }

    @PostMapping("/update.hs")
    public String doUpdate(HttpServletRequest request, @ModelAttribute("form") ApprovalSignLineDTO approvalSignLineDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalSignLineService.updateApprovalStatus(empDTO, approvalSignLineDTO);
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }
}
