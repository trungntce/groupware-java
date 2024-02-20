package kr.co.hs.board.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.service.CommonSearch;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {
    @Autowired
    private EmpService empService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CommonSearch commonSearch;

    @TilesDynamic("base")
    @GetMapping("/board/notice/list.hs")
    public String list(Model model, HttpServletRequest request){
        return "/hrga/notice/list";
    }

    @RequestMapping(value = "/springPaginationDataTablesNotice", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String springPaginationDataTables(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        //xu ly date
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");

        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(time_up , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(time_up , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(time_up , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date
        String deptCd = request.getParameter("levelDept");
        HashMap para = new HashMap();
        para.put("langCd", langCd);
        String deptSearch=deptCd;

        //xu ly input search

        String inputSearchSql = "";
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        if (!inputSearch.equals("") && !inputSearch.equals(null)){
            if (optionSearch.equals("emp_name")){
                inputSearchSql=" AND emp_name LIKE '%"+inputSearch+"%' ";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND position_name  LIKE '%"+inputSearch+"%'";
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
        if(str.equals("event_notice_id desc")){
            str = "html_str_open ASC, time_up DESC";
        }
        System.out.println("DXD:->> "+ str);
        //end sort
        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter=request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data

        List<NoticeDTO> ttList = createPaginationData(pageNumber,pageDisplayLength, searchParameter, request, dateSql, deptSearch, inputSearchSql, str);

        NoticeDTO noticeDTO = new NoticeDTO();
        //select total row
        EmpDTO emp = empService.getSessionUserLogin(request);
        int sum_row ;
        HashMap param = new HashMap();
        param.put("empCd",emp.getEmpCd());
        String str_ = emp.getEmpCd().toString();
        param.put("langCd",langCd);
        param.put("empCd_", str);
        param.put("adminYn", emp.getAdminYn());
        // Set Total record
        sum_row = noticeService.getSumRowNoticeAll(param, emp.getAdminYn(), emp.getTranslationYn());
        noticeDTO.setITotalRecords(sum_row);
        System.out.println("tat ca: "+ sum_row);
        param.put("txtSearch",searchParameter.trim());
        param.put("dateSql", dateSql);
        param.put("inputSearchSql", inputSearchSql );

        sum_row = noticeService.getSumRowNoticeAll(param, emp.getAdminYn(), emp.getTranslationYn());
        System.out.println("search: "+ sum_row);
        // Set Total display record
        noticeDTO.setITotalDisplayRecords(sum_row);
        noticeDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(noticeDTO);

        return json;
    }

        private List<NoticeDTO> createPaginationData(Integer pageNumber, Integer pageDisplayLength,String searchParameter,HttpServletRequest request
                ,String dateSql,String deptSearch,String inputSearchSql,String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);

        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("empCd_", emp.getEmpCd().toString());
        param.put("adminYn", emp.getAdminYn());
        if (str.equals("time_up asc")){
            str = "EVENT_NOTICE_ID DESC";
        }
        param.put("str", str);
        param.put("translationYn", emp.getTranslationYn());

        param.put("txtSearch",searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("dateSql", dateSql);
        param.put("deptSearch", deptSearch);
        param.put("inputSearchSql", inputSearchSql);

        List<NoticeDTO> ttList = noticeService.allNotice(param, emp.getAdminYn(), emp.getTranslationYn());

        return ttList;
    }

    @ResponseBody
    @PostMapping(value = "/board/notice/detele", produces = "application/json; charset=UTF-8")
    public int readPageNotice(String lstChecked, HttpServletRequest request) {
        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("listNoticeCd", "(" +lstChecked + ")");
        noticeService.readPageNotificationReceiver(param);
        return 1;
    }

    @ResponseBody
    @PostMapping(value = "/board/notice/readall", produces = "application/json; charset=UTF-8")
    public int readAllNotice(HttpServletRequest request){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        noticeService.readAllNotificationReceiver(empDTO.getEmpCd().toString());
        return 1;
    }
}
