package kr.co.hs.board.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.board.service.BoardGroupService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dto.FileUploadDTO;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardGroupController {
    final BoardGroupService datBoardService;
    @Autowired
    private EmpService empService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private TranslationService translationService;
    @Autowired
    ServletContext context;

    @TilesDynamic("base")
    @GetMapping("/board/notice/group/list.hs")
    public String list(HttpServletRequest request, Model model, @ModelAttribute("search") BoardControlDTO boardControlDTO) {

        String langCd = LocaleContextHolder.getLocale().toString();
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmpDTO emp = empService.getSessionUserLogin(request);

        boardControlDTO.setAdminYn(emp.getAdminYn());
        boardControlDTO.setTranslationYn(emp.getTranslationYn());
        boardControlDTO.setEmpCd(emp.getEmpCd());
        model.addAttribute("adminYn",emp.getAdminYn());
        model.addAttribute("selectTopByNoticeYn",datBoardService.selectTopByNoticeYn(langCd));

        boardControlDTO.setLangCd(langCd);

        model.addAttribute("admin", emp.getAdminYn());

        return "/hrga/groupnotice/list";
    }

    @RequestMapping(value = "/springPaginationDataTablesBoardGroup", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String springPaginationDataTablesBoardCompany(HttpServletRequest request) throws IOException {
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
        para.put("langCd", langCd);
        String deptSearch=deptCd;

        //xuly translationStatus
        String translationStatus ="";
        String translationStatusGet = request.getParameter("translationStatus");
        if(translationStatusGet.equals("Y") && langCd.equals("ko") ){
            translationStatus="유";
        }else if(translationStatusGet.equals("N") && langCd.equals("ko") ){
            translationStatus="무";
        }else if(translationStatusGet.equals("Y") && langCd.equals("en") ){
            translationStatus="translated";
        }else if(translationStatusGet.equals("N") && langCd.equals("en") ){
            translationStatus="Not yet";
        }else if(translationStatusGet.equals("N") && langCd.equals("vt") ){
            translationStatus="Chưa dịch";
        }else if(translationStatusGet.equals("Y") && langCd.equals("vt") ){
            translationStatus="Đã dịch";
        }else{
            translationStatus ="";
        }

        //xu ly input search

        String inputSearchSql = "";
        String optionSearch = request.getParameter("optionSearch");
        String inputSearch = request.getParameter("inputSearch").trim();
        if (!inputSearch.equals("") && !inputSearch.equals(null)){
            if (optionSearch.equals("emp_name")){
                inputSearchSql=" AND GET_EMP_NAME(emp_cd, '"+langCd+"') LIKE '%"+inputSearch+"%' ";
            }else if(optionSearch.equals("position_name")){
                inputSearchSql=" AND GET_POSITION_NAME(position_cd, '"+langCd+"')  LIKE '%"+inputSearch+"%'";
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

        List<BoardControlDTO> ttList = createPaginationDataBoard(pageNumber,pageDisplayLength, searchParameter, request, dateSql, deptSearch, inputSearchSql, translationStatus,str);

        BoardControlDTO boardDTO = new BoardControlDTO();
        //select total row
        BoardControlDTO boardControlDTO = new BoardControlDTO();
        EmpDTO emp = empService.getSessionUserLogin(request);
        int sum_row ;
        boardControlDTO.setEmpCd(emp.getEmpCd());
        boardControlDTO.setLangCd(langCd);
        sum_row = datBoardService.count(boardControlDTO);
        // Set Total record
        boardDTO.setITotalRecords(sum_row);

        boardControlDTO.setTxtSearch(searchParameter.trim());
        boardControlDTO.setDateSql(dateSql);
        boardControlDTO.setDeptSearch(deptSearch);
        boardControlDTO.setInputSearchSql(inputSearchSql);
        boardControlDTO.setTranslationStatus(translationStatus);

        sum_row = datBoardService.count(boardControlDTO);
        // Set Total display record
        boardDTO.setITotalDisplayRecords(sum_row);

        boardDTO.setAaData(ttList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(boardDTO);

        return json;
    }

    private List<BoardControlDTO> createPaginationDataBoard(Integer pageNumber, Integer pageDisplayLength, String searchParameter, HttpServletRequest request, String dateSql, String deptSearch, String inputSearchSql, String translationStatus, String str) {
        int a = (pageNumber - 1) * pageDisplayLength;
        String langCd = LocaleContextHolder.getLocale().toString();
        EmpDTO emp = empService.getSessionUserLogin(request);

        BoardControlDTO boardControlDTO = new BoardControlDTO();

        boardControlDTO.setAdminYn(emp.getAdminYn());
        boardControlDTO.setTranslationYn(emp.getTranslationYn());
        boardControlDTO.setEmpCd(emp.getEmpCd());

        boardControlDTO.setLangCd(langCd);
        boardControlDTO.setTxtSearch(searchParameter.trim());
        boardControlDTO.setStartRow(a);
        boardControlDTO.setRecordsPerPage(pageDisplayLength);
        boardControlDTO.setDateSql(dateSql);
        boardControlDTO.setDeptSearch(deptSearch);
        boardControlDTO.setInputSearchSql(inputSearchSql);
        boardControlDTO.setTranslationStatus(translationStatus);
        boardControlDTO.setStr(str);
        List<BoardControlDTO> ttList = datBoardService.getCompanyNoticeList(boardControlDTO);

        return ttList;
    }

    //show write page
    @TilesDynamic("base")
    @GetMapping("/board/notice/group/write.hs")
    public String goWrite(Model model, HttpServletRequest request,@ModelAttribute("board") BoardControlDTO boardControlDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeBoardLevelList", datBoardService.getBoardTypeList());
        model.addAttribute("typeLangList", datBoardService.getLangList());
        model.addAttribute("typeCodeList", codeService.getCodeList("yesno",langCd));

        log.error("emp/write @@@@@@@@@");
        return "/hrga/groupnotice/write";
    }

    //method insert for board
    @PostMapping("/board/notice/group/write.hs")
    public String doWrite(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        model.addAttribute("search", "");
        log.error("{the first}", new Gson().toJson(boardControlDTO));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String result = "";
        if(boardControlDTO.getNotiStartDt()==""){
            boardControlDTO.setNotiStartDt(null);
            boardControlDTO.setNotiEndDt(null);
        }

        if (empDTO.getAdminYn().equals("Y")){
            datBoardService.boardCompanySave(empDTO, boardControlDTO);

            //insert file_id & board_id to board_file and update to real_file
            datBoardService.insertRealFile(empDTO.getEmpCd());
            datBoardService.updateRealFile(empDTO.getEmpCd());
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

            result = "redirect:/board/notice/group/list.hs";
        }else {
            result = "redirect:/board/notice/group/list.hs?title=error&message=error";
        }
        log.error("{the second}", new Gson().toJson(boardControlDTO));

        return result;
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/group/view.hs")
    public String view(Model model, @ModelAttribute("search") BoardControlDTO boardControlDTO, HttpServletRequest request) {
        String langCd = LocaleContextHolder.getLocale().toString();

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        param.put("empCd",emp.getEmpCd());
        String str = emp.getEmpCd().toString();
        param.put("empCd_", str);
        param.put("noticeCd", boardControlDTO.getBoardId());
        param.put("boardId", boardControlDTO.getBoardId());
        param.put("langCd", langCd);
        boardControlDTO.setLangCd(langCd);
        param.put("type", "GROUP");
        noticeService.readNotificationReceiver(param);
        model.addAttribute("boardId", boardControlDTO.getBoardId());
        model.addAttribute("boardDTO", datBoardService.getBoardView(boardControlDTO));
        model.addAttribute("detailTran", translationService.getTransBoard(param));
        model.addAttribute("Permission",emp.getAdminYn());
        model.addAttribute("empCd",emp.getEmpCd());
        model.addAttribute("lstFile",datBoardService.selectListFile(boardControlDTO.getBoardId()));

        return "/hrga/groupnotice/view";
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/group/edit.hs")
    public String goEdit(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();

        model.addAttribute("boardId", boardControlDTO.getBoardId());
        model.addAttribute("boardDTO", datBoardService.getBoardView(boardControlDTO));
        model.addAttribute("typeCodeList", codeService.getCodeList("yesno",langCd));

        model.addAttribute("typeBoardLevelList", datBoardService.getBoardTypeList());
        model.addAttribute("typeLangList", datBoardService.getLangList());
        model.addAttribute("lstFile",datBoardService.selectListFile(boardControlDTO.getBoardId()));

        log.error("emp/write @@@@@@@@@");
        return "/hrga/groupnotice/edit";
    }

    @PostMapping("/board/notice/group/edit.hs")
    public String doEdit(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        if(boardControlDTO.getNotiStartDt()==""){
            boardControlDTO.setNotiStartDt(null);
            boardControlDTO.setNotiEndDt(null);
        }
        int boardId = boardControlDTO.getBoardId();
        datBoardService.boardInfoEdit(boardControlDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        //insert file_id & board_id to board_file and update to real_file
        HashMap param = new HashMap();
        param.put("empCd",empDTO.getEmpCd());
        param.put("boardId",boardId);
        datBoardService.insertRealFileEdit(param);
        datBoardService.updateRealFile(empDTO.getEmpCd());
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

        String paraBackSearch = "&"+ boardControlDTO.getTxtSearch();
        return "redirect:/board/notice/group/view.hs?boardId="+boardId+paraBackSearch;
    }

    @PostMapping("/board/notice/group/uploadFile")
    @ResponseBody
    public String submit(Model model, @RequestParam("file") MultipartFile file,@RequestParam("fileSize") String fileSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
       boolean rsCheck =checkTypeFile(file, TYPE.total);
        if(rsCheck==true){
            try {
                //file
                String fileName = file.getOriginalFilename();
                String fileExt=FilenameUtils.getExtension(fileName);
                String fileHashName=SHA256Encryptor.encryptor(fileName)+"."+fileExt;
                File file1 = new File(context.getRealPath("resources/Upload/temp_file/"+empDTO.getEmpCd()+""), fileHashName);
                if(!file1.exists()){
                    file1.mkdirs();
                }
                file.transferTo(file1);

                //update temp file to database
                FileUploadDTO fileUploadDTO = new FileUploadDTO();
                fileUploadDTO.setDeptCd(empDTO.getDeptCd());
                fileUploadDTO.setPositionCd(empDTO.getPositionCd());
                fileUploadDTO.setEmpCd(empDTO.getEmpCd());
                fileUploadDTO.setFileType("board");
                fileUploadDTO.setFilePath("/resources/Upload/temp_file/"+empDTO.getEmpCd()+"/");
                fileUploadDTO.setFileName(fileName);
                fileUploadDTO.setFileHashName(fileHashName);
                fileUploadDTO.setFileExt(FilenameUtils.getExtension(fileName));
                fileUploadDTO.setFileSize(fileSize);
                fileUploadDTO.setFileStatus("temp");
                datBoardService.insertTempFile(fileUploadDTO);

            } catch (Exception e) { e.printStackTrace();}
            return "success";
        }else{
            return "false";
        }

    }
    @RequestMapping(value = "/board/notice/group/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public String deleteFile(@RequestParam("filename") String filename) {
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
            datBoardService.deleteOneTempFile(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    @RequestMapping(value = "/board/notice/group/deleteOldFile", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOldFile(@RequestParam("fileId") int fileId) {
    //delete file in folder and table file and board_file
    FileUploadDTO fileInfor = datBoardService.selectInforFile(fileId);
    datBoardService.deleteRealFile(fileId);
//        try {
//            File files = new File(context.getRealPath(fileInfor.getFilePath()), fileInfor.getFileHashName());
//            files.delete();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "success";
    }

    @RequestMapping(value = "/board/notice/group/reloadPage", method = RequestMethod.POST)
    @ResponseBody
    public String reloadPage() {
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

        datBoardService.deleteAllTempFile(empDTO.getEmpCd());
        return "success";
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





}


