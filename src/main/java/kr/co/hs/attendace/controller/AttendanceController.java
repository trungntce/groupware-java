package kr.co.hs.attendace.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.attendace.dto.AttendanceDTO;
import kr.co.hs.attendace.dto.AttendanceSumDTO;
import kr.co.hs.attendace.service.AttendaceService;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AttendanceController {
    final AttendaceService attendaceService;
    final DeptService deptService;
    @Autowired
    private EmpService empService;
    final NoticeService noticeService;


    @TilesDynamic("base")
    @GetMapping("/attendance/attendanceadmin/attendance.hs")
    public String attendance(Model model, @RequestParam(required = false, name = "startdate") String startdate, @RequestParam(required = false, name = "enddate") String enddate,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("title", title);
        request.setAttribute("message", message);
        String langCd = LocaleContextHolder.getLocale().toString();
        if (startdate == null) {
            startdate = java.time.LocalDate.now().toString();

        }
        model.addAttribute("startdate", startdate);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO deptSearchDTO = new DeptSearchDTO();
        deptSearchDTO.setDeptCd(empDTO.getDeptCd());
        deptSearchDTO.setLangCd(langCd);
        deptSearchDTO.setTranslationSpecific("Specific");
        deptSearchDTO.setEmpCd(empDTO.getEmpCd());
        deptSearchDTO.setAdminSpecitfic(empDTO.getAdminYn());
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));
        return "/attendance/attendance/attendance-admin";
    }

    @RequestMapping(value = "/springPaginationDataTablesAtt", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String springPaginationDataTablesAtt(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String deptCd = request.getParameter("levelDept");
        HashMap para = new HashMap();
        para.put("langCd", langCd);
        para.put("deptCd", deptCd);
        String deptSearch = deptService.selectDeptLevelbyDeptCd(para);
        String startdate="";
        String enddate="";
        startdate=request.getParameter("startdate");
        enddate=request.getParameter("enddate");
        if(startdate.equals("")){
            startdate= java.time.LocalDate.now().toString();
            enddate=java.time.LocalDate.now().toString();
        }
        //sort
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
//        String typeOrder1 =  request.getParameter("sSortDir_1");
//        if(typeOrder1 != null){
//            str+=" ,IFNULL(checkin_time,'zzz') asc";
//        }
        //end sort

        if (null != request.getParameter("iDisplayStart"))
            pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / pageDisplayLength) + 1;
        // Fetch search parameter
        String searchParameter = "";
        searchParameter = request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        List<AttendanceDTO> ttList = createPaginationDataAtt(pageNumber, pageDisplayLength, searchParameter, request, deptSearch,str,startdate,enddate);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("txtSearch", searchParameter.trim());
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("deptSearch", deptSearch);
        param.put("startdate", startdate);
        param.put("enddate", enddate);

        int sum_row = attendaceService.getSumRowAtt(param);
        // Set Total display record
        attendanceDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch", searchParameter.trim(), "");
        param.replace("deptSearch", deptSearch, "");
        sum_row = attendaceService.getSumRowAtt(param);
        attendanceDTO.setITotalRecords(sum_row);
        attendanceDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(attendanceDTO);

        return json;
    }

    private List<AttendanceDTO> createPaginationDataAtt(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String deptSearch,String str,String startdate,String enddate) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);

        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("txtSearch", searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("deptSearch", deptSearch);
        param.put("str", str);
        List<AttendanceDTO> ttList = attendaceService.selectListAtt(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/attendance/attendance/attendance.hs")
    public String myAttendance(Model model, @RequestParam(required = false, name = "startdate") String startdate, @RequestParam(required = false, name = "enddate") String enddate, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);

        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        if (startdate == null) {
            startdate = java.time.LocalDate.now().toString();
            enddate = java.time.LocalDate.now().toString();
        }
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        model.addAttribute("startdate", startdate);
        model.addAttribute("enddate", enddate);
        model.addAttribute("listAtt", attendaceService.getMyAttendanceList(param));

        return "/attendance/attendance/attendance";
    }

    @TilesDynamic("base")
    @GetMapping("/attendance/attendanceadmin/summaryadmin.hs")
    public String summaryadmin(Model model, @RequestParam(required = false, name = "startdate") String startdate, @RequestParam(required = false, name = "enddate") String enddate, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        if (startdate == null) {
            startdate = java.time.LocalDate.now().toString();
            enddate = java.time.LocalDate.now().toString();
        }
        model.addAttribute("startdate", startdate);
        model.addAttribute("enddate", enddate);
        String langCd = LocaleContextHolder.getLocale().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO deptSearchDTO = new DeptSearchDTO();
        deptSearchDTO.setDeptCd(empDTO.getDeptCd());
        deptSearchDTO.setLangCd(langCd);
        deptSearchDTO.setTranslationSpecific("Specific");
        deptSearchDTO.setEmpCd(empDTO.getEmpCd());
        deptSearchDTO.setAdminSpecitfic(empDTO.getAdminYn());
        model.addAttribute("lstDeptLevel",deptService.selectDeptLowLevel(deptSearchDTO));

        return "/attendance/attendance/summaryadmin";
    }

    @RequestMapping(value = "/springPaginationDataTablesSumAdmin", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String springPaginationDataTablesSumAdmin(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        String startdate = "";
        String enddate = "";
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String deptCd = request.getParameter("levelDept");
        HashMap para = new HashMap();
        para.put("langCd", langCd);
        para.put("deptCd", deptCd);
        String deptSearch = deptService.selectDeptLevelbyDeptCd(para);
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
        searchParameter = request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        startdate = request.getParameter("startdate");
        enddate = request.getParameter("enddate");
        if (startdate.equals("")) {
            startdate = java.time.LocalDate.now().toString();
            enddate = java.time.LocalDate.now().toString();
        }
        List<AttendanceSumDTO> ttList = createPaginationDataSumAdmin(pageNumber, pageDisplayLength, searchParameter, request, startdate, enddate, deptSearch,str);

        AttendanceSumDTO attendanceSumDTO = new AttendanceSumDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("txtSearch", searchParameter.trim());
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("deptSearch", deptSearch);

        int sum_row = attendaceService.SumrowSummaryAttAdmin(param);
        // Set Total display record
        attendanceSumDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch", searchParameter.trim(), "");
        param.replace("deptSearch", deptSearch, "");
        sum_row = attendaceService.SumrowSummaryAttAdmin(param);
        attendanceSumDTO.setITotalRecords(sum_row);
        attendanceSumDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(attendanceSumDTO);

        return json;
    }

    private List<AttendanceSumDTO> createPaginationDataSumAdmin(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String startdate, String enddate, String deptSearch,String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);

        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("txtSearch", searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("deptSearch", deptSearch);
        param.put("str", str);
        List<AttendanceSumDTO> ttList = attendaceService.summaryAttAdmin(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/attendance/attendance/summaryuser.hs")
    public String summaryuser(Model model, @RequestParam(required = false, name = "startdate") String startdate, @RequestParam(required = false, name = "enddate") String enddate, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/attendance/attendance/attendance @@@@@@@@@");
        request.setAttribute("title", title);
        request.setAttribute("message", message);

        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        if (startdate == null) {
            startdate = java.time.LocalDate.now().toString();
            enddate = java.time.LocalDate.now().toString();
        }
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        model.addAttribute("startdate", startdate);
        model.addAttribute("enddate", enddate);

        model.addAttribute("listAtt", attendaceService.summaryAttUser(param));

        return "/attendance/attendance/summaryuser";
    }

    @TilesDynamic("base")
    @GetMapping("/attendance/attendance/checkattendance.hs")
    public String checkattendance(Model model, @RequestParam(name = "id") String id, @RequestParam(required = false, name = "title") String title
            , @RequestParam(required = false, name = "message") String message, HttpServletRequest request
            , HttpServletResponse response, RedirectAttributes ra) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        //kiem tra ton tai
        int rs = attendaceService.checkExist(empDTO.getEmpCd());
        InetAddress IP = null;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String addr = IP.getHostAddress();
        if (rs > 0) {
            //co dong roi

            if (id.equals("1")) {
                //xu ly check in
                int rsin = attendaceService.checkExistCheckIn(empDTO.getEmpCd());
                if (rsin > 0) {
                    //thoi
                    ra.addAttribute("message", "You already record time attendance !");
                    ra.addAttribute("title", "error");
                    return "redirect:/attendance/attendance/attendance.hs";
                } else {
                    //update vao

                    HashMap param = new HashMap();
                    param.put("empCd", empDTO.getEmpCd());
                    param.put("regIp", addr);
                    int r = attendaceService.updateCheckin(param);
                    ra.addAttribute("message", "Check In Successfully !");
                    ra.addAttribute("title", "success");
                    return "redirect:/attendance/attendance/attendance.hs";
                }
            } else {
                //xu ly check out

                HashMap param = new HashMap();
                param.put("empCd", empDTO.getEmpCd());
                param.put("regIp", addr);
                int r = attendaceService.updateCheckOut(param);
                ra.addAttribute("message", "Check Out Successfully !");
                ra.addAttribute("title", "success");
                return "redirect:/attendance/attendance/attendance.hs";
            }

        } else {
            //chua co dong
            AttendanceDTO attendanceDTO = new AttendanceDTO();
            attendanceDTO.setEmpCd(empDTO.getEmpCd());
            attendanceDTO.setDeptChangeHistoryId(deptService.getDepthis(empDTO.getDeptCd()));

            attendanceDTO.setRegIp(addr);
            attendaceService.insertAttendance(attendanceDTO);
            if (id.equals("1")) {
                HashMap param = new HashMap();
                param.put("empCd", empDTO.getEmpCd());
                param.put("regIp", addr);
                int r = attendaceService.updateCheckin(param);
                ra.addAttribute("message", "Check In Successfully !");
                ra.addAttribute("title", "success");
                return "redirect:/attendance/attendance/attendance.hs";
            } else {
                HashMap param = new HashMap();
                param.put("empCd", empDTO.getEmpCd());
                param.put("regIp", addr);
                int r = attendaceService.updateCheckOut(param);
                ra.addAttribute("message", "Check Out Successfully !");
                ra.addAttribute("title", "success");
                return "redirect:/attendance/attendance/attendance.hs";
            }
        }
    }


    @RequestMapping(value = "/attendance/loadWorkingDay", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AttendanceDTO> loadWorkingDay(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("empCd") int empCd, @RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd", empCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("langCd", langCd);

        List<AttendanceDTO> lst = attendaceService.loadWorkingDay(param);
        return lst;
    }

    @RequestMapping(value = "/attendance/loadEarlyDay", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AttendanceDTO> loadEarlyDay(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("empCd") int empCd, @RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd", empCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("langCd", langCd);

        List<AttendanceDTO> lst = attendaceService.loadEarlyDay(param);
        return lst;
    }

    @RequestMapping(value = "/attendance/loadOntimeDay", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AttendanceDTO> loadOntimeDay(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("empCd") int empCd, @RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd", empCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("langCd", langCd);

        List<AttendanceDTO> lst = attendaceService.loadOntimeDay(param);
        return lst;
    }

    @RequestMapping(value = "/attendance/loadLateDay", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AttendanceDTO> loadLateDay(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("empCd") int empCd, @RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd", empCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("langCd", langCd);

        List<AttendanceDTO> lst = attendaceService.loadLateDay(param);
        return lst;
    }

    @RequestMapping(value = "/attendance/loadLeaveEarly", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AttendanceDTO> loadLeaveEarly(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam("empCd") int empCd, @RequestParam("startdate") String startdate, @RequestParam("enddate") String enddate) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd", empCd);
        param.put("startdate", startdate);
        param.put("enddate", enddate);
        param.put("langCd", langCd);

        List<AttendanceDTO> lst = attendaceService.loadLeaveEarly(param);
        return lst;
    }


}
