package kr.co.hs.emp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class EmpController {
    final EmpService empService;
    final DeptService deptService;
    final PositionService positionService;
    final NoticeService noticeService;
    final CodeService codeService;


    @TilesDynamic("base")
    @GetMapping("/emp/list.hs")
    public String list(Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("lstDeptLevel",deptService.selectDeptLevel(langCd));
        return "/emp/list";
    }
    @RequestMapping(value = "/springPaginationDataTablesEmp", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String springPaginationDataTablesEmp(HttpServletRequest request) throws IOException {
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));

        String deptCd = request.getParameter("levelDept");
        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptSearch=deptService.selectDeptLevelbyDeptCd(para);

        //xu ly input search
        String inputSearchSql="";
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        if(!inputSearch.equals("") && !inputSearch.equals(null)){
            if(optionSearch.equals("emp_name")){
                inputSearchSql=" AND GET_EMP_NAME(emp_cd, '"+langCd+"') LIKE '%"+inputSearch+"%' ";
            }else if(optionSearch.equals("emp_id")){
                inputSearchSql=" AND EMP_ID  LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql="  AND GET_POSITION_NAME(EMP.POSITION_CD, '"+langCd+"') LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("enter_date")){
                inputSearchSql="  AND DATE_FORMAT(ENTER_DATE, '%Y-%m-%d') LIKE '%"+inputSearch+"%'";
            }
            else{
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
        searchParameter = request.getParameter("sSearch");
        // Fetch Page display length
        // Create page list data
        List<EmpDTO> ttList = createPaginationDataEmp(pageNumber, pageDisplayLength, searchParameter, request,deptSearch,inputSearchSql,str);
        EmpDTO empDTO = new EmpDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch", searchParameter.trim());
        param.put("deptSearch", deptSearch);
        param.put("inputSearchSql", inputSearchSql);
        int sum_row = empService.sumRowsEmp(param);
        // Set Total display record
        empDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.replace("txtSearch", searchParameter.trim(), "");
        param.replace("deptSearch", deptSearch, "");
        param.replace("inputSearchSql", inputSearchSql, "");
        sum_row = empService.sumRowsEmp(param);
        empDTO.setITotalRecords(sum_row);
        empDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(empDTO);

        return json;
    }

    private List<EmpDTO> createPaginationDataEmp(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request,String deptSearch,String inputSearchSql,String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd", emp.getEmpCd());
        param.put("langCd", langCd);
        param.put("txtSearch", searchParameter.trim());
        param.put("startRow", a);
        param.put("recordsPerPage", pageDisplayLength);
        param.put("deptSearch", deptSearch);
        param.put("inputSearchSql", inputSearchSql);
        param.put("adminYnPara",emp.getAdminYn());
        param.put("str", str);
        List<EmpDTO> ttList = empService.getEmpList(param);

        return ttList;
    }

    @TilesDynamic("base")
    @GetMapping("/emp/profile.hs")
    public String profile(@RequestParam(name = "id") String id, Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        log.error("/emp/profile @@@@@@@@@");
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("empCd", id);
        request.setAttribute("langCd", langCd);
        model.addAttribute("EmpCdMain", id);
        model.addAttribute("empAllDTO", empService.getAllProfile(pra));

        return "/emp/profile";
    }

    @TilesDynamic("base")
    @GetMapping("/emp/addEmp.hs")
    public String addEmp(Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeCodeLists", codeService.getCodeList("workstatus",langCd));
        model.addAttribute("typeCodeListWorkType", codeService.getCodeList("worktype",langCd));
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstPo", positionService.getPositionList(langCd));
        return "/emp/add";
    }

    @TilesDynamic("base")
    @GetMapping("/emp/addSubEmp.hs")
    public String addSubEmp(Model model, @RequestParam(name = "id") String empCd, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeCodeLists", codeService.getCodeList("workstatus",langCd));
        model.addAttribute("typeCodeListWorkType", codeService.getCodeList("worktype",langCd));
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstPo", positionService.getPositionList(langCd));
        model.addAttribute("parentEmpId", empCd);
        //formart date
        EmpDTO empDTO = empService.getEdit(empCd);
        empDTO.setBirthDay(empDTO.getBirthDay().split(" ")[0]);
        empDTO.setEnterDate(empDTO.getEnterDate().split(" ")[0]);
        int parentIdCheck = empService.getCountParent(empCd);
        parentIdCheck += 1;
        String empParentId = empDTO.getEmpId() +"_"+ parentIdCheck;
        empDTO.setEmpId(empParentId);
        //end formart date

        model.addAttribute("empDTO", empDTO);
        return "/emp/subadd";
    }

    @RequestMapping(value = "/emp/addsubemp", method = RequestMethod.POST)
    @ResponseBody
    public String addSubEmp(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                            @RequestBody EmpDTO empDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        System.out.println("DXD:====> " + langCd);
        EmpDTO subEmpDTO = empService.getEdit(String.valueOf(empDTO.getEmpCd()));
        System.out.println("DXD:====> " + subEmpDTO);
        subEmpDTO.setEmpId(empDTO.getEmpId());
        subEmpDTO.setEnterDate(empDTO.getEnterDate().split(" ")[0]);
        subEmpDTO.setDeptCd(empDTO.getDeptCd());
        subEmpDTO.setPositionCd(empDTO.getPositionCd());
        subEmpDTO.setEmpName(empDTO.getEmpName());
        subEmpDTO.setEnterDate(empDTO.getEnterDate());
        subEmpDTO.setTranslationYn(empDTO.getTranslationYn());
        subEmpDTO.setAdminYn(empDTO.getAdminYn());
        subEmpDTO.setGubun(empDTO.getGubun());
        subEmpDTO.setStatus(empDTO.getStatus());
        subEmpDTO.setEmpParentCd(empDTO.getEmpCd());
        System.out.println("DXD:====> "+ subEmpDTO);
        int result = empService.InsertEmp(subEmpDTO);
        return "ok";
    }

    @ResponseBody
    @PostMapping(value = "/emp/deleteEmp.hs", produces = "application/json; charset=UTF-8")
    public int empIdCheck(int empCd) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/deleteEmp @@@@@@@@@");
        int Del = empService.deleteEmp(empCd);
        log.error("{}", new Gson().toJson(user));
        return 1;
    }


    @RequestMapping(value = "/emp/emp/addemp", method = RequestMethod.POST)
    @ResponseBody
    public String addEmp(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody EmpDTO empDTO) {
        // check exist truoc
        int sl = empService.checkExist(empDTO.getEmpId());
        System.out.println("DXD:-> "+sl);
        if (sl < 1) {
            //them o day
            String langCd = LocaleContextHolder.getLocale().toString();
            empDTO.setLangCd(langCd);
            empDTO.setEmpPw(SHA256Encryptor.encryptor(empDTO.getEmpPw()));
            empDTO.setEmpParentCd(null);
            int result = empService.InsertEmp(empDTO);
            return "a";
        } else {
            return "b";
        }
    }



    @RequestMapping(value = "/emp/checkexitusername", method = RequestMethod.GET)
    @ResponseBody
    public int checkExit(@RequestBody EmpDTO empDTO){
        int sl = empService.checkExist(empDTO.getEmpId());
        return sl;
    }

    @TilesDynamic("base")
    @GetMapping("/emp/editEmp.hs")
    public String editEmp(@RequestParam(name = "empCd") String empCd, Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeCodeLists", codeService.getCodeList("workstatus",langCd));
        model.addAttribute("typeCodeListWorkType", codeService.getCodeList("worktype",langCd));
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstPo", positionService.getPositionList(langCd));

        //formart date
        System.out.println("DXD:  "+ empCd);
        EmpDTO empDTO = empService.getEdit(empCd);
        System.out.println("DXD:  "+ empDTO);
        empDTO.setBirthDay(empDTO.getBirthDay().split(" ")[0]);
        empDTO.setEnterDate(empDTO.getEnterDate().split(" ")[0]);
        //end formart date

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("empCd", empCd);
        model.addAttribute("empDTO", empDTO);
        System.out.println("DXD:  "+ empDTO);

        return "/emp/edit";
    }

    @RequestMapping(value = "/emp/emp/suaemp", method = RequestMethod.POST)
    @ResponseBody
    public String sua(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                      @RequestBody EmpDTO empDTO) {
        //them o day
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO empParentDTO = empService.checkExitParentEmp(empDTO);
        System.out.println("DXD: "+ empParentDTO.getEmpParentCd());

        empDTO.setEmpParentCd(empParentDTO.getEmpParentCd());

        if (!empDTO.getEmpPw().equals("")){
            empDTO.setEmpPw(SHA256Encryptor.encryptor(empDTO.getEmpPw()));
            empDTO.setLangCd(langCd);
        }
        
        int rsUpdate = empService.updateEmp(empDTO);
        int result;
        if (empParentDTO.getEmpParentCd() != null)
        {
            result = empParentDTO.getEmpParentCd();
        } else {
            result = empParentDTO.getEmpCd();
        }
        System.out.println("DXD: "+ result);
        return String.valueOf(result);
    }
    @ResponseBody
    @PostMapping(value = "/emp/delete.hs", produces = "application/json; charset=UTF-8")
    public int delete(String lstChecked) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {
            str += " emp_cd=" + arrOfStr[i].toString() + " or";
        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        int Del = empService.deleteEmp(param);
        log.error("{}", new Gson().toJson(user));
        return 1;
    }

    @TilesDynamic("base")
    @GetMapping("/emp/mypage.hs")
    public String goMypage(Model model) {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmpDTO emp = (EmpDTO)user.getEmp();
        log.error("/emp/profile @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeCodeLists", codeService.getCodeList("workstatus",langCd));
        model.addAttribute("typeCodeListWorkType", codeService.getCodeList("worktype",langCd));
        model.addAttribute("lstDept", deptService.getDeptLang(langCd));
        model.addAttribute("lstPo", positionService.getPositionList(langCd));

        //formart date
        EmpDTO empDTO = empService.getEdit(String.valueOf(emp.getEmpCd()));
        empDTO.setBirthDay(empDTO.getBirthDay().split(" ")[0]);
        empDTO.setEnterDate(empDTO.getEnterDate().split(" ")[0]);
        //end formart date

        model.addAttribute("empDTO", empDTO);
        return "/mypage/detail";
    }

    @ResponseBody
    @PostMapping("/emp/mypage.hs")
    public String doChangeMyinfo(Model model, @RequestParam(name = "oldEmpPw") String oldEmpPw, @RequestParam(name = "newEmpPw") String newEmpPw) {
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmpDTO emp = (EmpDTO)user.getEmp();
        return empService.updateMyinfo(emp,oldEmpPw,newEmpPw);
    }

    @RequestMapping(value = "/emp/getemplistbycustomid", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Object getEmpListByCustomID(@RequestParam(name = "empList") String empList){
        String langCd = LocaleContextHolder.getLocale().toString();
        System.out.println("DXD: " + empList);
        if (empList.equals("") ){
            return null;
        }else {
            String proEmpList = "(" + empList + ")";
            HashMap param = new HashMap();
            param.put("empList", proEmpList);
            param.put("langCd", langCd);
            System.out.println("DXD: "+ param);
            List<EmpDTO> resultEmpList = empService.getEmpListByCustomID(param);
            //for (int i = 0; i < resultEmpList.size(); i++){}
            System.out.println("DXD: ->> "+ resultEmpList);
            return resultEmpList;
        }
    }
}
