package kr.co.hs.menuauthen.controller;

import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.management.dto.ManagementEmpDTO;
import kr.co.hs.management.dto.ManagementEmpSearchDTO;
import kr.co.hs.menuauthen.dto.ManagementMenuDTO;
import kr.co.hs.management.service.ManagementEmpService;
import kr.co.hs.menuauthen.service.ManagementMenuService;
import kr.co.hs.position.dto.PositionDTO;
import kr.co.hs.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ManagementAuthorController {

    final ManagementEmpService managementEmpService;
    final ManagementMenuService managementMenuService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private EmpService empService;
    @Autowired
    private PositionService positionService;

    @TilesDynamic("base")
    @GetMapping("/management/author/list.hs")
    public String list(Model model, @ModelAttribute("author") ManagementEmpSearchDTO managementEmpSearchDTO, HttpServletRequest request) {
        System.out.println("hello author page");
        String langCd = LocaleContextHolder.getLocale().toString();
        managementEmpSearchDTO.setLangCd(langCd);
        EmpDTO emp = empService.getSessionUserLogin(request);

        model.addAttribute("typeCodeList", positionService.getPositionList(langCd));

        model.addAttribute("getMenuRoleList", managementMenuService.getMenuRoleList());
        managementEmpSearchDTO.setTotal(managementEmpService.count(managementEmpSearchDTO));
        managementEmpSearchDTO.setUseYn("Y");
        managementEmpSearchDTO.setEmpCd(emp.getEmpCd());
        model.addAttribute("authorEMPDTOList", managementEmpService.getEmpList(managementEmpSearchDTO));

        return "management/author/list";
    }

    @PostMapping("/management/author/update.hs")
    public String doUpdate(Model model,@ModelAttribute("author") ManagementEmpSearchDTO _managementEmpSearchDTO,@RequestBody ManagementEmpDTO managementEmpDTO){

        managementMenuService.menuAuthEdit(managementEmpDTO);
        return "management/author/list";
    }

    @TilesDynamic("base")
    @GetMapping("/management/author/custom.hs")
    public String menuCustomList (Model model, @ModelAttribute("custom") ManagementMenuDTO managementMenuDTO){
        System.out.println("Hello custom page for menu page");

        model.addAttribute("menuCustomList", managementMenuService.getRoleList(managementMenuDTO));
        System.out.println("____: "+ managementMenuService.getRoleList(managementMenuDTO));
        return "management/author/custom";
    }

    @TilesDynamic("base")
    @GetMapping("/management/author/view.hs")
    public String view (Model model, HttpServletRequest request, @ModelAttribute("menuList") ManagementMenuDTO managementMenuDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        String langCd = LocaleContextHolder.getLocale().toString();
        managementMenuDTO.setLangCd(langCd);
        managementMenuDTO.setTranslationYn(empDTO.getTranslationYn());
        managementMenuDTO.setTranslationAdminYn(empDTO.getTranslationAdminYn());
        List<ManagementMenuDTO> lst = managementMenuService.getNameMenuList(managementMenuDTO);
        model.addAttribute("menuList", lst);
        model.addAttribute("emcd",lst.get(0).getEmpCd());
        return "management/author/view";
    }

    @PostMapping("/management/author/customUpdate.hs")
    public String doCustomUpdate(Model model,@RequestBody ManagementMenuDTO managementMenuDTO){
        System.out.println("hello Custom Update Function");
        managementMenuService.customeUpdate(managementMenuDTO);

        return "management/author/view";
    }

    @TilesDynamic("base")
    @GetMapping("/management/author/position.hs")
    public String positionView (Model model, PositionDTO positionDTO){

        return "management/author/position";
    }

    @ResponseBody
    @PostMapping(value = "/management/author/transaction", produces = "application/json; charset=UTF-8")
    public int xoaW(String lstChecked) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {

            str += " " + arrOfStr[i].toString() + " ,";

        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);
        System.out.println("DXD: -> "+ param);
        int Del = managementMenuService.updateTransRolse(param);
        return 1;
    }
}
