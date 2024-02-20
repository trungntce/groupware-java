package kr.co.hs.daywork.controller;

import kr.co.hs.common.service.NoticeService;
import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.daywork.service.DayWorkService;
import kr.co.hs.daywork.service.WorkExportExcel;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DayWorkAPI {
    @Autowired
    private DayWorkService dayWorkService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private WorkExportExcel workExportExcel;
    @Autowired
    private EmpService empService;


    //export Excel controller

    @RequestMapping(value = "/export/excel/mydaywork", method = RequestMethod.GET)
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
        return result;
    }

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
}
