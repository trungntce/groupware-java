package kr.co.hs.approval.register.controller;

import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalFormSearchDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.register.service.ApprovalRegisterService;
import kr.co.hs.approval.service.ApprovalFormService;
import kr.co.hs.approval.service.ApprovalService;
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

@Controller
@RequestMapping("/approval/register")
public class ApprovalRegisterController {

    @Autowired
    private ApprovalFormService approvalFormService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private EmpService empService;

    @Autowired
    private ApprovalRegisterService approvalRegisterService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goIndex(Model model, ApprovalFormSearchDTO searchDTO){
        searchDTO.setTotal(approvalFormService.count(searchDTO));
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("list", approvalFormService.selectList(searchDTO));
        return "/approve/register/list";
    }

    @TilesDynamic("base")
    @GetMapping("/create.hs")
    public String goCreate(Model model, ApprovalFormSearchDTO searchDTO){
        model.addAttribute("form", new ApprovalDTO(approvalFormService.selectOne(searchDTO)));
        model.addAttribute("approvalRole", codeService.getCodeList("approval_role", LocaleContextHolder.getLocale().toString()));
        return "/approve/register/create";
    }

    @PostMapping("/create.hs")
    public String doCreate(HttpServletRequest request, @ModelAttribute("form") ApprovalDTO approvalDTO){
        approvalRegisterService.register(request, approvalDTO);
        if(approvalDTO.getApprovalStatus() == 9){
            return "redirect:/approval/agree/list.hs";
        }else{
            return "redirect:/approval/draft/list.hs";
        }
    }
}
