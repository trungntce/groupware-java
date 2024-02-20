package kr.co.hs.approval.draft.controller;

import kr.co.hs.approval.draft.service.ApprovalDraftService;
import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalFileSearchDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.dto.ApprovalSignLineSearchDTO;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
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
@RequestMapping("/approval/draft")
public class ApprovalDraftController {

    @Autowired
    private ApprovalDraftService approvalDraftService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalFileService approvalFileService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goIndex(HttpServletRequest request, Model model, ApprovalSearchDTO searchDTO){
        searchDTO = approvalDraftService.initParam(request, searchDTO);
        List<ApprovalDTO> listApproval = approvalService.selectRequestApproveByEmpCd(searchDTO);
        searchDTO.setTotal(approvalService.countRequestApproveByEmpCd(searchDTO));
        model.addAttribute("list", listApproval);
        model.addAttribute("searchDTO", searchDTO);
        return "/approve/draft/list";
    }

    @TilesDynamic("base")
    @GetMapping("/update.hs")
    public String goUpdate(Model model, HttpServletRequest request, ApprovalSearchDTO searchDTO){
        ApprovalSignLineSearchDTO signSearch = new ApprovalSignLineSearchDTO();
        signSearch.setApprovalId(searchDTO.getApprovalId());
        model.addAttribute("form", approvalService.selectOne(searchDTO));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        model.addAttribute("empSign", approvalSignLineService.selectList(signSearch));
        model.addAttribute("approvalFiles", approvalFileService.selectFileByApprovalId(new ApprovalFileSearchDTO(searchDTO.getApprovalId())));
        return "/approve/draft/update";
    }

    @PostMapping("/update.hs")
    public String doUpdate(HttpServletRequest request, @ModelAttribute("form") ApprovalDTO approvalDTO){
        approvalDraftService.update(request, approvalDTO);
        if(approvalDTO.getApprovalStatus() == 9){
            return "redirect:/approval/agree/list.hs";
        }else{
            return "redirect:/approval/draft/list.hs";
        }
    }
}
