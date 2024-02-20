package kr.co.hs.work.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.deptconfig.dto.DeptDTO;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.work.dto.WorkDTO;
import kr.co.hs.work.service.WorkService;
import kr.co.hs.translation.service.TranslationService;
import kr.co.hs.oldwork.dto.CoperationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WorkController {
    final WorkService workService;
    final TranslationService translationService;
    final DeptService deptService;
    final EmpService empService;

    @Autowired
    private NoticeService noticeService;

    @TilesDynamic("base")
    @GetMapping("/work/work/list.hs")
    public String list(Model model,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        String langCd = LocaleContextHolder.getLocale().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO deptSearchDTO = new DeptSearchDTO();
        deptSearchDTO.setDeptCd(empDTO.getDeptCd());
        deptSearchDTO.setLangCd(langCd);
        deptSearchDTO.setTranslationSpecific(empDTO.getTranslationYn());
        deptSearchDTO.setAdminSpecitfic(empDTO.getAdminYn());
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));

        return "/work/work/list";
    }

    @RequestMapping(value = "/springPaginationDataTablesWork", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String springPaginationDataTablesWork(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        //xu ly date
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date
        String deptCd = request.getParameter("levelDept");
        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptString=deptService.selectDeptLevelbyDeptCd(para);
        String deptSearch="";
        if (deptString != null && deptString.length() > 0) {
            deptSearch=" AND dept_level_vertical_name LIKE '%"+deptString+"%'";
        }
        //xuly translationStatus

        String translationStatus ="";
        String translationStatusGet = request.getParameter("translationStatus");
        if(translationStatusGet.equals("Y")){
            translationStatus=" AND ( SELECT COUNT(dwt.work_id) FROM work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE work_id=work.work_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus="AND ( SELECT COUNT(dwt.work_id) FROM work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE work_id=work.work_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus
        String workStatus = request.getParameter("workStatus");
        //xu ly work stattus
        String workStatusSql="";
        if(workStatus.equals("NOTYET")){
            workStatusSql=" AND work_status='N' ";
        }else if(workStatus.equals("WORKING")){
            workStatusSql=" AND work_status='0' ";
        }else if(workStatus.equals("FINISH")){
            workStatusSql=" AND work_status='Y' ";
        }else{
            workStatusSql="";
        }
        //end work stattus

        //xu ly input search
        String inputSearchSql="";
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        if(!inputSearch.equals("") && !inputSearch.equals(null)){
            if(optionSearch.equals("emp_name")){
                inputSearchSql=" AND emp_name LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND position_name LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("title")){
                inputSearchSql="  AND title LIKE '%"+inputSearch+"%'";
            }else{
                inputSearchSql="";
            }
        }
        //end input search

        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;

        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter = request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        List<WorkDTO> ttList = createPaginationDataWork(pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,workStatusSql,inputSearchSql,str);
        WorkDTO workDTO = new WorkDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch", searchParameter.trim());
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("translationYnPara",emp.getTranslationYn());
        int sum_row = workService.getSumRowWork(param);
        // Set Total display record
        workDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch", searchParameter.trim(), "");
        param.replace("dateSql", dateSql, "");
        param.replace("deptSearch", deptSearch, "");
        param.replace("translationStatus", translationStatus, "");
        param.replace("workStatusSql", workStatusSql, "");
        param.replace("inputSearchSql", inputSearchSql, "");
        sum_row = workService.getSumRowWork(param);
        workDTO.setITotalRecords(sum_row);
        workDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(workDTO);

        return json;
    }

    private List<WorkDTO> createPaginationDataWork(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String translationStatus, String workStatusSql, String inputSearchSql, String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch", searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("translationYnPara",emp.getTranslationYn());
        param.put("adminYnPara",emp.getAdminYn());
        param.put("str", str);
        List<WorkDTO> ttList = workService.getlistWork(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/work/work/detail.hs")
    public String detail(Model model, @RequestParam(name = "id") String id, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/work @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        String str = emp.getEmpCd().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("workId", id);
        pra.put("empCd_", str);
        //noticeService.updateViewNoticeWork(pra);

        model.addAttribute("detailDTO", workService.getlistWorkByID(pra));
        model.addAttribute("detailTran", translationService.getTranKork(pra));
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstCo", workService.getCoporation(pra));
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("empGui", empDTO.getEmpCd());
        model.addAttribute("empDTO", emp);

        return "/work/work/detail";
    }

    @RequestMapping(value = "/work/work/getEmp", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getEmp(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("deptCd") String deptCd) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("deptCd", deptCd);
        String strEmp = "";
        String str2 = "";

        List<EmpDTO> lstemp = workService.getEmpByDept(pra);
//        for (int i = 0; i < lstemp.size(); i++) {
//            int k = i + 1;
//            strEmp += "<option value='" + lstemp.get(i).getEmpCd() + "'>" + lstemp.get(i).getEmpName() + "</option>";
//            str2 += "<div class='choices__item choices__item--choice choices__item--selectable' data-select-text='Press to select' data-choice='' data-id='" + k + "' data-value='" + lstemp.get(i).getEmpCd() + "' data-choice-selectable='' id='choices--choices-multiple-remove-button-item-choice-" + k + "' role='option'>" + lstemp.get(i).getEmpName() + "</div>";
//        }
//
//        List<String> messages = new ArrayList<String>();
//        messages.add(strEmp);
//        messages.add(str2);

        return new Gson().toJson(lstemp).toString();
    }

    @RequestMapping(value = "/work/work/addCo", method = RequestMethod.POST)
    @ResponseBody
    public String addCoper(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("lstCo") String lstCo, @RequestParam("workId") String workId) {

        String[] mangEmp = lstCo.split(",");
        for (int i = 0; i < mangEmp.length; i++) {
            EmpDTO empDTO = workService.getEmpForWork(Integer.parseInt(mangEmp[i]));
            CoperationDTO coperationDTO = new CoperationDTO();
            coperationDTO.setWorkId(Integer.parseInt(workId));
            coperationDTO.setEmpCd(Integer.parseInt(mangEmp[i]));
            coperationDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
            coperationDTO.setDeptCd(empDTO.getDeptCd());
            coperationDTO.setPositionCd(empDTO.getPositionCd());
            coperationDTO.setWorkCheckYn("N");
            workService.insertCoForWork(coperationDTO);
        }
        return "A";
    }

    @RequestMapping(value = "/work/work/xoaCo", method = RequestMethod.POST)
    @ResponseBody
    public String xoaCo(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        //xoa coperation
        int a = workService.xoaCo(id);
        return "A";
    }

    @PostMapping("/work/work/updateworkstatus.hs")
    public String updateWorkStatus(@RequestBody WorkDTO workDTO) {
        workService.updateWorkStatus(workDTO);
        return "/work/work/detail.hs?id=" + workDTO.getWorkId();
    }

    @ResponseBody
    @PostMapping(value = "/work/work/xoaW", produces = "application/json; charset=UTF-8")
    public int xoaW(String lstChecked) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/work/xoaW @@@@@@@@@");

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {

            str += " work_id=" + arrOfStr[i].toString() + " or";

        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        int Del = workService.xoaW(param);
        log.error("{}", new Gson().toJson(user));
        return 1;
    }

    @RequestMapping(value = "/work/work/xoaTran", method = RequestMethod.POST)
    @ResponseBody
    public String xoaTran(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                          @RequestParam("id") String id) {
        //xoa
        int a = workService.xoaTran(id);
        return "A";
    }

    @RequestMapping(value = "/work/work/updateInfor", method = RequestMethod.POST)
    @ResponseBody
    public String updateInfor(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("id") String id, @RequestParam("typeCheck") String typeCheck, @RequestParam("trangthai") String trangthai) {
        //update infor
        HashMap param = new HashMap();
        param.put("id", id);
        param.put("trangthai", trangthai);

        EmpDTO emp = empService.getSessionUserLogin(request);
        System.out.println(param);
        String result = "";

        if (typeCheck.equals("status")) {
            if (emp.getPositionCd() < 8){
                int a = workService.updateInTran(param);
                result = "A";
            }
            else {
                result = "B";
            }
        }
        if (typeCheck.equals("approve")) {
            int b = workService.updateInCo(param);
            result = "A";
        }
        return result;
    }

    @TilesDynamic("base")
    @GetMapping("/work/work/addW.hs")
    public String themW(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/work @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());

        return "/work/work/add";
    }

    @RequestMapping(value = "/work/work/addW", method = RequestMethod.POST)
    @ResponseBody
    public String addW(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody WorkDTO workDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO empDTO = workService.getEmpForWork(workDTO.getEmpCd());
        workDTO.setLangCd(langCd);
        workDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
        workDTO.setDeptCd(empDTO.getDeptCd());
        workDTO.setPositionCd(empDTO.getPositionCd());
        workDTO.setWorkStatus("0");
        int a = workService.addWork(workDTO);
        if (workDTO.getEmpList().equals("")){
            System.out.println("emp list is empty");
        }else {
            String[] mangEmp = workDTO.getEmpList().split(",");
            for (int i = 0; i < mangEmp.length; i++) {
                EmpDTO emp = workService.getEmpForWork(Integer.parseInt(mangEmp[i]));
                CoperationDTO coperationDTO = new CoperationDTO();
                coperationDTO.setEmpCd(Integer.parseInt(mangEmp[i]));
                coperationDTO.setDeptChangeHistoryId(emp.getDeptChangeHistoryId());
                coperationDTO.setDeptCd(emp.getDeptCd());
                coperationDTO.setPositionCd(emp.getPositionCd());
                coperationDTO.setWorkCheckYn("N");
                workService.insertCoForWorkNewWork(coperationDTO);
            }
        }
        return "A";
    }


    @TilesDynamic("base")
    @GetMapping("/work/work/editW.hs")
    public String suaW(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("workId") String workId) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("workId", workId);
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstCo", workService.getCoporation(pra));

        WorkDTO workDTO = workService.getlistWorkByID(pra);
        workDTO.setWorkStartDt(workDTO.getWorkStartDt().split(" ")[0]);
        workDTO.setWorkEndDt(workDTO.getWorkEndDt().split(" ")[0]);
        model.addAttribute("workDTO", workDTO);

        return "/work/work/edit";
    }

    @RequestMapping(value = "/work/work/editW", method = RequestMethod.POST)
    @ResponseBody
    public String editW(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody WorkDTO workDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO empDTO = workService.getEmpForWork(workDTO.getEmpCd());
        workDTO.setLangCd(langCd);
        workDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
        workDTO.setDeptCd(empDTO.getDeptCd());
        workDTO.setPositionCd(empDTO.getPositionCd());
        int a = workService.updateWork(workDTO);
        HashMap param = new HashMap();
        param.put("workId", workDTO.getWorkId());
        String proEmpList = "(" + workDTO.getEmpList() + ")";
        param.put("empList", proEmpList);

        int exitEmpCheck;

        if (workDTO.getEmpList().equals("")){
            workService.deleteCopEmpty(param);
        }
        else {
            System.out.println("DXD: ---------------------------");
            String[] mangEmp = workDTO.getEmpList().split(",");
            for (int i = 0; i < mangEmp.length; i++) {
                EmpDTO emp = workService.getEmpForWork(Integer.parseInt(mangEmp[i]));
                CoperationDTO coperationDTO = new CoperationDTO();
                workDTO.setEmpCd(Integer.parseInt(mangEmp[i]));
                exitEmpCheck = workService.empCheckExitCop(workDTO);
                if (exitEmpCheck == 0){
                    coperationDTO.setWorkId(workDTO.getWorkId());
                    coperationDTO.setEmpCd(Integer.parseInt(mangEmp[i]));
                    coperationDTO.setDeptChangeHistoryId(emp.getDeptChangeHistoryId());
                    coperationDTO.setDeptCd(emp.getDeptCd());
                    coperationDTO.setPositionCd(emp.getPositionCd());
                    coperationDTO.setWorkCheckYn("N");
                    workService.insertCoForWork(coperationDTO);
                }
            }
            workService.deleteCop(param);
        }

        return "A";
    }

}
