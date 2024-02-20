package kr.co.hs.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.board.controller.BoardGroupController;
import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dto.FileUploadDTO;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.util.CommonUtil;
import kr.co.hs.deptconfig.service.DeptService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.project.dto.*;
import kr.co.hs.project.service.ExcelExport;
import kr.co.hs.project.service.ProjectService;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/API")
public class ProjectAPI {

    @Autowired
    NoticeService noticeService;

    @Autowired
    ProjectService projectService;

    @Autowired
    DeptService deptService;

    @Autowired
    EmpService empService;

    @Autowired
    TranslationService translationService;

    @Autowired
    ExcelExport excelExport;

    @RequestMapping(value ="/project/main", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTables(HttpServletRequest request) throws IOException{


        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String deptCd = request.getParameter("levelDept");
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        String translationStatusGet = request.getParameter("translationStatus");
        String numberOrderColumn = request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        String approval = request.getParameter("approval");
        int type = Integer.valueOf(request.getParameter("type"));

        String json = projectService.searchCommonProject(pageDisplayLength, start_date, end_date, deptCd, optionSearch,
                inputSearch, translationStatusGet, orderColumn, typeOrder, iDisplayStart, searchParameter, approval, type);
        return json;
    }

    /*
     * return 0: picName from request is exit on the DB, the method return 0 parametter
     * return 1: the request already success
     * */
    @PostMapping("/mainproject/add")
    public int writeMainProject(HttpServletRequest request, @RequestBody ProjectMainDTO projectMainDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        int num = projectService.processWriteMainProject(projectMainDTO, empDTO);
        return num;
    }

    @GetMapping( value = "/mainproject/projectauth", produces = "application/json")
    public List<ProjectAuthDTO> selectProjectAuth(){
        return projectService.selectProjectAuth();
    }

    @PostMapping("/mainproject/addprojectauth")
    public int insertProjectAuth(@RequestParam ("inputName") String name){

        return projectService.insertProjectAuth(name);
    }

    @PostMapping("/mainproject/deleteprojectauth")
    public int deleteProjectAuth(@RequestParam("id") int id){
        projectService.deleteProjectAuth(id);
        return 1;
    }

    @PostMapping("/mainproject/edit")
    public int editMainProject(HttpServletRequest request, @RequestBody ProjectMainDTO projectMainDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        return projectService.processUpdateMainProject(projectMainDTO, empDTO);
    }

    @PostMapping("/mainproject/delete")
    public int deleteMainProject(String lstChecked) {
        return projectService.deleteMainProject(lstChecked);
    }

    @PostMapping("/mainproject/approval")
    public int approvalMainProject(String lstChecked) {
        return projectService.processApprovalMainProject(lstChecked);
    }

    @GetMapping (value = "/mainproject/leaderlist", produces = "application/json")
    public List<ProjectLeaderAuthDTO> selectLeaderMainProject(ProjectLeaderAuthDTO projectLeaderAuthDTO){
        return projectService.selectLeaderMainProject(projectLeaderAuthDTO);
    }

    @RequestMapping(value ="/mainproject/exportexcel", method = RequestMethod.GET)
    public String exportExcelMainProject(HttpServletRequest request) throws IOException{

        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String deptCd = request.getParameter("levelDept");
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        String translationStatusGet = request.getParameter("translationStatus");
        String orderColumn =  "rownum";
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        String approval = request.getParameter("approval");

        //get data with No finish project status
        List<ProjectMainDTO> lst = projectService.selectListExportProject(pageDisplayLength, start_date, end_date, deptCd, optionSearch,
                inputSearch, translationStatusGet, orderColumn, typeOrder, iDisplayStart, searchParameter, approval, "N");

        //get data with project has finish status
        List<ProjectMainDTO> lstY = projectService.selectListExportProject(pageDisplayLength, start_date, end_date, deptCd, optionSearch,
                inputSearch, translationStatusGet, orderColumn, typeOrder, iDisplayStart, searchParameter, approval, "Y");

        EmpDTO empDTO = empService.getSessionUserLogin(request);

        String result = excelExport.exportExcelFile(empDTO, lstY, lst, start_date, end_date);

        return result;
    }

    /*
    * end main project
    * */

    @RequestMapping(value ="/project/getDayProjectDatatables", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getDayProjectDatatables(HttpServletRequest request) throws IOException{
        int pjId = Integer.valueOf(request.getParameter("pjId"));
        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String deptCd = request.getParameter("levelDept");
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        String translationStatusGet = request.getParameter("translationStatus");
        String dayProjectStatus = request.getParameter("dayProjectStatus");
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        int type = Integer.valueOf(request.getParameter("type"));

        String json = projectService.getDayProjectDatatables(pjId,pageDisplayLength, start_date, end_date, deptCd, optionSearch,
                inputSearch, translationStatusGet,dayProjectStatus,numberOrderColumn, orderColumn, typeOrder, iDisplayStart, searchParameter,request, type);
        return json;
    }

    @RequestMapping(value ="/dayproject/exportexcel", method = RequestMethod.GET)
    public String exportExcelDayProject(HttpServletRequest request) throws IOException{

        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String start_date = request.getParameter("start_date");
        String end_date = request.getParameter("end_date");
        String deptCd = request.getParameter("levelDept");
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        String translationStatusGet = request.getParameter("translationStatus");
        String orderColumn =  "rownum";
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        int pjId = Integer.valueOf(request.getParameter("pjId"));
        String dayProjectStatus = request.getParameter("dayProjectStatus");

        List<DayProjectDTO> lst = projectService.selectListExportDayProject(pjId , pageDisplayLength, start_date, end_date, deptCd, optionSearch,
                inputSearch, translationStatusGet, dayProjectStatus, orderColumn, typeOrder, iDisplayStart, searchParameter, request);

        EmpDTO empDTO = empService.getSessionUserLogin(request);
        ProjectMainDTO projectMainDTO = projectService.selectDeltailProjectId(pjId);

        String result = excelExport.exportExcelFileDayProject(empDTO,lst, start_date, end_date, projectMainDTO);

        return result;
    }



    @PostMapping("/dayproject/add")
    public String dayprojectAdd(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.InsertDayProject(dayProjectDTO);
        return "rs";
    }
    @PostMapping("/dayproject/addDeposit")
    public String addDeposit(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.InsertDepositDayProject(dayProjectDTO);
        return "rs";
    }

    @RequestMapping(value = "/dayproject/selectInfoDayProjectByID", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String selectInfoDayProjectByID(@RequestBody DayProjectDTO paramDTO) {
        DayProjectDTO dayProjectDTO=projectService.selectInfoDayProjectByID(paramDTO);
        return new Gson().toJson(dayProjectDTO).toString();
    }

    @PostMapping("/dayproject/editSpentDayProject")
    public String editSpentDayProject(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.editSpentDayProject(dayProjectDTO);
        return "rs";
    }

    @PostMapping("/dayproject/editDepositDayProject")
    public String editDepositDayProject(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.editDepositDayProject(dayProjectDTO);
        return "rs";
    }

    @PostMapping("/dayproject/deleteDayProject")
    public String deleteDayProject(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.deleteDayProject(dayProjectDTO);
        return String.valueOf(result);
    }

    @PostMapping("/dayproject/approveDayProject")
    public String approveDayProject(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.approveDayProject(dayProjectDTO);
        return "rs";
    }

    @RequestMapping(value ="/project/getDayProjectItemDatatables", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getDayProjectItemDatatables(HttpServletRequest request) throws IOException{
        int dpId = Integer.valueOf(request.getParameter("dpId"));
        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  request.getParameter("mDataProp_"+numberOrderColumn);
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        int type = Integer.valueOf(request.getParameter("type"));

        String json = projectService.getDayProjectItemDatatables(dpId,pageDisplayLength,numberOrderColumn, orderColumn, typeOrder, iDisplayStart, searchParameter,request, type);
        return json;
    }

    @RequestMapping(value ="/project/getDayProjectItemExportExcel", method = RequestMethod.GET)
    public String getDayProjectItemDatatablesExportExcel(HttpServletRequest request) throws IOException{
        int dpId = Integer.valueOf(request.getParameter("dpId"));
        int pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
        String numberOrderColumn=request.getParameter("iSortCol_0");
        String orderColumn =  "rownum";
        String typeOrder =  request.getParameter("sSortDir_0");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String searchParameter = request.getParameter("sSearch");
        int pjId = Integer.valueOf(request.getParameter("pjId"));
        ProjectMainDTO projectMainDTO = projectService.selectDeltailProjectId(pjId);
        DayProjectDTO dayProjectDTO = projectService.selectDetailDayProjectExcelExport(dpId);

        List<DayProjectItemDTO> lst = projectService.getDayProjectItemExportExcel(dpId,pageDisplayLength,numberOrderColumn, orderColumn, typeOrder
                                                                                    , iDisplayStart, searchParameter,request);

        EmpDTO empDTO = empService.getSessionUserLogin(request);

        return excelExport.exportExcelFileDayProjectItem(empDTO, lst, projectMainDTO, dayProjectDTO);
    }

    @PostMapping("/dayProjectItem/addDayProjectItem")
    public String addDayProjectItem(@RequestParam(required = false,name = "file") MultipartFile file, @RequestParam("productName") String productName, @RequestParam("price") long price, @RequestParam("ea") int ea, @RequestParam("amount") long amount, @RequestParam("dpId") int dpId, @RequestParam("pjId") int pjId, @RequestParam("spentType") String spentType) {
        DayProjectItemDTO dayProjectItemDTO = new DayProjectItemDTO();
        dayProjectItemDTO.setFile(file);
        dayProjectItemDTO.setProductName(productName);
        dayProjectItemDTO.setPrice(price);
        dayProjectItemDTO.setEa(ea);
        dayProjectItemDTO.setAmount(amount);
        dayProjectItemDTO.setDpId(dpId);
        dayProjectItemDTO.setPjId(pjId);
        dayProjectItemDTO.setSpentType(spentType);
        int result = projectService.addDayProjectItem(dayProjectItemDTO);
        return "rs";
    }

    @PostMapping("/dayProjectItem/editDayProjectItem")
    public String editDayProjectItem(@RequestParam(required = false,name = "file") MultipartFile file,@RequestParam("productName") String productName,@RequestParam("price") long price,@RequestParam("ea") int ea,@RequestParam("amount") long amount,@RequestParam("dpId") int dpId,@RequestParam("pjId") int pjId,@RequestParam("dpiId") int dpiId,@RequestParam("spentType") String spentType) {
        DayProjectItemDTO dayProjectItemDTO = new DayProjectItemDTO();
        dayProjectItemDTO.setFile(file);
        dayProjectItemDTO.setProductName(productName);
        dayProjectItemDTO.setPrice(price);
        dayProjectItemDTO.setEa(ea);
        dayProjectItemDTO.setAmount(amount);
        dayProjectItemDTO.setDpId(dpId);
        dayProjectItemDTO.setPjId(pjId);
        dayProjectItemDTO.setDpiId(dpiId);
        dayProjectItemDTO.setSpentType(spentType);
        int result = projectService.editDayProjectItem(dayProjectItemDTO);
        return "rs";
    }

    @PostMapping("/dayProjectItem/deleteDayProjectItem")
    public String deleteDayProjectItem(@RequestBody DayProjectItemDTO dayProjectItemDTO) {
        int result = projectService.deleteDayProjectItem(dayProjectItemDTO);
        return "rs";
    }

    @PostMapping("/dayProjectItem/approveDayProjectItem")
    public String approveDayProjectItem(@RequestBody DayProjectItemDTO dayProjectItemDTO) {
        int result = projectService.approveDayProjectItem(dayProjectItemDTO);
        return "rs";
    }

    @PostMapping("/dayProject/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("fileSize") String fileSize) {
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setFile(file);
        fileUploadDTO.setFileSize(fileSize);
        String rs=projectService.insertTempFile(fileUploadDTO);
        return rs;
    }

    @PostMapping("/dayProject/reloadPage")
    public String reloadPage() {
        projectService.deleteAllTempFile();
        return "success";
    }

    @PostMapping("/dayProject/deleteFile")
    public String deleteFile(@RequestParam("filename") String filename) {
        projectService.deleteOneTempFile(filename);
        return "success";
    }

    @PostMapping("/dayProject/deleteOldFile")
    public String deleteOldFile(@RequestParam("fileId") int fileId) {
        projectService.deleteRealFile(fileId);
        return "success";
    }

    @PostMapping("/dayProject/EditFile")
    public String EditFile(@RequestBody DayProjectDTO dayProjectDTO) {
        int result = projectService.EditFile(dayProjectDTO);
        return "rs";
    }

    @RequestMapping(value = "/dayProjectItem/getTranDayProjectItem", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String getTranDayProjectItem(@RequestBody DayProjectItemDTO dayProjectItemDTO) {
        List<TranslationDTO> translationDTOList=translationService.getTranDayProjectItem(dayProjectItemDTO);
        return new Gson().toJson(translationDTOList).toString();
    }

    @RequestMapping(value = "/dayProject/getTranDayProject", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String getTranDayProject(@RequestBody DayProjectDTO paramDTO) {
        List<TranslationDTO> translationDTOList=translationService.getTranDayProject(paramDTO);
        return new Gson().toJson(translationDTOList).toString();
    }

    @RequestMapping(value = "/Project/getTranProject", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String getTranProject(@RequestBody ProjectMainDTO paramDTO) {
        List<TranslationDTO> translationDTOList=translationService.getTranProject(paramDTO);
        return new Gson().toJson(translationDTOList).toString();
    }

    @RequestMapping(value = "/dayProject/selectSummaryFileByID", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String selectSummaryFileByID(@RequestBody DayProjectDTO dayProjectDTO) {
        List<FileUploadDTO> lst=projectService.selectSummaryFileByID(dayProjectDTO);
        return new Gson().toJson(lst).toString();
    }
}
