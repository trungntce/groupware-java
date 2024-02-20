package kr.co.hs.deptcontrol.controller;

import kr.co.hs.deptcontrol.dto.DeptControlDTO;
import kr.co.hs.deptcontrol.dto.SearchDeptControlDTO;
import kr.co.hs.deptcontrol.service.DeptControlService;
import kr.co.hs.common.tiles.TilesDynamic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeptController {
    @Autowired
    private DeptControlService deptControlService;

    @TilesDynamic("base")
    @GetMapping("/deptcontrol/dept/list.hs")
    public String list(Model model, @ModelAttribute("search") SearchDeptControlDTO searchDeptControlDTO){
        System.out.println("this is dept list page");
        String langCd = LocaleContextHolder.getLocale().toString();
        if(searchDeptControlDTO.getType() !=null && searchDeptControlDTO.getType().equals("DEPT_NAME")){
            searchDeptControlDTO.setType("GET_DEPT_NAME(dept_cd, '"+langCd+"')");
        }else if(searchDeptControlDTO.getType() !=null && searchDeptControlDTO.getType().equals("DEPT_PARENT_NAME")){
            searchDeptControlDTO.setType("GET_DEPT_PARENT_NAME(dept_cd, '"+langCd+"')");
        }

        searchDeptControlDTO.setLangCd(langCd);
        searchDeptControlDTO.setTotal(deptControlService.count(searchDeptControlDTO));
        List<DeptControlDTO> lst = deptControlService.getDeptList(searchDeptControlDTO);
        model.addAttribute("getDeptList", lst);

        return "/hrga/dept/list";
    }

    @TilesDynamic("base")
    @GetMapping("/deptcontrol/dept/view.hs")
    public String view(Model model, @ModelAttribute("search") DeptControlDTO deptControlDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        deptControlDTO.setLangCd(langCd);

        model.addAttribute("getDeptView", deptControlService.getDeptView(deptControlDTO));

        return "/hrga/dept/view";
    }

    @TilesDynamic("base")
    @GetMapping("/deptcontrol/dept/write.hs")
    public String goWrite(Model model, @ModelAttribute("dept") DeptControlDTO deptControlDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        deptControlDTO.setLangCd(langCd);
        model.addAttribute("getDeptCd", deptControlDTO.getDeptCd());
        model.addAttribute("getDeptNameList", deptControlService.getDeptNameList());
        return "/hrga/dept/write";
    }

    @PostMapping("/deptcontrol/dept/write.hs")
    public String doWrite(Model model, @ModelAttribute("dept") DeptControlDTO deptControlDTO){
        deptControlService.deptSave(deptControlDTO);
        return "redirect:/deptcontrol/dept/list.hs";
    }

    @TilesDynamic("base")
    @GetMapping("/deptcontrol/dept/update.hs")
    public String goUpdate(Model model, @ModelAttribute("getDeptUpdate") DeptControlDTO deptControlDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        deptControlDTO.setLangCd(langCd);
        model.addAttribute("getDeptCd", deptControlDTO.getDeptCd());
        model.addAttribute("getDeptDTO", deptControlService.getDeptViewEdit(deptControlDTO));
        model.addAttribute("getDeptNameList", deptControlService.getDeptNameList());

        return ("/hrga/dept/update");
    }

    @PostMapping("/deptcontrol/dept/update.hs")
    public String doUpdate(Model model, @ModelAttribute("getDeptUpdate") DeptControlDTO deptControlDTO){
        deptControlService.deptUpdate(deptControlDTO);
        return "redirect:/deptcontrol/dept/list.hs";
    }
}
