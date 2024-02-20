package kr.co.hs.project.controller;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Mod;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.CommonSearch;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.common.util.CommonUtil;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.project.dto.DayProjectDTO;
import kr.co.hs.project.dto.DayProjectItemDTO;
import kr.co.hs.project.dto.InfoDTO;
import kr.co.hs.project.dto.ProjectMainDTO;
import kr.co.hs.project.service.ProjectService;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


@Controller
@RequiredArgsConstructor
public class ProjectController {

    @Autowired
    EmpService empService;

    @Autowired
    TranslationService translationService;

    @Autowired
    DeptService deptService;

    @Autowired
    ProjectService projectService;

    @Autowired
    CommonSearch commonSearch;

    @TilesDynamic("base")
    @GetMapping("/project/project/list.hs")
    public String projectList(Model model ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO deptSearchDTO = new DeptSearchDTO();
        if(empDTO.getAccountingYn().equals("Y")){
            deptSearchDTO.setDeptCd(empDTO.getDeptCd());
            deptSearchDTO.setLangCd(LocaleContextHolder.getLocale().toString());
            deptSearchDTO.setTranslationSpecific("Accounting");
            deptSearchDTO.setEmpCd(empDTO.getEmpCd());
            deptSearchDTO.setAdminSpecitfic(empDTO.getAdminYn());
        } else {
            deptSearchDTO = commonSearch.deptSearchDTO();
        }

        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));
        model.addAttribute("empDTO", empDTO);
        model.addAttribute("empCd", empDTO.getEmpCd());
        model.addAttribute("translationYn", empDTO.getTranslationYn());
        model.addAttribute("approvalPermision", projectService.checkExitEmpProjectAuth());
        model.addAttribute("timeNow", java.time.LocalDate.now().toString());
        return "/project/project/list";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/dayproject.hs")
    public String dayProject(Model model,@RequestParam(name = "pjId") int pjId,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request){
        DeptSearchDTO deptSearchDTO = commonSearch.deptSearchDTO();
        request.setAttribute("countDefault",projectService.countDefault(pjId));
        request.setAttribute("pjId",pjId);
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));
        model.addAttribute("infoProject",projectService.getInfoProject(pjId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());
        model.addAttribute("translationYn", empDTO.getTranslationYn());
        //auth
        model.addAttribute("projectLeader", projectService.getEmpProjectLeaderCD(pjId));
        model.addAttribute("accountingLeader", projectService.getEmpAccountingLeaderCD(pjId));
        model.addAttribute("projectMainDTO", projectService.selectOneMainProject(pjId));
        model.addAttribute("timeNow", java.time.LocalDate.now().toString());
        model.addAttribute("emp", empDTO);
        ProjectMainDTO projectMainDTO = projectService.selectDeltailProjectId(pjId);
        model.addAttribute("nameProject", projectService.unescapeHtml3(projectMainDTO.getTitle()).replace("\n", " "));
        model.addAttribute("leaderProjectname", projectMainDTO.getLeaderProjectName());
        model.addAttribute("leaderAccountingName", projectMainDTO.getLeaderAccountingName());

        return "/project/dayproject/dayproject";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/add.hs")
    public String projectAdd(Model model, HttpServletRequest request){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        model.addAttribute("empInfor", empService.selectEmpDeptName(empDTO.getEmpCd()));
        model.addAttribute("empDTO", empDTO);
        model.addAttribute("addFrom", 1);
        model.addAttribute("langCd",LocaleContextHolder.getLocale().toString());
        return "/project/project/add";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/edit.hs")
    public String projectEdit(Model model, @RequestParam(name = "id") int id, HttpServletRequest request){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        model.addAttribute("empInfor", empService.selectEmpDeptName(empDTO.getEmpCd()));
        model.addAttribute("empDTO", empDTO);
        model.addAttribute("projectMainDTO", projectService.selectOneMainProject(id));
        return "/project/project/edit";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/dayProjectAdd.hs")
    public String dayProjectAdd(Model model, @RequestParam(name="pjId") int pjId){
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("lstInfoProjectByID",projectService.getInfoProjectByID(pjId));
        model.addAttribute("pjId",pjId);
        model.addAttribute("langCd",langCd);
        return "/project/dayproject/addspent";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/dayProjectAddDeposit.hs")
    public String dayProjectAddDeposit(Model model, @RequestParam(name="pjId") int pjId){
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("lstInfoProjectByID",projectService.getInfoProjectByID(pjId));
        model.addAttribute("pjId",pjId);
        model.addAttribute("langCd",langCd);
        return "/project/dayproject/deposit";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/dayProjectEditSpent.hs")
    public String dayProjectEdit(Model model, @RequestParam(name="pjId") int pjId,@RequestParam(name="dpId") int dpId){
        DayProjectDTO dayProjectDTO= new DayProjectDTO();
        dayProjectDTO.setDpId(dpId);
        model.addAttribute("lst",projectService.selectInfoDayProjectByID(dayProjectDTO));
        model.addAttribute("dpId",dpId);
        model.addAttribute("pjId",pjId);
        return "/project/dayproject/editspent";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/dayProjectEditDeposit.hs")
    public String dayProjectEditDeposit(Model model, @RequestParam(name="pjId") int pjId,@RequestParam(name="dpId") int dpId){
        DayProjectDTO dayProjectDTO= new DayProjectDTO();
        dayProjectDTO.setDpId(dpId);
        model.addAttribute("lst",projectService.selectInfoDayProjectByID(dayProjectDTO));
        model.addAttribute("dpId",dpId);
        model.addAttribute("pjId",pjId);
        return "/project/dayproject/editdeposit";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/detail.hs")
    public String detail(Model model,@RequestParam(name = "pjId") int pjId,@RequestParam(name = "dpId") int dpId,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request){
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("dpId",dpId);
        model.addAttribute("pjId",pjId);
        DeptSearchDTO deptSearchDTO = commonSearch.deptSearchDTO();
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));
        model.addAttribute("infoProject",projectService.getInfoProject(pjId));
        DayProjectDTO dayProjectDTO=new DayProjectDTO();
        dayProjectDTO.setDpId(dpId);
        model.addAttribute("InfoDayProjectByID",projectService.selectInfoDayProjectByID(dayProjectDTO));

        String dateInfo=projectService.selectInfoDayProjectByID(dayProjectDTO).getRegDt().split(" ")[0];
        String currentDate=java.time.LocalDate.now().toString();
        String disable = "";
        if (!dateInfo.equals(currentDate))
        {
            disable = " disabled";
        }
        model.addAttribute("disable",disable);

        model.addAttribute("sumMoneyItem",projectService.sumMoneyItem(dpId));
        model.addAttribute("lstFile",projectService.selectListFile(dpId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());
        model.addAttribute("translationYn", empDTO.getTranslationYn());

        //auth
        model.addAttribute("projectLeader", projectService.getEmpProjectLeaderCD(pjId));
        model.addAttribute("accountingLeader", projectService.getEmpAccountingLeaderCD(pjId));
        model.addAttribute("projectMainDTO", projectService.selectOneMainProject(pjId));
        ProjectMainDTO projectMainDTO = projectService.selectDeltailProjectId(pjId);
        DayProjectDTO dayProjectDTO1 = projectService.selectDetailDayProjectExcelExport(dpId);
        model.addAttribute("dayProjectDTO1RegDate", dayProjectDTO1.getRegDt());
        model.addAttribute("dayProjectDTO1Title", projectService.unescapeHtml3(dayProjectDTO1.getTitle()).replace("\n", " "));

        model.addAttribute("projectMainDTOProject", projectMainDTO.getLeaderProjectName());
        model.addAttribute("projectMainDTOAccounting", projectMainDTO.getLeaderAccountingName());
        model.addAttribute("emp", empDTO);

        return "/project/detailproject/detail";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/detailProjectAdd.hs")
    public String detailProjectAdd(Model model,@RequestParam(name="pjId") int pjId ,@RequestParam(name="dpId") int dpId){
        DayProjectDTO dayProjectDTO= new DayProjectDTO();
        dayProjectDTO.setDpId(dpId);
        model.addAttribute("lstInfoProjectByID",projectService.getInfoProjectByID(pjId));
        model.addAttribute("dayProjectDTO",projectService.selectInfoDayProjectByID(dayProjectDTO));
        model.addAttribute("pjId",pjId);
        model.addAttribute("dpId",dpId);
        return "/project/detailproject/additem";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/detailProjectEdit.hs")
    public String detailProjectEdit(Model model,@RequestParam(name="pjId") int pjId ,@RequestParam(name="dpId") int dpId,@RequestParam(name="dpiId") int dpiId){
        DayProjectItemDTO dayProjectItemDTO= new DayProjectItemDTO();
        dayProjectItemDTO.setDpiId(dpiId);
        model.addAttribute("lst",projectService.getDayProjectItemByID(dayProjectItemDTO));
        model.addAttribute("pjId",pjId);
        model.addAttribute("dpId",dpId);
        model.addAttribute("dpiId",dpiId);
        return "/project/detailproject/edititem";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/AddFileDayProject.hs")
    public String AddFileDayProject(Model model,@RequestParam(name="pjId") int pjId ,@RequestParam(name="dpId") int dpId){
        model.addAttribute("pjId",pjId);
        model.addAttribute("dpId",dpId);
        model.addAttribute("lstFile",projectService.selectListFile(dpId));
        return "/project/detailproject/addfile";
    }
}
