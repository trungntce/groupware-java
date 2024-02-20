package kr.co.hs.approval.wait.process.controller;

import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalFileSearchDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.dto.ApprovalSignLineSearchDTO;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.approval.wait.pending.service.ApprovalWaitPendingService;
import kr.co.hs.approval.wait.process.service.ApprovalWaitProcessService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.lang.service.LangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/approval/wait/process")
public class ApprovalWaitProcessController {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ApprovalWaitProcessService approvalWaitProcessService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private EmpService empService;

    @Autowired
    private ApprovalTranslationService approvalTranslationService;

    @Autowired
    private LangService langService;

    @Autowired
    private ApprovalFileService approvalFileService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goList(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalWaitProcessService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalService.selectRequestApproveByEmpCd(searchDTO);
        searchDTO.setTotal(approvalService.countRequestApproveByEmpCd(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("approvalStatus", codeService.getCodeList("approval_status", LocaleContextHolder.getLocale().toString()));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        return "/approve/wait/process/list";
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
        return "/approve/wait/process/detail";
    }
}
