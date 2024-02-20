package kr.co.hs.approval.setup.signature.controller;

import kr.co.hs.approval.dto.ApprovalSettingsDTO;
import kr.co.hs.approval.dto.ApprovalSettingsSearchDTO;
import kr.co.hs.approval.service.ApprovalSettingService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.common.util.UrlUtils;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/approval/setup/signature")
public class ApprovalSignatureController {

    @Autowired
    private EmpService empService;

    @Autowired
    private ApprovalSettingService approvalSettingService;

    @TilesDynamic("base")
    @GetMapping({"", "/index.hs"})
    public String goIndex(Model model, HttpServletRequest request, ApprovalSettingsSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setCreateId(empDTO.getEmpId());
        model.addAttribute("list", approvalSettingService.selectList(searchDTO));
        searchDTO.setUseYn("Y");
        model.addAttribute("enableForm", approvalSettingService.selectOneByUserEnable(searchDTO));
        return "/approve/setup/signature/index";
    }

    @PostMapping({"", "/index.hs"})
    public String doIndex(HttpServletRequest request, String base64){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalSettingService.createSignatureSource(empDTO, base64);
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @PostMapping("/enable.hs")
    public String doEnable(HttpServletRequest request, ApprovalSettingsDTO approvalSettingsDTO){
        approvalSettingService.enableSignSettings(request, approvalSettingsDTO);
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");

    }

    @GetMapping("/remove.hs")
    public String doRemove(HttpServletRequest request, ApprovalSettingsDTO approvalSettingsDTO){
        approvalSettingService.removeSignSettings(request, approvalSettingsDTO);
        return UrlUtils.getPreviousPageByRequest(request).orElse("/");

    }
}
