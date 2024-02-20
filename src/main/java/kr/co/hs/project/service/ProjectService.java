package kr.co.hs.project.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.FileUploadDTO;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    ICommonDao iCommonDao;

    @Autowired
    DeptService deptService;

    @Autowired
    NoticeService noticeService;
    final EmpService empService;

    @Autowired
    ServletContext context;

    @Autowired
    private MessageSource messageSource;

    public List<ProjectMainDTO> projectMainList(Integer pageNumber, Integer pageDisplayLength, String searchParameter
            , String dateSql, String deptSearch,String inputSearchSql, String translationStatus, String str, String approval, String type){
        int startRow = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        ProjectMainDTO projectMainDTO = new ProjectMainDTO();
        projectMainDTO.setLangCd(langCd);
        projectMainDTO.setStartRow(startRow);
        projectMainDTO.setRecordsPerPage(pageDisplayLength);
        projectMainDTO.setTxtSearch(searchParameter.trim());
        projectMainDTO.setDateSql(dateSql);
        projectMainDTO.setDeptSearch(deptSearch);
        projectMainDTO.setTranslationStatus(translationStatus);
        projectMainDTO.setInputSearchSql(inputSearchSql);
        projectMainDTO.setStr(str);
        projectMainDTO.setApproval(approval);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        if(empDTO.getAdminYn().equals("N") && empDTO.getTranslationYn().equals("N") && empDTO.getPositionCd() > 7 && checkExitEmpProjectAuth() == 0){

            if (empDTO.getAccountingYn().equals("Y")){
                projectMainDTO.setRule("Y");
            }else {
                projectMainDTO.setRule("N");
            }
        }

        projectMainDTO.setEmpCd(empDTO.getEmpCd());

        if (type.equals("datatable")){
            return iCommonDao.selectList("ProjectMain.ProjectMainList", projectMainDTO);
        }else if (type.equals("excel")){
            return iCommonDao.selectList("ProjectMain.selectMainProjectNExcel", projectMainDTO);
        } else {
            return iCommonDao.selectList("ProjectMain.selectMainProjectYExcel", projectMainDTO);
        }
    }

    public int countRecordProjectMain(String searchParameter
            , String dateSql, String inputSearchSql, String translationStatus, String str, String approval, String deptSearch){
        ProjectMainDTO projectMainDTO = new ProjectMainDTO();
        String langCd = LocaleContextHolder.getLocale().toString();
        projectMainDTO.setLangCd(langCd);
        projectMainDTO.setTxtSearch(searchParameter.trim());
        projectMainDTO.setDateSql(dateSql);
        projectMainDTO.setDeptSearch(deptSearch);
        projectMainDTO.setTranslationStatus(translationStatus);
        projectMainDTO.setInputSearchSql(inputSearchSql);
        projectMainDTO.setStr(str);
        projectMainDTO.setApproval(approval);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        if(empDTO.getAdminYn().equals("N") && empDTO.getTranslationYn().equals("N") && empDTO.getPositionCd() > 7 && checkExitEmpProjectAuth() == 0){
            if (empDTO.getAccountingYn().equals("Y")){
                projectMainDTO.setRule("Y");
            }else {
                projectMainDTO.setRule("N");
            }
        }

        projectMainDTO.setEmpCd(empDTO.getEmpCd());

        return iCommonDao.selectCount("ProjectMain.CountRecordProjectMain", projectMainDTO);
    }

    public String searchCommonProject(int pageDisplayLength, String start_date, String end_date, String deptCd
            , String optionSearch, String inputSearch, String translationStatusGet, String orderColumn
            , String typeOrder, String iDisplayStart, String searchParameter, String approval, int type){
        String langCd = LocaleContextHolder.getLocale().toString();

        int pageNumber = 0;

        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date

        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptString = deptService.selectDeptLevelbyDeptCd(para);
        String deptSearch="";
        if (deptString != null && deptString.length() > 0) {
            deptSearch=" AND GET_DEPT_LEVEL_NAME(pla_dept_cd, '"+langCd+"') LIKE '%"+deptString+"%'";
        }

        System.out.println("DXD: "+ deptSearch);
        String inputSearchSql = "";
        if (!inputSearch.equals("") && !inputSearch.equals(null)){
            if (optionSearch.equals("emp_name")){
                inputSearchSql=" AND emp_name LIKE '%"+inputSearch+"%' ";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND position_name  LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("title")){
                inputSearchSql=" AND title  LIKE '%"+inputSearch+"%'";
            }
            else{
                inputSearchSql="";
            }
        }
        //end input search

        //xuly translationStatus

        String translationStatus ="";
        if(translationStatusGet.equals("Y")){
            translationStatus=" AND TRANSLATION_STATUS = 1 ";
        }else if(translationStatusGet.equals("N")){
            translationStatus=" AND TRANSLATION_STATUS = 0 ";
        }else{
            translationStatus ="";
        }
        //end translationStatus

        //PROJECT STATUS

        if (approval.equals("Y"))
            approval = " AND project_status = 'Y' ";
        else if (approval.equals("N"))
            approval = " AND project_status = 'N' ";
        else
            approval = "";

        //END PROJECT STATUS

        //sort
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
        //end sort
        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Fetch search parameter
        // Create page list data

        List<ProjectMainDTO> ttList;
        if (type == 1){
            ttList = projectMainList(pageNumber, pageDisplayLength, searchParameter, dateSql, deptSearch
                    , inputSearchSql, translationStatus, str, approval, "datatable");
        }else if(type == 2){
            ttList = projectMainList(pageNumber, pageDisplayLength, searchParameter, dateSql, deptSearch
                    , inputSearchSql, translationStatus, str, approval, "excel");
        } else {
            ttList = projectMainList(pageNumber, pageDisplayLength, searchParameter, dateSql, deptSearch
                    , inputSearchSql, translationStatus, str, approval, "excelY");
        }


        int sum_row= countRecordProjectMain(searchParameter.trim(), dateSql, inputSearchSql, translationStatus, str, approval, deptSearch);

        ProjectMainDTO projectMainDTO = new ProjectMainDTO();
        projectMainDTO.setITotalDisplayRecords(sum_row);

        sum_row = countTotalProjectMain();
        projectMainDTO.setITotalRecords(sum_row);
        projectMainDTO.setAaData(ttList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(projectMainDTO);

        return json;
    }

    public List<ProjectMainDTO> selectListExportProject(int pageDisplayLength, String start_date, String end_date, String deptCd
            , String optionSearch, String inputSearch, String translationStatusGet, String orderColumn
            , String typeOrder, String iDisplayStart, String searchParameter, String approval, String type){
        String langCd = LocaleContextHolder.getLocale().toString();

        int pageNumber = 0;

        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date

        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptString = deptService.selectDeptLevelbyDeptCd(para);
        String deptSearch="";
        if (deptString != null && deptString.length() > 0) {
            deptSearch=" AND GET_DEPT_LEVEL_NAME(pla_dept_cd, '"+langCd+"') LIKE '%"+deptString+"%'";
        }
        String inputSearchSql = "";
        if (!inputSearch.equals("") && !inputSearch.equals(null)){
            if (optionSearch.equals("emp_name")){
                inputSearchSql=" AND emp_name LIKE '%"+inputSearch+"%' ";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND position_name  LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("title")){
                inputSearchSql=" AND title  LIKE '%"+inputSearch+"%'";
            }else{
                inputSearchSql="";
            }
        }
        //end input search

        //xuly translationStatus

        String translationStatus ="";
        if(translationStatusGet.equals("Y")){
            translationStatus=" AND TRANSLATION_STATUS = 1 ";
        }else if(translationStatusGet.equals("N")){
            translationStatus=" AND TRANSLATION_STATUS = 0 ";
        }else{
            translationStatus ="";
        }
        //end translationStatus

        //PROJECT STATUS

        if (approval.equals("Y"))
            approval = " AND project_status = 'Y' ";
        else if (approval.equals("N"))
            approval = " AND project_status = 'N' ";
        else
            approval = "";

        //END PROJECT STATUS

        //sort
        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;
        //end sort
        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Fetch search parameter
        // Create page list data
        List<ProjectMainDTO> ttList = new LinkedList<>();
        if (type.equals("N")){
            ttList = projectMainList(pageNumber, pageDisplayLength, searchParameter, dateSql, deptSearch
                    , inputSearchSql, translationStatus, str, approval, "excel");
        }else {
            ttList = projectMainList(pageNumber, pageDisplayLength, searchParameter, dateSql, deptSearch
                    , inputSearchSql, translationStatus, str, approval, "excelY");
        }
        return ttList;
    }


    public int countTotalProjectMain(){
        return iCommonDao.selectCount("ProjectMain.CountTotalProjectMain", null);
    }

    public void writeMainProject(ProjectMainDTO projectMainDTO, EmpDTO empDTO){
        projectMainDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        projectMainDTO.setEmpCd(empDTO.getEmpCd());
        projectMainDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
        projectMainDTO.setDeptCd(empDTO.getDeptCd());
        projectMainDTO.setPositionCd(empDTO.getPositionCd());
        iCommonDao.insertData("ProjectMain.writeMainProject", projectMainDTO);
    }

    public void writeProjectLeaderAuth(String leaderName, String typeLeader, String createId){

        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("langCd", langCd);
        param.put("empCd", empService.findLeaderName(leaderName));
        EmpDTO empDTO = empService.GetProfile(param);
        ProjectLeaderAuthDTO projectLeaderAuthDTO = new ProjectLeaderAuthDTO();
        projectLeaderAuthDTO.setLeaderType(typeLeader);
        projectLeaderAuthDTO.setEmpCd(empDTO.getEmpCd());
        projectLeaderAuthDTO.setDeptCd(empDTO.getDeptCd());
        projectLeaderAuthDTO.setPositionCd(empDTO.getPositionCd());
        projectLeaderAuthDTO.setCreateId(createId);

        iCommonDao.insertData("ProjectMain.writeProjectLeaderAuth", projectLeaderAuthDTO);
    }

    public int processWriteMainProject(ProjectMainDTO projectMainDTO, EmpDTO empDTO){
        if (empService.checkExitEmpDept(projectMainDTO.getLeaderProjectName()) == false
                || empService.checkExitEmpDept(projectMainDTO.getLeaderAccountingName()) == false){
            return 0;
        }else {
            writeMainProject(projectMainDTO,empDTO);
            writeProjectLeaderAuth(projectMainDTO.getLeaderProjectName(), "1", empDTO.getEmpCd().toString());
            writeProjectLeaderAuth(projectMainDTO.getLeaderAccountingName(), "2", empDTO.getEmpCd().toString());

            /*
            * insert deposit
            * */
            int pjId = iCommonDao.selectCount("ProjectMain.selectMaxProjectId", null);
            DayProjectDTO dayProjectDTO = new DayProjectDTO();
            dayProjectDTO.setTitle(projectMainDTO.getStr());
            dayProjectDTO.setProjectPrice(projectMainDTO.getAdvanceAmount());
            dayProjectDTO.setLangCd(LocaleContextHolder.getLocale().toString());

            int deptChangeHistoryId=iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO);

            dayProjectDTO.setEmpCd(empDTO.getEmpCd());
            dayProjectDTO.setDeptCd(empDTO.getDeptCd());
            dayProjectDTO.setPositionCd(empDTO.getPositionCd());
            dayProjectDTO.setDeptChangeHistoryId(deptChangeHistoryId);
            dayProjectDTO.setPjId(pjId);

            dayProjectDTO.setSpentType("default");
            dayProjectDTO.setAdvanceAmount(dayProjectDTO.getProjectPrice());
            dayProjectDTO.setRemainAmount(dayProjectDTO.getProjectPrice());

            dayProjectDTO.setDayProjectStatus("N");
            dayProjectDTO.setUseYn("Y");

            iCommonDao.insertData("ProjectMain.InsertFirstDeposit", dayProjectDTO);

            String date = java.time.LocalDate.now().toString();
            if (projectMainDTO.getProjectStartDate().equals(date)){
                InfoDTO infoDTO =iCommonDao.selectOne("ProjectMain.getInfoProject",pjId);
                dayProjectDTO.setTitle("Chi tiêu " + date);
                dayProjectDTO.setSpentType("spent");
                dayProjectDTO.setProjectPrice(0);
                dayProjectDTO.setAdvanceAmount(infoDTO.getNowPrice());
                dayProjectDTO.setRemainAmount(infoDTO.getRemain());

                iCommonDao.insertData("ProjectMain.InsertDayProject",dayProjectDTO);
            }
            return 1;
        }
    }

    public String selectLeaderProject(ProjectMainDTO projectMainDTO){
        ProjectMainDTO leaderProjectDTO = iCommonDao.selectOne("ProjectMain.selectOneProjectLeader", projectMainDTO);
        return leaderProjectDTO.getLeaderProjectName();
    }

    public String selectLeaderAccounting(ProjectMainDTO projectMainDTO){
        ProjectMainDTO leaderAccountingDTO = iCommonDao.selectOne("ProjectMain.selectOneAccountingLeader", projectMainDTO);
        return leaderAccountingDTO.getLeaderAccountingName();
    }

    public ProjectMainDTO selectOneMainProject(int id){
        ProjectMainDTO paramProjectMainDTO = new ProjectMainDTO();
        paramProjectMainDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        paramProjectMainDTO.setPjId(id);
        ProjectMainDTO projectMainDTO = iCommonDao.selectOne("ProjectMain.selectOneMainProject", paramProjectMainDTO);
        projectMainDTO.setLeaderProjectName(selectLeaderProject(paramProjectMainDTO));
        projectMainDTO.setLeaderAccountingName(selectLeaderAccounting(paramProjectMainDTO));
        return projectMainDTO;
    }

    public void updateProjectLeaderAuth(String leaderName, String typeLeader, String createId, int id){
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("langCd", langCd);
        param.put("empCd", empService.findLeaderName(leaderName));
        EmpDTO empDTO = empService.GetProfile(param);
        ProjectLeaderAuthDTO projectLeaderAuthDTO = new ProjectLeaderAuthDTO();
        projectLeaderAuthDTO.setPjId(id);
        projectLeaderAuthDTO.setLeaderType(typeLeader);
        projectLeaderAuthDTO.setEmpCd(empDTO.getEmpCd());
        projectLeaderAuthDTO.setDeptCd(empDTO.getDeptCd());
        projectLeaderAuthDTO.setPositionCd(empDTO.getPositionCd());
        projectLeaderAuthDTO.setCreateId(createId);

        iCommonDao.insertData("ProjectMain.updateProjectLeaderAuth", projectLeaderAuthDTO);
    }

    /**
     * update mainproject and then call update leader project
     */
    public void updateMainProject(ProjectMainDTO projectMainDTO, EmpDTO empDTO){

        projectMainDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        iCommonDao.updateData("ProjectMain.updateMainProject", projectMainDTO);

        if (!selectLeaderProject(projectMainDTO).equals(projectMainDTO.getLeaderProjectName())){
            updateProjectLeaderAuth(projectMainDTO.getLeaderProjectName()
                    , "1", empDTO.getEmpCd().toString(), projectMainDTO.getPjId());
        }
        if (!selectLeaderAccounting(projectMainDTO).equals(projectMainDTO.getLeaderAccountingName())){
            updateProjectLeaderAuth(projectMainDTO.getLeaderAccountingName()
                    , "2", empDTO.getEmpCd().toString(), projectMainDTO.getPjId());
        }
    }

    public int processUpdateMainProject(ProjectMainDTO projectMainDTO, EmpDTO empDTO){
        if (empService.checkExitEmpDept(projectMainDTO.getLeaderProjectName()) == false
                || empService.checkExitEmpDept(projectMainDTO.getLeaderAccountingName()) == false){
            return 0;
        }else {
            updateMainProject(projectMainDTO, empDTO);

            String date = java.time.LocalDate.now().toString();

            int checkFirstSpent = iCommonDao.selectCount("ProjectMain.checkFistSpent", projectMainDTO);
            if(projectMainDTO.getProjectStartDate().equals(date) && checkFirstSpent == 0){
                String langCd = LocaleContextHolder.getLocale().toString();
                int deptChangeHistoryId=iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO);
                DayProjectDTO dayProjectDTO = new DayProjectDTO();
                InfoDTO infoDTO =iCommonDao.selectOne("ProjectMain.getInfoProject",projectMainDTO.getPjId());

                dayProjectDTO.setLangCd(langCd);
                dayProjectDTO.setPjId(projectMainDTO.getPjId());
                dayProjectDTO.setTitle("Chi tiêu " + date);
                dayProjectDTO.setEmpCd(empDTO.getEmpCd());
                dayProjectDTO.setDeptCd(empDTO.getDeptCd());
                dayProjectDTO.setPositionCd(empDTO.getPositionCd());
                dayProjectDTO.setDeptChangeHistoryId(deptChangeHistoryId);
                dayProjectDTO.setSpentType("spent");
                dayProjectDTO.setDayProjectStatus("N");
                dayProjectDTO.setUseYn("Y");
                dayProjectDTO.setProjectPrice(0);
                dayProjectDTO.setAdvanceAmount(infoDTO.getNowPrice());
                dayProjectDTO.setRemainAmount(infoDTO.getRemain());

                iCommonDao.insertData("ProjectMain.InsertDayProject",dayProjectDTO);
            }
            return 1;
        }
    }

    public int deleteMainProject(String lstChecked){
        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");

        for (int i = 0; i < arrOfStr.length; i++) {
            str += " PJ_ID =" + arrOfStr[i].toString() + " or";
        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        iCommonDao.updateData("ProjectMain.deleteMainProject", param);
        return 1;
    }

    /*
    * approval project
    *
    * */
    public int processApprovalMainProject(String lstChecked){
        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");

        for (int i = 0; i < arrOfStr.length; i++) {
            str += " PJ_ID =" + arrOfStr[i].toString() + " or";
        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        iCommonDao.updateData("ProjectMain.approvalProject", param);
        return 1;
    }

    public List<ProjectLeaderAuthDTO> selectLeaderMainProject(ProjectLeaderAuthDTO projectLeaderAuthDTO){
        projectLeaderAuthDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        return iCommonDao.selectList("ProjectMain.selectLeaderMainProject", projectLeaderAuthDTO);
    }

    /*
     * end main project
     * */


    public String getDayProjectDatatables(int pjId, int pageDisplayLength, String start_date, String end_date, String deptCd, String optionSearch, String inputSearch, String translationStatusGet,
                                          String dayProjectStatus, String numberOrderColumn, String orderColumn, String typeOrder, String iDisplayStart, String searchParameter, HttpServletRequest request, int type){
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        //xu ly date
        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(dp.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(dp.reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(dp.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date
        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptString=deptService.selectDeptLevelbyDeptCd(para);
        String deptSearch="";
        if (deptString != null && deptString.length() > 0) {
            deptSearch=" AND GET_DEPT_LEVEL_NAME(dp.dept_cd, '"+langCd+"') LIKE '%"+deptString+"%'";
        }
        //xuly translationStatus

        String translationStatus ="";
        if(translationStatusGet.equals("Y")){
            translationStatus=" AND ( SELECT COUNT(dwt.dp_id) FROM day_project_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE dp_id=dp.dp_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus=" AND ( SELECT COUNT(dwt.dp_id) FROM day_project_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE dp_id=dp.dp_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus

        //xu ly day Project Status
        String dayProjectStatusSql="";
        if(dayProjectStatus.equals("Y")){
            dayProjectStatusSql=" AND day_project_status='Y' ";
        }else if(dayProjectStatus.equals("N")){
            dayProjectStatusSql=" AND day_project_status='N' ";
        }else{
            dayProjectStatusSql="";
        }
        //end day Project Status

        //xu ly input search
        String inputSearchSql="";
        if(!inputSearch.equals("") && !inputSearch.equals(null)){
            if(optionSearch.equals("emp_name")){
                inputSearchSql=" AND GET_EMP_NAME(dp.emp_cd, '"+langCd+"') LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND GET_POSITION_NAME(dp.position_cd, '"+langCd+"') LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("project_name")){
                inputSearchSql="  AND p.title LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("title")){
                inputSearchSql="  AND dp.title LIKE '%"+inputSearch+"%'";
            }
            else{
                inputSearchSql="";
            }
        }
        //end input search

        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;

        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Create page list data
        List<DayProjectDTO> ttList;
        if (type == 1){
            ttList = getDayProjectServices(pjId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectStatusSql,inputSearchSql,str, "datatable");
        }else {
            ttList = getDayProjectServices(pjId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectStatusSql,inputSearchSql,str, "excel");
        }

        DayProjectDTO dayProjectDTO = new DayProjectDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        DayProjectDTO param = new DayProjectDTO();
        param.setPjId(pjId);
        param.setEmpCd(emp.getEmpCd());
        param.setLangCd(langCd);
        param.setTxtSearch(searchParameter.trim());
        param.setDateSql(dateSql);
        param.setDeptSearch(deptSearch);
        param.setTranslationStatus(translationStatus);
        param.setDayProjectStatusSql(dayProjectStatusSql);
        param.setInputSearchSql(inputSearchSql);
        param.setTranslationYnPara(emp.getTranslationYn());
        int sum_row =getSumRowDayProject(param);
        // Set Total display record
        dayProjectDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.setTxtSearch("");
        param.setDateSql("");
        param.setDeptSearch("");
        param.setTranslationStatus("");
        param.setDayProjectStatusSql("");
        param.setInputSearchSql("");

        sum_row =getSumRowDayProject(param);

        dayProjectDTO.setITotalRecords(sum_row);
        dayProjectDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dayProjectDTO);

        return json;
    }

    //get all list for export excel
    public List<DayProjectDTO> selectListExportDayProject(int pjId, int pageDisplayLength, String start_date, String end_date, String deptCd
                                                            , String optionSearch, String inputSearch, String translationStatusGet
                                                            , String dayProjectStatus, String orderColumn, String typeOrder
                                                            , String iDisplayStart, String searchParameter, HttpServletRequest request){
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        //xu ly date
        String dateSql="";
        if(!start_date.equals("") && !end_date.equals("") && !start_date.equals(null) && !end_date.equals(null)){
            dateSql=" AND (CONVERT(dp.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE) and CONVERT(dp.reg_dt , DATE) <= CONVERT('"+end_date.trim()+"' , DATE))";
        }else if(!start_date.equals("") && !start_date.equals(null) && (end_date.equals("") ||end_date.equals(null))){
            dateSql=" AND CONVERT(dp.reg_dt , DATE) >= CONVERT('"+start_date.trim()+"' , DATE)";
        }else{
            dateSql="";
        }
        //end xu ly date
        HashMap para = new HashMap();
        para.put("langCd",langCd);
        para.put("deptCd",deptCd);
        String deptString=deptService.selectDeptLevelbyDeptCd(para);
        String deptSearch="";
        if (deptString != null && deptString.length() > 0) {
            deptSearch=" AND GET_DEPT_LEVEL_NAME(dp.dept_cd, '"+langCd+"') LIKE '%"+deptString+"%'";
        }
        //xuly translationStatus

        String translationStatus ="";
        if(translationStatusGet.equals("Y")){
            translationStatus=" AND ( SELECT COUNT(dwt.dp_id) FROM day_project_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE dp_id=dp.dp_id AND t.use_yn='Y') >0";
        }else if(translationStatusGet.equals("N")){
            translationStatus=" AND ( SELECT COUNT(dwt.dp_id) FROM day_project_translation dwt left JOIN translation t on dwt.translation_id = t.translation_id WHERE dp_id=dp.dp_id AND t.use_yn='Y') =0";
        }else{
            translationStatus ="";
        }
        //end translationStatus

        //xu ly day Project Status
        String dayProjectStatusSql="";
        if(dayProjectStatus.equals("Y")){
            dayProjectStatusSql=" AND day_project_status='Y' ";
        }else if(dayProjectStatus.equals("N")){
            dayProjectStatusSql=" AND day_project_status='N' ";
        }else{
            dayProjectStatusSql="";
        }
        //end day Project Status

        //xu ly input search
        String inputSearchSql="";
        if(!inputSearch.equals("") && !inputSearch.equals(null)){
            if(optionSearch.equals("emp_name")){
                inputSearchSql=" AND GET_EMP_NAME(dp.emp_cd, '"+langCd+"') LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND GET_POSITION_NAME(dp.position_cd, '"+langCd+"') LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("project_name")){
                inputSearchSql="  AND p.title LIKE '%"+inputSearch+"%'";
            }else if(optionSearch.equals("title")){
                inputSearchSql="  AND dp.title LIKE '%"+inputSearch+"%'";
            }
            else{
                inputSearchSql="";
            }
        }
        //end input search

        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;

        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Create page list data
        List<DayProjectDTO> ttList = getDayProjectServices(pjId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectStatusSql,inputSearchSql,str, "excel");

        return ttList;
    }



    /*
     * select project auth
     * */
    public List<ProjectAuthDTO> selectProjectAuth(){
        ProjectAuthDTO projectAuthDTO = new ProjectAuthDTO();
        projectAuthDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        return iCommonDao.selectList("ProjectMain.selectProjectAuth", projectAuthDTO);
    }

    /*
    * Authentication for project function
    * chech exit emp from project auth table
    * */
    public int checkExitEmpProjectAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        return iCommonDao.selectCount("ProjectMain.checkExitEmpProjectAuth", empDTO);
    }

    /*
    * insert project auth
    * */
    public int insertProjectAuth(String empDept){
        if (empService.checkExitEmpDept(empDept) == false){
            return 0;
        }else {
            String langCd = LocaleContextHolder.getLocale().toString();
            HashMap param = new HashMap();
            param.put("langCd", langCd);
            param.put("empCd", empService.findLeaderName(empDept));
            EmpDTO empDTO = empService.GetProfile(param);



            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            EmpDTO empRequest = ((UserDetail) authentication.getPrincipal()).getEmp();

            ProjectAuthDTO projectAuthDTO = new ProjectAuthDTO();
            projectAuthDTO.setEmpCd(empDTO.getEmpCd());
            projectAuthDTO.setDeptCd(empDTO.getDeptCd());
            projectAuthDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
            projectAuthDTO.setPositionCd(empDTO.getPositionCd());
            projectAuthDTO.setCreateId(String.valueOf(empRequest.getEmpCd()));

            int exitNumber = iCommonDao.selectCount("ProjectMain.checkExitProjectAuth", projectAuthDTO);
            if (exitNumber != 0){
                return 0;
            }
            iCommonDao.insertData("ProjectMain.insertProjectAuth", projectAuthDTO);
            return 1;
        }
    }

    public void deleteProjectAuth(int id){
        ProjectAuthDTO projectAuthDTO = new ProjectAuthDTO();
        projectAuthDTO.setProjectAuthId(id);
        iCommonDao.updateData("ProjectMain.deleteProjectAuth", projectAuthDTO);
    }

    public List<DayProjectDTO> getDayProjectServices(Integer pjId,Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String translationStatus, String dayProjectStatusSql, String inputSearchSql, String str, String type){
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        DayProjectDTO param = new DayProjectDTO();
        param.setPjId(pjId);
        param.setEmpCd(emp.getEmpCd());
        param.setLangCd(langCd);
        param.setTxtSearch(searchParameter.trim());
        param.setStartRow(a);
        param.setRecordsPerPage(pageDisplayLength);
        param.setDateSql(dateSql);
        param.setDeptSearch(deptSearch);
        param.setTranslationStatus(translationStatus);
        param.setDayProjectStatusSql(dayProjectStatusSql);
        param.setInputSearchSql(inputSearchSql);
        param.setTranslationYnPara(emp.getTranslationYn());
        param.setAdminYnPara(emp.getAdminYn());
        param.setStr(str);

        if (type.equals("excel")){
            return iCommonDao.selectList("ProjectMain.getDayProjectListExcelExport", param);
        }else
            return iCommonDao.selectList("ProjectMain.getDayProjectList", param);
    }
    public int getSumRowDayProject(DayProjectDTO param){
        return iCommonDao.selectOne("ProjectMain.getSumRowDayProject",param);
    }

    public InfoDTO getInfoProject(int pjId){
        return iCommonDao.selectOne("ProjectMain.getInfoProject",pjId);
    }

    public DayProjectDTO getInfoProjectByID(int pjId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String langCd = LocaleContextHolder.getLocale().toString();

        DayProjectDTO dayProjectDTO= new DayProjectDTO();

        dayProjectDTO.setPjId(pjId);
        dayProjectDTO.setLangCd(langCd);
        dayProjectDTO.setDeptCd(empDTO.getDeptCd());
        dayProjectDTO.setPositionCd(empDTO.getPositionCd());
        dayProjectDTO.setEmpCd(empDTO.getEmpCd());

        dayProjectDTO=iCommonDao.selectOne("ProjectMain.getInfoProjectByID",dayProjectDTO);

        return dayProjectDTO;
    }

    public int InsertDayProject(DayProjectDTO dayProjectDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String langCd = LocaleContextHolder.getLocale().toString();
        int deptChangeHistoryId=iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO);
        InfoDTO infoDTO =iCommonDao.selectOne("ProjectMain.getInfoProject",dayProjectDTO.getPjId());

        dayProjectDTO.setLangCd(langCd);
        dayProjectDTO.setEmpCd(empDTO.getEmpCd());
        dayProjectDTO.setDeptCd(empDTO.getDeptCd());
        dayProjectDTO.setPositionCd(empDTO.getPositionCd());
        dayProjectDTO.setDeptChangeHistoryId(deptChangeHistoryId);
        dayProjectDTO.setSpentType("spent");
        dayProjectDTO.setDayProjectStatus("N");
        dayProjectDTO.setUseYn("Y");
        dayProjectDTO.setProjectPrice(0);
        dayProjectDTO.setAdvanceAmount(infoDTO.getNowPrice());
        dayProjectDTO.setRemainAmount(infoDTO.getRemain());

        return iCommonDao.insertDataInt("ProjectMain.InsertDayProject",dayProjectDTO);
    }

    public int InsertDepositDayProject(DayProjectDTO dayProjectDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String langCd = LocaleContextHolder.getLocale().toString();
        int deptChangeHistoryId=iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO);
        InfoDTO infoDTO =iCommonDao.selectOne("ProjectMain.getInfoProject",dayProjectDTO.getPjId());
        int rs = iCommonDao.selectCount("ProjectMain.countDefault",dayProjectDTO.getPjId());

        dayProjectDTO.setLangCd(langCd);
        dayProjectDTO.setEmpCd(empDTO.getEmpCd());
        dayProjectDTO.setDeptCd(empDTO.getDeptCd());
        dayProjectDTO.setPositionCd(empDTO.getPositionCd());
        dayProjectDTO.setDeptChangeHistoryId(deptChangeHistoryId);
        if(rs>0){
            dayProjectDTO.setSpentType("deposit");
            dayProjectDTO.setAdvanceAmount(infoDTO.getNowPrice()+dayProjectDTO.getProjectPrice());
            dayProjectDTO.setRemainAmount(infoDTO.getRemain()+dayProjectDTO.getProjectPrice());
        }else{
            dayProjectDTO.setSpentType("default");
            dayProjectDTO.setAdvanceAmount(dayProjectDTO.getProjectPrice());
            dayProjectDTO.setRemainAmount(dayProjectDTO.getProjectPrice());
        }
        dayProjectDTO.setDayProjectStatus("N");
        dayProjectDTO.setUseYn("Y");

        return iCommonDao.insertDataInt("ProjectMain.InsertDayProject",dayProjectDTO);
    }

    public int countDefault(int pjId){
        return iCommonDao.selectCount("ProjectMain.countDefault",pjId);
    }

    public DayProjectDTO selectInfoDayProjectByID(DayProjectDTO dayProjectDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        dayProjectDTO.setLangCd(langCd);
        return iCommonDao.selectOne("ProjectMain.selectInfoDayProjectByID",dayProjectDTO);
    }

    public int editSpentDayProject(DayProjectDTO dayProjectDTO){
        return iCommonDao.updateData("ProjectMain.editSpentDayProject",dayProjectDTO);
    }

    public int editDepositDayProject(DayProjectDTO dayProjectDTO){
        //lay gia tri hien tai
        DayProjectDTO currentDTO = selectInfoDayProjectByID(dayProjectDTO);
        long currentAmount=currentDTO.getProjectPrice();
        //tinh toan
        dayProjectDTO.setDifferAmount(dayProjectDTO.getProjectPrice()-currentAmount);
        return iCommonDao.updateData("ProjectMain.editDepositDayProject",dayProjectDTO);
    }

    public int deleteDayProject(DayProjectDTO dayProjectDTO){
        int rs=0;
        String [] arr=dayProjectDTO.getInputSearchSql().split(",");
        for(int i=0;i< arr.length;i++){
            int pdId=Integer.parseInt(arr[i]);
            dayProjectDTO.setDpId(pdId);

            //lay gia tri hien tai
            DayProjectDTO currentDTO = selectInfoDayProjectByID(dayProjectDTO);

            //logic by SONG
//            if(currentDTO.getSpentType().equals("spent")){
//                rs=iCommonDao.updateData("ProjectMain.deleteDayProjectSpent",currentDTO);
//            }
//            else{
//                rs=iCommonDao.updateData("ProjectMain.deleteDayProjectDeposit",currentDTO);
//            }currentDTO = {DayProjectDTO@10137} "DayProjectDTO(rownum=0, dpId=333, pjId=75, langCd=ko, empCd=99, deptChangeHistoryId=149, deptCd=1, positionCd=1, spentType=Default, advanceAmount=100000000, projectPrice=100000000, remainAmount=100000000, title=Nạp tiền lần đầu, dayProjectStatus=N, regDt=2022-07-28 11:05:06.0, useYn=Y, projectName=호텔 호텔 호텔 호텔 , deptName=[VietKo Group], positionName=President , empName=Admin, numOfItem=0, file=null, differAmount=0, type=null, iTotalRecords=0, iTotalDisplayRecords=0, txtSearch=null, startRow=0, recordsPerPage=0, dateSql=null, inputSearchSql=null, str=null, deptSearch=null, translationStatus=null, dayProjectStatusSql=null, translationYnPara=null, adminYnPara=null, sEcho=null, sColumns=null, aaData=null)"

            if(currentDTO.getType().equals("spent") || currentDTO.getType().equals("default")){
                return 5;
            }
        }
        for(int i=0;i< arr.length;i++){
            int pdId=Integer.parseInt(arr[i]);
            dayProjectDTO.setDpId(pdId);
            //lay gia tri hien tai
            DayProjectDTO currentDTO = selectInfoDayProjectByID(dayProjectDTO);
            rs=iCommonDao.updateData("ProjectMain.deleteDayProjectDeposit",currentDTO);

        }
        return rs;
    }

    public int approveDayProject(DayProjectDTO dayProjectDTO){
        int rs=0;
        String [] arr=dayProjectDTO.getInputSearchSql().split(",");
        for(int i=0;i< arr.length;i++){
            int pdId=Integer.parseInt(arr[i]);
            dayProjectDTO.setDpId(pdId);

            //lay gia tri hien tai
            DayProjectDTO currentDTO = selectInfoDayProjectByID(dayProjectDTO);
            rs=iCommonDao.updateData("ProjectMain.approveDayProject",currentDTO);
        }
        return rs;
    }

    public long sumMoneyItem(int dpId){
        return iCommonDao.selectOne("ProjectMain.sumMoneyItem",dpId);
    }

    public String getDayProjectItemDatatables(int dpId, int pageDisplayLength, String numberOrderColumn, String orderColumn, String typeOrder, String iDisplayStart, String searchParameter, HttpServletRequest request, int type){
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        //xu ly date
        String dateSql="";

        //end xu ly date
        String deptSearch="";
        //xuly translationStatus
        String translationStatus ="";
        //end translationStatus

        //xu ly day Project Status
        String dayProjectItemStatusSql="";
        //end day Project Status

        //xu ly input search
        String inputSearchSql="";
        //end input search

        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;

        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Create page list data
        List<DayProjectItemDTO> ttList;
        if (type == 1){
             ttList = getDayProjectItemServices(dpId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectItemStatusSql,inputSearchSql,str, "datatable");
        }else {
            ttList = getDayProjectItemServices(dpId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectItemStatusSql,inputSearchSql,str, "excel");
        }

        DayProjectItemDTO dayProjectItemDTO = new DayProjectItemDTO();
        //select total row

        EmpDTO emp = empService.getSessionUserLogin(request);
        DayProjectItemDTO param = new DayProjectItemDTO();
        param.setDpId(dpId);
        param.setEmpCd(emp.getEmpCd());
        param.setLangCd(langCd);
        param.setTxtSearch(searchParameter.trim());
        param.setDateSql(dateSql);
        param.setDeptSearch(deptSearch);
        param.setTranslationStatus(translationStatus);
        param.setDayProjectItemStatusSql(dayProjectItemStatusSql);
        param.setInputSearchSql(inputSearchSql);
        param.setTranslationYnPara(emp.getTranslationYn());
        int sum_row =getSumRowDayProjectItem(param);
        // Set Total display record
        dayProjectItemDTO.setITotalDisplayRecords(sum_row);
        // Set Total record
        param.setTxtSearch("");
        param.setDateSql("");
        param.setDeptSearch("");
        param.setTranslationStatus("");
        param.setDayProjectItemStatusSql("");
        param.setInputSearchSql("");

        sum_row =getSumRowDayProjectItem(param);

        dayProjectItemDTO.setITotalRecords(sum_row);
        dayProjectItemDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dayProjectItemDTO);

        return json;
    }

    public List<DayProjectItemDTO> getDayProjectItemExportExcel(int dpId, int pageDisplayLength, String numberOrderColumn, String orderColumn, String typeOrder, String iDisplayStart, String searchParameter, HttpServletRequest request){
        String langCd = LocaleContextHolder.getLocale().toString();
        // Fetch the page number from client
        Integer pageNumber = 0;
        //xu ly date
        String dateSql="";

        //end xu ly date
        String deptSearch="";
        //xuly translationStatus
        String translationStatus ="";
        //end translationStatus

        //xu ly day Project Status
        String dayProjectItemStatusSql="";
        //end day Project Status

        //xu ly input search
        String inputSearchSql="";
        //end input search

        orderColumn=noticeService.replaceUptoLow(orderColumn);
        String str=orderColumn+" "+typeOrder;

        if (null != iDisplayStart)
            pageNumber = (Integer.valueOf(iDisplayStart) / pageDisplayLength) + 1;
        // Create page list data
        List<DayProjectItemDTO> ttList = getDayProjectItemServices(dpId,pageNumber, pageDisplayLength, searchParameter, request,dateSql,deptSearch,translationStatus,dayProjectItemStatusSql,inputSearchSql,str, "excel");


        return ttList;
    }

    public List<DayProjectItemDTO> getDayProjectItemServices(Integer dpId, Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String translationStatus, String dayProjectStatusSql, String inputSearchSql, String str, String type){
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);
        DayProjectItemDTO param = new DayProjectItemDTO();
        param.setDpId(dpId);
        param.setEmpCd(emp.getEmpCd());
        param.setLangCd(langCd);
        param.setTxtSearch(searchParameter.trim());
        param.setStartRow(a);
        param.setRecordsPerPage(pageDisplayLength);
        param.setDateSql(dateSql);
        param.setDeptSearch(deptSearch);
        param.setTranslationStatus(translationStatus);
        param.setDayProjectItemStatusSql(dayProjectStatusSql);
        param.setInputSearchSql(inputSearchSql);
        param.setTranslationYnPara(emp.getTranslationYn());
        param.setAdminYnPara(emp.getAdminYn());
        param.setStr(str);

        if (type.equals("excel")){
            return iCommonDao.selectList("ProjectMain.getDayProjectItemListExcelExport", param);
        } else {
            return iCommonDao.selectList("ProjectMain.getDayProjectItemList", param);
        }

    }

    public int getSumRowDayProjectItem(DayProjectItemDTO param){
        return iCommonDao.selectOne("ProjectMain.getSumRowDayProjectItem",param);
    }

    public int addDayProjectItem(DayProjectItemDTO dayProjectItemDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String langCd = LocaleContextHolder.getLocale().toString();
        int deptChangeHistoryId=iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO);

        if(dayProjectItemDTO.getFile()!=null){
            String fileName=java.time.LocalDateTime.now().toString();
            fileName=fileName.replace(":","").replace(" ","").replace(".","").replace("-","");
            //xu ly o day
            //dua anh vao thu muc
            try{
                MultipartFile multipartFile = dayProjectItemDTO.getFile();
                fileName =fileName+"_"+ multipartFile.getOriginalFilename();
                File file = new File(context.getRealPath("resources/Upload/img_project"), fileName);
                multipartFile.transferTo(file);

                File fileAfter=resizeImage(file, 700, 700,fileName);
            }catch (Exception e){}
            dayProjectItemDTO.setImgPath("/resources/Upload/img_project/"+fileName);
        }


        //insert vao bang day_project_item

        dayProjectItemDTO.setLangCd(langCd);
        dayProjectItemDTO.setEmpCd(empDTO.getEmpCd());
        dayProjectItemDTO.setDeptChangeHistoryId(deptChangeHistoryId);
        dayProjectItemDTO.setDeptCd(empDTO.getDeptCd());
        dayProjectItemDTO.setPositionCd(empDTO.getPositionCd());
        dayProjectItemDTO.setCheckStatus("N");
        dayProjectItemDTO.setUseYn("Y");
        int rs =iCommonDao.insertDataInt("ProjectMain.insertDayProjectItem",dayProjectItemDTO);
        //update vao bang day_project
        int rs1 =iCommonDao.updateData("ProjectMain.updateDayProjectItem",dayProjectItemDTO);



        return rs;
    }

    public DayProjectItemDTO getDayProjectItemByID(DayProjectItemDTO dayProjectItemDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        dayProjectItemDTO.setLangCd(langCd);
        return iCommonDao.selectOne("ProjectMain.getDayProjectItemByID",dayProjectItemDTO);
    }

    public int editDayProjectItem(DayProjectItemDTO dayProjectItemDTO){
        //co anh thi sua anh
        DayProjectItemDTO oldDTO=getDayProjectItemByID(dayProjectItemDTO);
        if(dayProjectItemDTO.getFile()!=null){
            //xoa file cu neu co
            if(oldDTO.getImgPath() != null){
                String [] arrOldFileName=oldDTO.getImgPath().split("/");
                String oldFileName=arrOldFileName[arrOldFileName.length-1];
                File oldFiles = new File(context.getRealPath("resources/Upload/img_project"), oldFileName);
                oldFiles.delete();
            }

            //them file moi
            String fileName=java.time.LocalDateTime.now().toString();
            fileName=fileName.replace(":","").replace(" ","").replace(".","").replace("-","");
            try{
                MultipartFile multipartFile = dayProjectItemDTO.getFile();
                fileName =fileName+"_"+ multipartFile.getOriginalFilename();
                File file = new File(context.getRealPath("resources/Upload/img_project"), fileName);
                multipartFile.transferTo(file);
                File fileAfter=resizeImage(file, 700, 700,fileName);
                dayProjectItemDTO.setImgPath("/resources/Upload/img_project/"+fileName);
            }catch (Exception e){}
        }else{
            dayProjectItemDTO.setImgPath(oldDTO.getImgPath());
        }

        //update bang day_project_item
        int rs =iCommonDao.updateData("ProjectMain.updateSpentItem",dayProjectItemDTO);
        //update bang day_project
        long Differ=dayProjectItemDTO.getAmount()-oldDTO.getAmount();
        dayProjectItemDTO.setAmount(Differ);
        int rs1 =iCommonDao.updateData("ProjectMain.updateDayProjectItem",dayProjectItemDTO);

        return rs;
    }

    public int deleteDayProjectItem(DayProjectItemDTO dayProjectItemDTO){
        int rs=0;
        String [] arr=dayProjectItemDTO.getInputSearchSql().split(",");
        for(int i=0;i< arr.length;i++){
            int dpiId=Integer.parseInt(arr[i]);
            dayProjectItemDTO.setDpiId(dpiId);

            //lay gia tri hien tai
            DayProjectItemDTO currentDTO = getDayProjectItemByID(dayProjectItemDTO);
            rs=iCommonDao.updateData("ProjectMain.deleteDayProjectItem",currentDTO);

        }
        return rs;
    }

    public int approveDayProjectItem(DayProjectItemDTO dayProjectItemDTO){
        int rs=0;
        String [] arr=dayProjectItemDTO.getInputSearchSql().split(",");
        for(int i=0;i< arr.length;i++){
            int dpiId=Integer.parseInt(arr[i]);
            dayProjectItemDTO.setDpiId(dpiId);

            //lay gia tri hien tai
            DayProjectItemDTO currentDTO = getDayProjectItemByID(dayProjectItemDTO);
            dayProjectItemDTO.setDpId(currentDTO.getDpId());
            rs=iCommonDao.updateData("ProjectMain.approveDayProjectItem",currentDTO);

        }
//        int count=iCommonDao.selectCount("ProjectMain.selectCountDayProjectItemApprove",dayProjectItemDTO);
//        if(count==0){
//            rs=iCommonDao.updateData("ProjectMain.updateDayProjectStatusByItem",dayProjectItemDTO);
//        }

        return rs;
    }

    public  List<FileUploadDTO> selectListFile(Integer dpId){
        return iCommonDao.selectList("ProjectMain.selectListFile", dpId);
    }

    public String insertTempFile(FileUploadDTO fileUploadDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        boolean rsCheck =checkTypeFile(fileUploadDTO.getFile(), TYPE.total);
        if(rsCheck==true){
            try {
                //file
                String fileName = fileUploadDTO.getFile().getOriginalFilename();
                String fileExt= FilenameUtils.getExtension(fileName);
                String fileHashName= SHA256Encryptor.encryptor(fileName)+"."+fileExt;
                File file1 = new File(context.getRealPath("resources/Upload/temp_file/"+empDTO.getEmpCd()+""), fileHashName);
                if(!file1.exists()){
                    file1.mkdirs();
                }
                fileUploadDTO.getFile().transferTo(file1);

                //update temp file to database
                FileUploadDTO paramFileUploadDTO = new FileUploadDTO();
                paramFileUploadDTO.setDeptCd(empDTO.getDeptCd());
                paramFileUploadDTO.setPositionCd(empDTO.getPositionCd());
                paramFileUploadDTO.setEmpCd(empDTO.getEmpCd());
                paramFileUploadDTO.setFileType("project");
                paramFileUploadDTO.setFilePath("/resources/Upload/temp_file/"+empDTO.getEmpCd()+"/");
                paramFileUploadDTO.setFileName(fileName);
                paramFileUploadDTO.setFileHashName(fileHashName);
                paramFileUploadDTO.setFileExt(FilenameUtils.getExtension(fileName));
                paramFileUploadDTO.setFileSize(fileUploadDTO.getFileSize());
                paramFileUploadDTO.setFileStatus("temp");
                iCommonDao.insertData("ProjectMain.insertTempFile", paramFileUploadDTO);

            } catch (Exception e) { e.printStackTrace();}
            return "success";
        }else{
            return "false";
        }
    }

    public void deleteAllTempFile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        //delete trong temp
        File directory = new File(context.getRealPath("resources/Upload/temp_file/"+empDTO.getEmpCd()+"/"));
        if(!directory.exists()){
            directory.mkdirs();
        }
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iCommonDao.deleteData("ProjectMain.deleteAllTempFile", empDTO.getEmpCd());
    }

    public void deleteOneTempFile(String filename){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String fileExt=FilenameUtils.getExtension(filename);
        String fileHashName=SHA256Encryptor.encryptor(filename)+"."+fileExt;
        try {
            File files = new File(context.getRealPath("resources/Upload/temp_file/"+empDTO.getEmpCd()+"/"), fileHashName);
            files.delete();
            HashMap param = new HashMap();
            param.put("empCd",empDTO.getEmpCd());
            param.put("filePathName","/resources/Upload/temp_file/"+empDTO.getEmpCd()+"/"+fileHashName);
            iCommonDao.deleteData("ProjectMain.deleteOneTempFile", param);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void deleteRealFile(Integer fileId){
        iCommonDao.deleteData("ProjectMain.deleteRealFile", fileId);
    }

    public int EditFile(DayProjectDTO dayProjectDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        dayProjectDTO.setEmpCd(empDTO.getEmpCd());
        //insert file_id & dp_id to dayproject_file and update to real_file
        iCommonDao.insertData("ProjectMain.insertRealFileEdit", dayProjectDTO);
        iCommonDao.updateData("ProjectMain.updateRealFile", dayProjectDTO);
        //move file from temp folder to real folder
        File srcFiles = new File(context.getRealPath("resources/Upload/temp_file/"+empDTO.getEmpCd()+""));
        File destFiles = new File(context.getRealPath("resources/Upload/real_file/"+empDTO.getEmpCd()+""));
        if(!destFiles.exists()){
            destFiles.mkdirs();
        }
        File[] content = srcFiles.listFiles();
        for(int i = 0; i < content.length; i++) {
            //move content[i]
            content[i].renameTo(new File(context.getRealPath("resources/Upload/real_file/"+empDTO.getEmpCd()+""),content[i].getName().toString()));
        }
        return 1;
    }

    public boolean checkTypeFile(MultipartFile mFile, String mType){
        String fileType = mFile.getContentType();
        boolean check = false;
        if(mType != null && mType.length() > 0){
            // image/*
            check = (mType.indexOf(fileType) != -1) ? true : false;
        }
        return check;
    }

    public int getEmpProjectLeaderCD(int id){
        ProjectLeaderAuthDTO projectLeaderAuthDTO = new ProjectLeaderAuthDTO();
        projectLeaderAuthDTO.setPjId(id);
        return iCommonDao.selectCount("ProjectMain.getEmpProjectLeaderCD", projectLeaderAuthDTO);
    }

    public int getEmpAccountingLeaderCD(int id){
        ProjectLeaderAuthDTO projectLeaderAuthDTO = new ProjectLeaderAuthDTO();
        projectLeaderAuthDTO.setPjId(id);
        return iCommonDao.selectCount("ProjectMain.getEmpAccountingLeaderCD", projectLeaderAuthDTO);
    }

    public static class TYPE {
        public static String csv	="text/csv";
        public static String csv1	="application/csv";
        public static String json	="application/json";
        public static String txt	="text/plain";
        public static String xlsx	="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        public static String xlsb	="application/vnd.ms-excel.sheet.binary.macroEnabled.12";
        public static String xls	="application/vnd.ms-excel";
        public static String xlsm	="application/vnd.ms-excel.sheet.macroEnabled.12";
        public static String bmp	="image/bmp";
        public static String gif	="image/gif";
        public static String jpg	="image/jpeg";
        public static String jpeg	="image/jpeg";
        public static String png	="image/png";
        public static String doc	="application/msword";
        public static String docm	="application/vnd.ms-word.document.macroEnabled.12";
        public static String docx	="application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        public static String dotx	="application/vnd.openxmlformats-officedocument.wordprocessingml.template";
        public static String dotm	="application/vnd.ms-word.template.macroEnabled.12";
        public static String html	="text/html";
        public static String pdf	="application/pdf";
        public static String potm	="application/vnd.ms-powerpoint.template.macroEnabled.12";
        public static String potx	="application/vnd.openxmlformats-officedocument.presentationml.template";
        public static String ppam	="application/vnd.ms-powerpoint.addin.macroEnabled.12";
        public static String pps	="application/vnd.openxmlformats-officedocument.presentationml.slideshow";
        public static String ppsx	="application/vnd.openxmlformats-officedocument.presentationml.slideshow";
        public static String ppsm	="application/vnd.ms-powerpoint.slideshow.macroEnabled.12";
        public static String ppt	="application/vnd.ms-powerpoint";
        public static String pptm	="application/vnd.ms-powerpoint.presentation.macroEnabled.12";
        public static String pptx	="application/vnd.openxmlformats-officedocument.presentationml.presentation";
        public static String rtf	="application/rtf";
        public static String rtf2	="text/rtf";
        public static String mp3	="audio/mpeg";
        public static String mp4	="video/mp4";
        public static String avi	="video/avi";
        public static String total=csv+csv1+json+txt+xlsx+xlsb+xls+xlsm+bmp+gif+jpg+jpeg+png+doc+docm+docx+dotx+dotm+html+pdf+potm+potx+ppam+pps+ppsx+ppsm+ppt+pptm+pptx+rtf+rtf2+mp3+mp4+avi;
    }

    public ProjectMainDTO selectDeltailProjectId(int id){
        ProjectMainDTO projectMainDTO = new ProjectMainDTO();
        String langCd = LocaleContextHolder.getLocale().toString();
        projectMainDTO.setLangCd(langCd);
        projectMainDTO.setPjId(id);
        ProjectMainDTO sampleProject = iCommonDao.selectOne("ProjectMain.selectDeltailProjectId", projectMainDTO);
        return sampleProject;
    }

    public DayProjectDTO selectDetailDayProjectExcelExport(int id){
        DayProjectDTO dayProjectDTO = new DayProjectDTO();
        dayProjectDTO.setDpId(id);
        dayProjectDTO = iCommonDao.selectOne("ProjectMain.selectDetailDayProjectExcelExport", dayProjectDTO);
        return dayProjectDTO;
    }

    public List<FileUploadDTO> selectSummaryFileByID(DayProjectDTO dayProjectDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        dayProjectDTO.setEmpCd(empDTO.getEmpCd());
        return iCommonDao.selectList("ProjectMain.selectSummaryFileByID",dayProjectDTO);
    }


    File resizeImage(File file, int targetWidth, int targetHeight,String fileName) throws Exception {
        File file1 = new File(context.getRealPath("resources/Upload/img_project"), fileName);
        Thumbnails.of(file)
                .size(targetWidth, targetHeight)
                .outputQuality(0.9)
                .toFile(file1);
        return file1;
    }

    public String unescapeHtml3( String str ) {
        try {
            HTMLDocument doc = new HTMLDocument();
            new HTMLEditorKit().read( new StringReader( "<html><body>" + str ), doc, 0 );
            return doc.getText( 1, doc.getLength() ).substring(0, doc.getText( 1, doc.getLength() ).length() - 1);
        } catch( Exception ex ) {
            return str;
        }
    }

    public void scheduleAutoInsert(){
        iCommonDao.insertData("ProjectMain.scheduleAutoInsert", null);
    }
}