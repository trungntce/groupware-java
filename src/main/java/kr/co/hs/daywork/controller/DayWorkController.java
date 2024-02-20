package kr.co.hs.daywork.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.comment.service.CommentService;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.daywork.service.WorkExportExcel;
import kr.co.hs.deptconfig.dto.DeptDTO;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.daywork.service.DayWorkService;
import kr.co.hs.work.service.WorkService;
import kr.co.hs.translation.service.TranslationService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DayWorkController {
    @Autowired
    private DayWorkService dayWorkService;
    @Autowired
    private EmpService empService;
    @Autowired
    private TranslationService translationService;
    @Autowired
    private WorkService workService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private WorkExportExcel workExportExcel;

    @TilesDynamic("base")
    @GetMapping("/work/daywork/list.hs")
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

        return "/daywork/daywork/list";
    }

    @RequestMapping(value = "/springPaginationDataTables", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTables(HttpServletRequest request) throws IOException {
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
            translationStatus=" AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus="AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus
        String workStatus = request.getParameter("workStatus");
        //xu ly work stattus
        String workStatusSql="";
        if(workStatus.equals("NOTYET")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet')>0 ";
        }else if(workStatus.equals("WORKING")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')>0";
        }else if(workStatus.equals("FINISH")){
            workStatusSql="AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet') =0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')=0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='finish') >0";
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

        //sort
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
        System.out.println("DXD: "+ str);
        //end sort
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter=request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        List<DayWorkDTO> ttList = createPaginationData(pageNumber, pageDisplayLength, searchParameter,request,dateSql,deptSearch,translationStatus,workStatusSql,inputSearchSql,str);

        DayWorkDTO dayWorkDTO = new DayWorkDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch",searchParameter.trim());
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("translationYnPara",emp.getTranslationYn());


        int sum_row= dayWorkService.getSumRowDaywork(param);
        // Set Total display record
        dayWorkDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch",searchParameter.trim(),"");
        param.replace("dateSql", dateSql, "");
        param.replace("deptSearch", deptSearch, "");
        param.replace("translationStatus", translationStatus, "");
        param.replace("workStatusSql", workStatusSql, "");
        param.replace("inputSearchSql", inputSearchSql, "");
        sum_row= dayWorkService.getSumRowDaywork(param);
        dayWorkDTO.setITotalRecords(sum_row);
        dayWorkDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dayWorkDTO);

        return json;
    }

    private List<DayWorkDTO> createPaginationData(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String translationStatus, String workStatusSql, String inputSearchSql, String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch",searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("str", str);
        param.put("translationYnPara",emp.getTranslationYn());
        param.put("adminYnPara",emp.getAdminYn());
        List<DayWorkDTO> ttList = dayWorkService.getListDayWork(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/work/daywork/detail.hs")
    public String detail(Model model, @RequestParam(name = "id") String id, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response){

        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        String str = emp.getEmpCd().toString();

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", id);
        pra.put("empCd_", str);
        //noticeService.updateViewNoticeDayWork(pra);
        System.out.println(pra);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        int positionNumber = emp.getPositionCd();

        model.addAttribute("detailDTO", dayWorkDTO);
        model.addAttribute("detailTran", translationService.getDayWorkTranko(pra));
        model.addAttribute("detailCMT", commentService.getListCMT(pra,id,langCd));
        model.addAttribute("workChild", dayWorkService.getListDayWorkChildById(pra));
        model.addAttribute("workMoreChild", dayWorkService.getlistMoreWorkChildByID(pra));
        model.addAttribute("countCheck", dayWorkService.getCountNumber(pra));
        model.addAttribute("empCd", emp.getEmpCd());
        model.addAttribute("positionEmp", positionNumber);
        model.addAttribute("empDTO", emp);
        log.error("{}", new Gson().toJson(emp));
        if (positionNumber>8){
            model.addAttribute("disabled", "disabled");
        } else {
            model.addAttribute("disabled", "");
        }

        if (emp.getEmpCd().equals(dayWorkDTO.getEmpCd())){
            model.addAttribute("styleDisble","");
            model.addAttribute("statusEdit", 1);
        }else {
            model.addAttribute("styleDisble","disabled");
            model.addAttribute("statusEdit", 0);
        }

        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("empGui", emp.getEmpCd());

        return "/daywork/daywork/detail";
    }

    @TilesDynamic("base")
    @GetMapping("/work/daywork/addmore.hs")
    public String addmore(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("dayWorkId") String dayWorkId) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());
        model.addAttribute("daywork", dayWorkId);

        String dayWorkTime = dayWorkDTO.getRegDt().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/daywork/list.hs";
        if (dayWorkTime.equals(timeNow)){
            link ="/daywork/daywork/addmore";
        }
        System.out.println("DXD: "+ link);

        return link;
    }

    @TilesDynamic("base")
    @GetMapping("/work/mydaywork/addmore.hs")
    public String addMoreMyDayWork(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("dayWorkId") String dayWorkId) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());
        model.addAttribute("daywork", dayWorkId);

        String dayWorkTime = dayWorkDTO.getRegDt().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/mydaywork/list.hs";
        if (dayWorkTime.equals(timeNow)){
            link ="/daywork/mydaywork/addmore";
        }
        System.out.println("DXD: "+ link);

        return link;

    }

    @TilesDynamic("base")
    @GetMapping("/work/daywork/edit.hs")
    public String edit(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("dayWorkId") String dayWorkId
                        , @RequestParam("type") String type){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);

        model.addAttribute("workDTO", dayWorkDTO);
        model.addAttribute("workChild", dayWorkService.getListDayWorkChildById(pra));
        model.addAttribute("type", type);

        String dayWorkTime = dayWorkDTO.getRegDt().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/daywork/list.hs";
        if (dayWorkTime.equals(timeNow)){
            link ="/daywork/daywork/edit";
        }
        System.out.println("DXD: "+ link);

        return link;
    }

    @TilesDynamic("base")
    @GetMapping("/work/daywork/editmore.hs")
    public String editmore(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("dayWorkId") String dayWorkId
            , @RequestParam("type") String type){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        model.addAttribute("workDTO", dayWorkDTO);
        model.addAttribute("workChild", dayWorkService.getListMoreDayWorkChildById(pra));
        model.addAttribute("type", type);
        String dayWorkTime = dayWorkDTO.getRegDt().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/daywork/list.hs";
        if (dayWorkTime.equals(timeNow)){
            link ="/daywork/daywork/edit";
        }
        System.out.println("DXD: "+ link);

        return link;
    }

    @TilesDynamic("base")
    @GetMapping("/work/mydaywork/editmore.hs")
    public String editmoreMy(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("dayWorkId") String dayWorkId
            , @RequestParam("type") String type){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        model.addAttribute("workDTO", dayWorkDTO);
        model.addAttribute("workChild", dayWorkService.getListMoreDayWorkChildById(pra));
        model.addAttribute("type", type);
        String dayWorkTime = dayWorkDTO.getRegDt().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/mydaywork/list.hs";
        if (dayWorkTime.equals(timeNow)){
            link ="/daywork/mydaywork/edit";
        }
        System.out.println("DXD: "+ link);

        return link;
    }

    @RequestMapping(value = "/work/daywork/editW", method = RequestMethod.POST)
    @ResponseBody
    public String doEdit(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody List<Object> lst, DayWorkDTO songDayWorkDTO,
                         @RequestParam("dayWorkId") int dayWorkId, @RequestParam("type") String type) {

        String langCd = LocaleContextHolder.getLocale().toString();
        String content = "", key = "";
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        songDayWorkDTO.setLangCd(langCd);
        songDayWorkDTO.setDeptCd(empDTO.getDeptCd());
        songDayWorkDTO.setPositionCd(empDTO.getPositionCd());
        songDayWorkDTO.setEmpCd(empDTO.getEmpCd());
        songDayWorkDTO.setDayWorkId(dayWorkId);

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        ////////
        int cout = 0;

        if (type.equals("main")){
            cout = dayWorkService.getTypeMainWork(pra);
        }
        if (type.equals("sub")){
            cout = dayWorkService.getTypeSubWork(pra);
        }
        System.out.println("DXD: "+ lst);
        //update
        for (int i = 0; i < cout; i++){
            //content = lst.get(i).toString().split(".=")[1].substring(0,lst.get(i).toString().split(".=")[1].length()-1);

            content = lst.get(i).toString().substring(lst.get(i).toString().split("=")[0].substring(1).length() + 2, lst.get(i).toString().length() -1);
            key = lst.get(i).toString().split("=")[0].substring(1);

            songDayWorkDTO.setId(Integer.valueOf(key));
            if (!content.equals(dayWorkService.getListDayWorkChildByIdChild(songDayWorkDTO))){
                songDayWorkDTO.setContents(content);
                int a = dayWorkService.updateWorkChild(songDayWorkDTO);
            }
        }

        //insert
        if (type.equals("main")){
            for (int i = cout; i < lst.size() - 1; i++){
                //content = lst.get(i).toString().split("=")[1].substring(0,lst.get(i).toString().split("=")[1].length()-1);
                content = lst.get(i).toString().substring(lst.get(i).toString().split("=")[0].substring(1).length() + 2, lst.get(i).toString().length() -1);
                key = lst.get(i).toString().split("=")[0].substring(1);
                songDayWorkDTO.setContents(content);
                int b = dayWorkService.addChildDayWorkUpdate(songDayWorkDTO);
            }
        }
        if (type.equals("sub")){
            for (int i = cout; i < lst.size() - 1; i++){
                //content = lst.get(i).toString().split("=")[1].substring(0,lst.get(i).toString().split("=")[1].length()-1);
                content = lst.get(i).toString().substring(lst.get(i).toString().split("=")[0].substring(1).length() + 2, lst.get(i).toString().length() -1);
                key = lst.get(i).toString().split("=")[0].substring(1);
                songDayWorkDTO.setContents(content);
                int b = dayWorkService.addMoreWorkChildUpdate(songDayWorkDTO);
            }
        }

        int strContents = lst.size()-1;
        songDayWorkDTO.setContents(String.valueOf(strContents));
        songDayWorkDTO.setTitle(lst.get(lst.size()-1).toString().split("=")[1].substring(0,lst.get(lst.size()-1).toString().split("=")[1].length()-1));
        int a = dayWorkService.updateWork(songDayWorkDTO);
        return "A";
    }

    @TilesDynamic("base")
    @GetMapping("/work/daywork/addW.hs")
    public String themW(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());

        return "/daywork/daywork/add";
    }

    @RequestMapping(value = "/work/daywork/addW", method = RequestMethod.POST)
    @ResponseBody
    public String addW(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody List<DayWorkDTO> lst, DayWorkDTO dayWorkDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        dayWorkDTO.setLangCd(langCd);
        dayWorkDTO.setDeptCd(empDTO.getDeptCd());
        dayWorkDTO.setPositionCd(empDTO.getPositionCd());
        dayWorkDTO.setEmpCd(empDTO.getEmpCd());
        dayWorkDTO.setTitle(lst.get(lst.size()-1).getTitle());

        int strContents = lst.size()-1;

        dayWorkDTO.setContents(String.valueOf(strContents));

        int a = dayWorkService.addWork(dayWorkDTO);

        for (int i = 0; i <lst.size() - 1; i++){
            dayWorkDTO.setContents(lst.get(i).getContents());
            int b = dayWorkService.addWorkChild(dayWorkDTO);
        }

        return "A";
    }

    @RequestMapping(value = "/work/daywork/addmore", method = RequestMethod.POST)
    @ResponseBody
    public String goAddMore(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody List<DayWorkDTO> lst, DayWorkDTO dayWorkDTO) {

        int dayworkid= Integer.valueOf(lst.get(lst.size()-1).getTitle());

        dayWorkDTO.setDayWorkId(dayworkid);

        for (int i = 0; i <lst.size() - 1; i++){
            dayWorkDTO.setContents(lst.get(i).getContents());
            int b = dayWorkService.addMoreWorkChild(dayWorkDTO);
        }

        return "A";
    }

    @PostMapping("/work/daywork/updateworkstatus.hs")
    public String updateWorkStatus(@RequestBody DayWorkDTO dayWorkDTO){
        dayWorkService.updateDayWorkStatus(dayWorkDTO);
        return "/work/work/detail.hs?id="+ dayWorkDTO.getDayWorkId();
    }


    //// THIS IS DAYWORK
    @TilesDynamic("base")
    @GetMapping("/work/mydaywork/list.hs")
    public String listMyDayWork(Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        String langCd = LocaleContextHolder.getLocale().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO deptSearchDTO = new DeptSearchDTO();
        deptSearchDTO.setDeptCd(empDTO.getDeptCd());
        deptSearchDTO.setLangCd(langCd);
        deptSearchDTO.setTranslationSpecific(empDTO.getTranslationYn());
        deptSearchDTO.setAdminSpecitfic(empDTO.getAdminYn());
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));

        return "/daywork/mydaywork/list";
    }
    @RequestMapping(value = "/springPaginationDataTablesMydaywork", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTablesMydaywork(HttpServletRequest request) throws IOException {
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
            translationStatus=" AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus="AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus
        String workStatus = request.getParameter("workStatus");
        //xu ly work stattus
        String workStatusSql="";
        if(workStatus.equals("NOTYET")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet')>0 ";
        }else if(workStatus.equals("WORKING")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')>0";
        }else if(workStatus.equals("FINISH")){
            workStatusSql="AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet') =0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')=0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='finish') >0";
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


        //sort
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
        //end sort

        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter=request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data

        String type = "homeList";
        List<DayWorkDTO> ttList = createPaginationDataMydaywork(pageNumber, pageDisplayLength, searchParameter,request,dateSql,deptSearch,translationStatus,workStatusSql,inputSearchSql,str, type);

        DayWorkDTO dayWorkDTO = new DayWorkDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch",searchParameter.trim());
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("deptCd", emp.getDeptCd());
        int sum_row= dayWorkService.getSumRowMyDaywork(param);
        // Set Total display record
        dayWorkDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch",searchParameter.trim(),"");
        param.replace("dateSql", dateSql, "");
        param.replace("deptSearch", deptSearch, "");
        param.replace("translationStatus", translationStatus, "");
        param.replace("workStatusSql", workStatusSql, "");
        param.replace("inputSearchSql", inputSearchSql, "");
        sum_row= dayWorkService.getSumRowMyDaywork(param);
        dayWorkDTO.setITotalRecords(sum_row);
        dayWorkDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dayWorkDTO);

        return json;
    }

    //export Excel controller

    @RequestMapping(value = "/export/excel/mydayworkkkk", method = RequestMethod.GET)
    public String exportExcelMyDayWork(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        //xu ly date
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(day_work.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(day_work.reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(day_work.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
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
            translationStatus=" AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus="AND ( SELECT COUNT(dwt.day_work_id) FROM day_work_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE day_work_id=day_work.day_work_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus
        String workStatus = request.getParameter("workStatus");
        //xu ly work stattus
        String workStatusSql="";
        if(workStatus.equals("NOTYET")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet')>0 ";
        }else if(workStatus.equals("WORKING")){
            workStatusSql=" AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')>0";
        }else if(workStatus.equals("FINISH")){
            workStatusSql="AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='notyet') =0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='working')=0 " +
                    "      AND (SELECT COUNT(*) FROM day_work_child WHERE day_work_id=day_work.day_work_id AND use_yn='Y' AND work_status='finish') >0";
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


        //sort
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
        //end sort

        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter=request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        String type = "excel";
        List<DayWorkDTO> ttList = createPaginationDataMydaywork(pageNumber, pageDisplayLength, searchParameter,request,dateSql,deptSearch,translationStatus,workStatusSql,inputSearchSql,str, type);

        EmpDTO empDTO = empService.getSessionUserLogin(request);
        String result = workExportExcel.exportExcelFile(empDTO, ttList);
        System.out.println("DXD:  "+ result);
        return "DXD";
    }

    // end export excel

    private List<DayWorkDTO> createPaginationDataMydaywork(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String translationStatus, String workStatusSql, String inputSearchSql, String str, String type) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch",searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("translationStatus", translationStatus);
        param.put("workStatusSql", workStatusSql);
        param.put("inputSearchSql", inputSearchSql);
        param.put("deptCd", emp.getDeptCd());
        param.put("str", str);
        param.put("type", type);

        List<DayWorkDTO> ttList = dayWorkService.getListDayMyWork(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/work/mydaywork/detail.hs")
    public String detailMy(Model model, @RequestParam(name = "id") String id, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response){

        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        String str = emp.getEmpCd().toString();

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", id);
        pra.put("empCd_", str);
        pra.put("noticeCd", id);
        pra.put("type", "DAYWORK");
        pra.put("empCd", emp.getEmpCd());

        noticeService.readNotificationReceiver(pra);

        model.addAttribute("empCd", emp.getEmpCd());
        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);

        model.addAttribute("detailDTO", dayWorkDTO);
        model.addAttribute("detailTran", translationService.getDayWorkTranko(pra));
        model.addAttribute("detailCMT", commentService.getListCMT(pra,id,langCd));
        model.addAttribute("workChild", dayWorkService.getListDayWorkChildById(pra));
        model.addAttribute("workMoreChild", dayWorkService.getlistMoreWorkChildByID(pra));
        model.addAttribute("positionEmp", emp.getPositionCd());
        model.addAttribute("countCheck", dayWorkService.getCountNumber(pra));
        model.addAttribute("empDTO", emp);
        if (emp.getEmpCd().equals(dayWorkDTO.getEmpCd())){
            model.addAttribute("styleDisble","");
            model.addAttribute("statusEdit", 1);
        }else {
            model.addAttribute("styleDisble","disabled");
            model.addAttribute("statusEdit", 0);
        }

        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("empGui", emp.getEmpCd());

        return "/daywork/mydaywork/detail";
    }

    @GetMapping("/work/mydaywork/edit.hs")
    public String editMy(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("dayWorkId") String dayWorkId
            , @RequestParam("type") String type) {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dayWorkId);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);

        model.addAttribute("workDTO", dayWorkDTO);
        model.addAttribute("workChild", dayWorkService.getListDayWorkChildById(pra));
        model.addAttribute("type", type);
        String dayWorkTime = dayWorkDTO.getRegDt().substring(0, 10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/work/mydaywork/list.hs";
        if (dayWorkTime.equals(timeNow)) {
            link = "/daywork/mydaywork/edit";
        }
        System.out.println("DXD: " + link);

        return link;
    }

    @TilesDynamic("base")
    @GetMapping("/work/mydaywork/addW.hs")
    public String themWMy(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("empCd", empDTO.getEmpCd());

        return "/daywork/mydaywork/add";
    }

    @ResponseBody
    @PostMapping(value = "/work/daywork/deleteWorkChild", produces = "application/json; charset=UTF-8")
    public int deleteWorkChild(int id) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/deleteEmp @@@@@@@@@");
        int Del = dayWorkService.deleteWorkChild(id);
        return 1;
    }

    @RequestMapping(value = "/work/work/statusupdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateDayWorkChildStatus(ModelMap model, HttpServletRequest request, HttpServletResponse response,@RequestBody DayWorkDTO dayWorkDTO) {
        DayWorkDTO dayWorkDTO1 = dayWorkService.getChildDayWorkId(dayWorkDTO);
        System.out.println("DXD: "+ dayWorkDTO1.getCreateDate());
        String dayWorkTime = dayWorkDTO1.getCreateDate().substring(0,10);
        String timeNow = String.valueOf(LocalDate.now());
        String link = "redirect:/main/index.hs";
        if (dayWorkTime.equals(timeNow)){
            int Del = dayWorkService.updateWorkChildstatus(dayWorkDTO);
            return dayWorkService.getListDayWorkChildByIdTime(dayWorkDTO).substring(0,19);
        }else {
            return link;
        }
    }

    @ResponseBody
    @PostMapping(value = "/work/daywork/xoaW", produces = "application/json; charset=UTF-8")
    public int xoaW(String lstChecked) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/work/xoaW @@@@@@@@@");

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {

            str += " day_work_id=" + arrOfStr[i].toString() + " or";

        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        int Del = dayWorkService.xoaW(param);
        log.error("{}", new Gson().toJson(user));
        return 1;
    }

    @RequestMapping(value = "/work/daywork/updateInfor", method = RequestMethod.POST)
    @ResponseBody
    public String updateTranStatus(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("id") String id, @RequestParam("dayworkid") String dayWorkId) {
        //update infor
        HashMap param = new HashMap();
        param.put("translationId", id);
        param.put("dayWorkId", dayWorkId);
        String result = "";

        EmpDTO emp = empService.getSessionUserLogin(request);
        if (emp.getPositionCd() < 8){
            dayWorkService.updateTransStatus(param);
            result = "A";
        }else {
            result = "B";
        }

        return result;
    }
}