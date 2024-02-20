package kr.co.hs.board.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hs.board.service.BoardGroupService;
import kr.co.hs.code.service.CodeService;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.board.service.FreeBoardService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FreeBoardController {
    final FreeBoardService freeBoardService;
    final BoardGroupService boardGroupService;

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
    @GetMapping("/board/notice/free/list.hs")
    public String list(HttpServletRequest request, Model model, @ModelAttribute("search") BoardControlDTO boardControlDTO) {

        String langCd = LocaleContextHolder.getLocale().toString();
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmpDTO emp = empService.getSessionUserLogin(request);

        boardControlDTO.setAdminYn(emp.getAdminYn());
        boardControlDTO.setTranslationYn(emp.getTranslationYn());

        boardControlDTO.setLangCd(langCd);
        model.addAttribute("adminYn",emp.getAdminYn());
        boardControlDTO.setTotal(freeBoardService.count(boardControlDTO));
        model.addAttribute("selectTopByNoticeYn", freeBoardService.selectTopByNoticeYn(langCd));

        return "/hrga/companynotice/list";
    }

    @RequestMapping(value = "/springPaginationDataTablesBoardFree", method = RequestMethod.GET, produces = "application/json")
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

        List<BoardControlDTO> ttList = createPaginationDataBoard(pageNumber,pageDisplayLength, searchParameter, request, dateSql, deptSearch, inputSearchSql,translationStatus, str);

        BoardControlDTO boardDTO = new BoardControlDTO();
        //select total row
        BoardControlDTO boardControlDTO = new BoardControlDTO();
        EmpDTO emp = empService.getSessionUserLogin(request);
        int sum_row ;
        boardControlDTO.setEmpCd(emp.getEmpCd());
        boardControlDTO.setLangCd(langCd);
        sum_row = freeBoardService.count(boardControlDTO);
        // Set Total record
        boardDTO.setITotalRecords(sum_row);

        boardControlDTO.setTxtSearch(searchParameter.trim());
        boardControlDTO.setDateSql(dateSql);
        boardControlDTO.setDeptSearch(deptSearch);
        boardControlDTO.setInputSearchSql(inputSearchSql);
        boardControlDTO.setTranslationStatus(translationStatus);

        sum_row = freeBoardService.count(boardControlDTO);
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
        List<BoardControlDTO> ttList = freeBoardService.getCompanyNoticeList(boardControlDTO);

        return ttList;
    }

    //show write page
    @TilesDynamic("base")
    @GetMapping("/board/notice/free/write.hs")
    public String goWrite(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeBoardLevelList", freeBoardService.getBoardTypeList());
        model.addAttribute("typeLangList", freeBoardService.getLangList());
        model.addAttribute("typeCodeList", codeService.getCodeList("yesno",langCd));

        log.error("emp/write @@@@@@@@@");
        return "/hrga/companynotice/write";
    }

    //method insert for board
    @PostMapping("/board/notice/free/write.hs")
    public String doWrite(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        if(boardControlDTO.getNotiStartDt()==""){
            boardControlDTO.setNotiStartDt(null);
            boardControlDTO.setNotiEndDt(null);
        }
        model.addAttribute("search", "");
        log.error("{the first}", new Gson().toJson(boardControlDTO));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        freeBoardService.boardCompanySave(empDTO, boardControlDTO);

        //insert file_id & board_id to board_file and update to real_file
        boardGroupService.insertRealFile(empDTO.getEmpCd());
        boardGroupService.updateRealFile(empDTO.getEmpCd());
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

        log.error("{the second}", new Gson().toJson(boardControlDTO));

        return "redirect:/board/notice/free/list.hs";
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/free/view.hs")
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
        param.put("type", "NOTICE");
        noticeService.readNotificationReceiver(param);
        boardControlDTO.setLangCd(langCd);
        model.addAttribute("boardId", boardControlDTO.getBoardId());
        model.addAttribute("boardDTO", freeBoardService.getBoardView(boardControlDTO));
        model.addAttribute("detailTran", translationService.getTransBoard(param));
        model.addAttribute("Permission",emp.getAdminYn());
        model.addAttribute("empCd",emp.getEmpCd());
        model.addAttribute("lstFile",boardGroupService.selectListFile(boardControlDTO.getBoardId()));

        return "/hrga/companynotice/view";
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/free/edit.hs")
    public String goEdit(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();

        model.addAttribute("boardId", boardControlDTO.getBoardId());
        model.addAttribute("boardDTO", freeBoardService.getBoardView(boardControlDTO));
        model.addAttribute("typeCodeList", codeService.getCodeList("yesno",langCd));

        model.addAttribute("typeBoardLevelList", freeBoardService.getBoardTypeList());
        model.addAttribute("typeLangList", freeBoardService.getLangList());
        model.addAttribute("lstFile",boardGroupService.selectListFile(boardControlDTO.getBoardId()));

        log.error("emp/write @@@@@@@@@");
        return "/hrga/companynotice/edit";
    }

    @PostMapping("/board/notice/free/edit.hs")
    public String doEdit(Model model, @ModelAttribute("board") BoardControlDTO boardControlDTO) {
        if(boardControlDTO.getNotiStartDt()==""){
            boardControlDTO.setNotiStartDt(null);
            boardControlDTO.setNotiEndDt(null);
        }
        int boardId = boardControlDTO.getBoardId();
        freeBoardService.boardInfoEdit(boardControlDTO);
        String paraBackSearch = "&"+ boardControlDTO.getTxtSearch();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();

        //insert file_id & board_id to board_file and update to real_file
        HashMap param = new HashMap();
        param.put("empCd",empDTO.getEmpCd());
        param.put("boardId",boardId);
        boardGroupService.insertRealFileEdit(param);
        boardGroupService.updateRealFile(empDTO.getEmpCd());
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

        return "redirect:/board/notice/free/view.hs?boardId="+boardId+paraBackSearch;
    }
}

