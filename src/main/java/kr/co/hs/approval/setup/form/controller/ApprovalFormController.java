package kr.co.hs.approval.setup.form.controller;

import kr.co.hs.approval.dto.ApprovalFormDTO;
import kr.co.hs.approval.dto.ApprovalFormSearchDTO;
import kr.co.hs.approval.service.ApprovalFormService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/approval/setup/form")
public class ApprovalFormController {

    @Autowired
    private ApprovalFormService approvalFormService;

    @Autowired
    private EmpService empService;

    @TilesDynamic("base")
    @GetMapping({"", "/list.hs"})
    public String goList(Model model, @ModelAttribute("searchDTO") ApprovalFormSearchDTO searchDTO){
        searchDTO.setTotal(approvalFormService.count(searchDTO));
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("list", approvalFormService.selectList(searchDTO));
        return "/approve/setup/form/list";
    }

    @TilesDynamic("base")
    @GetMapping("/add.hs")
    public String goAdd(Model model){
        model.addAttribute("myform", new ApprovalFormDTO());
        return "/approve/setup/form/add";
    }

    @PostMapping("/add.hs")
    public String doAdd(HttpServletRequest request, @ModelAttribute("myform") ApprovalFormDTO approvalFormDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalFormDTO.setCreateId(empDTO.getEmpId());
        approvalFormService.insertApprovalForm(approvalFormDTO);
        return "redirect:/approval/setup/form/list.hs";
    }

    @TilesDynamic("base")
    @GetMapping("/edit.hs")
    public String goEdit(Model model, ApprovalFormSearchDTO searchDTO){
        model.addAttribute("myform", approvalFormService.selectOne(searchDTO));
        return "/approve/setup/form/edit";
    }

    @PostMapping("/edit.hs")
    public String doEdit(HttpServletRequest request, @ModelAttribute("myform") ApprovalFormDTO approvalFormDTO){
        approvalFormService.updateApprovalForm(approvalFormDTO);
        return "redirect:/approval/setup/form/list.hs";
    }

}
