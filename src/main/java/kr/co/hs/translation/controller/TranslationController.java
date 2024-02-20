package kr.co.hs.translation.controller;

import kr.co.hs.code.service.CodeService;
import kr.co.hs.comment.dto.CommentDTO;
import kr.co.hs.comment.service.CommentService;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.board.service.FreeBoardService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.lang.service.LangService;
import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.daywork.service.DayWorkService;
import kr.co.hs.project.dto.*;
import kr.co.hs.project.service.ProjectService;
import kr.co.hs.work.dto.WorktranslationDTO;
import kr.co.hs.work.dto.WorkDTO;
import kr.co.hs.work.service.WorkService;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.dto.Translation_location;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.core.internal.resources.Project;
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
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TranslationController {
    final WorkService workService;
    final TranslationService translationService;
    final LangService langService;
    final FreeBoardService freeBoardService;
    @Autowired
    private DayWorkService dayWorkService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EmpService empService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private ProjectService projectService;

    @TilesDynamic("base")
    @GetMapping("/work/{type}/addTran.hs")
    public String addTran(Model model, @RequestParam(name = "id") String id,@RequestParam(name = "loai") String loai
            , @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message
            , HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        request.setAttribute("id", id);
        request.setAttribute("loai", loai);
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("workId", id);
        pra.put("dayWorkId", id);
        if (loai.equals("mywork")||loai.equals("work")){
            model.addAttribute("detailDTO", workService.getlistWorkByID(pra));
        }
        if (loai.equals("daywork")||loai.equals("mydaywork")){
            DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
            List<DayWorkDTO> lst = dayWorkService.getListDayWorkChildById(pra);
            String str = "";
            for (int i = 0; i < lst.size(); i ++){
                str += "<p>" + lst.get(i).getContents() + "</p>";
            }
            int countCheck = dayWorkService.getCountNumber(pra);
            if (countCheck == 2){
                List<DayWorkDTO> childWorkList = dayWorkService.getlistMoreWorkChildByID(pra);
                String childWorkStr = "";
                for (int j = 0; j < childWorkList.size(); j++){
                    childWorkStr += "<p>" + childWorkList.get(j).getContents() + "</p>";
                }
                model.addAttribute("childContents", childWorkStr);
            }
            model.addAttribute("countCheck", countCheck);
            dayWorkDTO.setContents(str);
            model.addAttribute("detailDTO", dayWorkDTO);
        }
        model.addAttribute("lstLang", langService.getLangList());

        return "/translation/add";
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/{type}/addTran.hs")
    public String addTransBoard(Model model, @RequestParam(name = "type") String type, @RequestParam(name = "id") String id,@RequestParam(name = "loai") String loai, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        request.setAttribute("id", id);
        request.setAttribute("loai", loai);
        model.addAttribute("lstLang", langService.getLangList());
        model.addAttribute("type", type);

        BoardControlDTO boardControlDTO = new BoardControlDTO();
        boardControlDTO.setBoardId(Integer.valueOf(id));
        boardControlDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        model.addAttribute("detailDTO", freeBoardService.getBoardView(boardControlDTO));

        return "/translation/boardadd";
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/addTran.hs")
    public String addTransProject(Model model, @RequestParam(name = "type") String type, @RequestParam(name = "id") String id,@RequestParam(required = false, name = "pjId") Integer pjId,@RequestParam(required = false, name = "dpId") Integer dpId  ,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request) {
        request.setAttribute("id", id);
        model.addAttribute("lstLang", langService.getLangList());
        model.addAttribute("type", type);
        if(type.equals("dpi")){
            DayProjectItemDTO dayProjectItemDTO= new DayProjectItemDTO();
            dayProjectItemDTO.setDpiId(Integer.parseInt(id));
            dayProjectItemDTO=projectService.getDayProjectItemByID(dayProjectItemDTO);
            dayProjectItemDTO.setTitle(dayProjectItemDTO.getProductName());
            model.addAttribute("DTO",dayProjectItemDTO);
            request.setAttribute("pjId", pjId);
            request.setAttribute("dpId", dpId);

        }
        if(type.equals("dp")){
            DayProjectDTO dayProjectDTO= new DayProjectDTO();
            dayProjectDTO.setDpId(Integer.parseInt(id));
            model.addAttribute("DTO",projectService.selectInfoDayProjectByID(dayProjectDTO));
            request.setAttribute("pjId", pjId);
        }
        if(type.equals("p")){
            model.addAttribute("DTO",projectService.selectOneMainProject(Integer.parseInt(id)));
        }

        return "/translation/projectadd";
    }

    @RequestMapping(value = "/translational/addTranProject", method = RequestMethod.POST)
    @ResponseBody
    public String addTranProject(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody TranslationDTO translationDTO) {

        EmpDTO emp = empService.getSessionUserLogin(request);

        translationDTO.setEmpCd(emp.getEmpCd());
        translationDTO.setDeptCd(emp.getDeptCd());
        translationDTO.setPositionCd(emp.getPositionCd());
        translationDTO.setTranslationStatus("0");
        translationDTO.setTranslationScore(0);
        translationDTO.setSelectYn("N");
        translationDTO.setUseYn("Y");
        int kq = translationService.InsertTran(translationDTO);

        //insert vao bang ..._tran
        int id=translationDTO.getTranslationId();
        int translationId= translationService.getTranId();

        if(translationDTO.getType().equals("dpi")){
            DayProjectItemTranslationDTO dayProjectItemTranslationDTO=new DayProjectItemTranslationDTO();
            dayProjectItemTranslationDTO.setDpiId(id);
            dayProjectItemTranslationDTO.setTranslationId(translationId);
            translationService.InsertDayProjectItemTrans(dayProjectItemTranslationDTO);
            return "dpi";
        }else if(translationDTO.getType().equals("dp")){
            DayProjectTranslationDTO dayProjectTranslationDTO=new DayProjectTranslationDTO();
            dayProjectTranslationDTO.setDpId(id);
            dayProjectTranslationDTO.setTranslationId(translationId);
            translationService.InsertDayProjectTrans(dayProjectTranslationDTO);
            return "dp";
        }else{
            ProjectTranslationDTO projectTranslationDTO=new ProjectTranslationDTO();
            projectTranslationDTO.setPjId(id);
            projectTranslationDTO.setTranslationId(translationId);
            translationService.InsertProjectTrans(projectTranslationDTO);
            return "p";
        }
    }

    @TilesDynamic("base")
    @GetMapping("/project/project/editTran.hs")
    public String editTransProject(Model model, HttpServletRequest request,@RequestParam(name = "type") String type, @RequestParam(name = "id") String id, @RequestParam(required = false, name = "pjId") Integer pjId
            ,@RequestParam(required = false, name = "dpId") Integer dpId, @RequestParam("translationId") String translationId)
    {
        request.setAttribute("id", id);
        model.addAttribute("lstLang", langService.getLangList());
        model.addAttribute("type", type);
        if(type.equals("dpi")){
            DayProjectItemDTO dayProjectItemDTO= new DayProjectItemDTO();
            dayProjectItemDTO.setDpiId(Integer.parseInt(id));
            dayProjectItemDTO=projectService.getDayProjectItemByID(dayProjectItemDTO);
            dayProjectItemDTO.setTitle(dayProjectItemDTO.getProductName());
            model.addAttribute("DTO",dayProjectItemDTO);
            request.setAttribute("pjId", pjId);
            request.setAttribute("dpId", dpId);

        }
        if(type.equals("dp")){
            DayProjectDTO dayProjectDTO= new DayProjectDTO();
            dayProjectDTO.setDpId(Integer.parseInt(id));
            model.addAttribute("DTO",projectService.selectInfoDayProjectByID(dayProjectDTO));
            request.setAttribute("pjId", pjId);
        }
        if(type.equals("p")){
            model.addAttribute("DTO",projectService.selectOneMainProject(Integer.parseInt(id)));
        }

        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("translationId", translationId);

        TranslationDTO translationDTO = translationService.getListTransById(pra);

        model.addAttribute("transDTO", translationDTO);

        return "/translation/projectedit";
    }

    @TilesDynamic("base")
    @GetMapping("/board/notice/{type}/edittrans.hs")
    public String editTransBoard(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("transId") String translationId
            ,@RequestParam("boardid") String boardId,@RequestParam("loai") String loai){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("translationId", translationId);

        TranslationDTO translationDTO = translationService.getListTransById(pra);

        model.addAttribute("transDTO", translationDTO);
        model.addAttribute("boardId", boardId);
        model.addAttribute("loai",loai);

        return "/translation/boardedit";
    }

    @RequestMapping(value = "/translational/them.hs", method = RequestMethod.POST)
    @ResponseBody
    public String them(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody TranslationDTO translationDTO) {

        //them vao translation
        int workId = translationDTO.getTranslationId();
        String langCd = LocaleContextHolder.getLocale().toString();
        String result = "";

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("workId", workId);
        pra.put("dayWorkId", translationDTO.getTranslationId());
        pra.put("boardId", translationDTO.getTranslationId());

        EmpDTO emp = empService.getSessionUserLogin(request);
        String str = translationDTO.getType();
        if (str.equals("gboardtrans") || str.equals("dboardtrans") || str.equals("fboardtrans") || str.equals("cboardtrans")){
            BoardControlDTO dbd = new BoardControlDTO();
            dbd.setLangCd(langCd);
            dbd.setBoardId(translationDTO.getTranslationId());

            BoardControlDTO boardControlDTO = freeBoardService.getBoardView(dbd);

            translationDTO.setEmpCd(emp.getEmpCd());
            translationDTO.setDeptChangeHistoryId(boardControlDTO.getDeptChangeHistoryId());
            translationDTO.setDeptCd(emp.getDeptCd());
            translationDTO.setPositionCd(emp.getPositionCd());
            translationDTO.setTranslationStatus("0");
            translationDTO.setTranslationScore(0);
            translationDTO.setSelectYn("N");
            translationDTO.setUseYn("Y");
        }


        if(str.equals("work") || str.equals("mywork")){
            WorkDTO workDTO = workService.getlistWorkByID(pra);

            translationDTO.setEmpCd(emp.getEmpCd());
            translationDTO.setDeptChangeHistoryId(workDTO.getDeptChangeHistoryId());
            translationDTO.setDeptCd(emp.getDeptCd());
            translationDTO.setPositionCd(emp.getPositionCd());
            translationDTO.setTranslationStatus("0");
            translationDTO.setTranslationScore(0);
            translationDTO.setSelectYn("N");
            translationDTO.setUseYn("Y");
        }
        if (str.equals("daywork")||str.equals("mydaywork")){
            DayWorkDTO songworkDTO = dayWorkService.getListDayWorkById(pra);

            translationDTO.setEmpCd(emp.getEmpCd());
            translationDTO.setDeptChangeHistoryId(songworkDTO.getDeptChangeHistoryId());
            translationDTO.setDeptCd(emp.getDeptCd());
            translationDTO.setPositionCd(emp.getPositionCd());
            translationDTO.setTranslationStatus("0");
            translationDTO.setTranslationScore(0);
            translationDTO.setSelectYn("N");
            translationDTO.setUseYn("Y");
        }
        //them vao work_translation
        int kq = translationService.InsertTran(translationDTO);
        int translationId= translationService.getTranId();
        WorktranslationDTO worktranslationDTO = new WorktranslationDTO();
        worktranslationDTO.setWorkId(workId);
        worktranslationDTO.setDayWorkId(workId);
        worktranslationDTO.setTranslationId(translationId);
        worktranslationDTO.setBoardId(workId);
        kq = translationService.InsertWorkTran(worktranslationDTO, str);
        result = "a";
        if (str.equals("gboardtrans")){
            result = "g";
        }
        if (str.equals("dboardtrans")){
            result = "d";
        }
        if (str.equals("fboardtrans")){
            result = "f";
        }
        if (str.equals("cboardtrans")){
            result = "c";
        }
        return result;
    }

    @TilesDynamic("base")
    @GetMapping("/translation/locationtran.hs")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/translation/locationtran @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("typeCodeList", codeService.getCodeList("locationstatus",langCd));
        model.addAttribute("lst", translationService.getLocationTran(langCd));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        model.addAttribute("currentEmp",empDTO.getEmpCd());
        model.addAttribute("CAdmin",empDTO.getAdminYn());

        return "/translation/locationtran";
    }

    @TilesDynamic("base")
    @GetMapping("/translation/addtran.hs")
    public String addlocationtran(Model model, HttpServletRequest request, HttpServletResponse response) {
        String langCd = LocaleContextHolder.getLocale().toString();
        return "/translation/addlocationtran";
    }

    @RequestMapping(value = "/translation/themTran", method = RequestMethod.POST)
    @ResponseBody
    public String themTran(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody Translation_location translation_location) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        translation_location.setEmpCd(empDTO.getEmpCd());
        translation_location.setDeptChangeHstoryId(empDTO.getDeptChangeHistoryId());
        translation_location.setDeptCd(empDTO.getDeptCd());
        translation_location.setPositionCd(empDTO.getPositionCd());
        //them o day
        int result = translationService.themTranLoca(translation_location);
        return "a";
    }

    @RequestMapping(value = "/translation/updateTranlocatoion", method = RequestMethod.POST)
    @ResponseBody
    public String updateTranlocation(ModelMap model,HttpServletRequest request, HttpServletResponse response,
                           @RequestBody EmpDTO empDTO1) {
        //update o day
        int result = translationService.updateTranlo(empDTO1);

        return "a";
    }

    @ResponseBody
    @PostMapping(value = "/translation/xoaT", produces = "application/json; charset=UTF-8")
    public int xoaT(String lstChecked) {

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {

            str += " id=" + arrOfStr[i].toString() + " or";

        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);

        int Del = translationService.xoalocationTran(param);

        return 1;
    }

    @RequestMapping(value = "api/getcommenttrans", method = RequestMethod.GET)
    @ResponseBody
    public List<TranslationDTO> getCommentTrans(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                                                @RequestBody int dayWorkCommentId) {
        HashMap pra = new HashMap();
        pra.put("dayWorkCommentId", dayWorkCommentId);

        List<TranslationDTO> lst = translationService.getCommentTrans(pra);
        return lst;
    }

    @TilesDynamic("base")
    @GetMapping("/work/{type}/addcomttrans.hs")
    public String addCmtTrans(Model model, @RequestParam(name = "id") String id,@RequestParam(name = "loai") String loai, @RequestParam(name = "dayworkid") String dwid, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        request.setAttribute("id", id);
        request.setAttribute("id", id);
        request.setAttribute("loai", loai);
        request.setAttribute("dayworkid", dwid);
        model.addAttribute("lstLang", langService.getLangList());

        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", dwid);

        DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
        List<DayWorkDTO> lst = dayWorkService.getListDayWorkChildById(pra);
        String str = "";
        for (int i = 0; i < lst.size(); i ++){
            str += "<p>" + lst.get(i).getContents() + "</p>";
        }
        dayWorkDTO.setContents(str);
        model.addAttribute("detailDTO", dayWorkDTO);
        int convertId = Integer.valueOf(id);
        List<CommentDTO> listCmt = commentService.getListCMT(pra,id,langCd);
        for (int i = 0; i < listCmt.size(); i++){
            if (convertId == listCmt.get(i).getDayWorkCommentId()){
                model.addAttribute("detailCMT", listCmt.get(i).getContents());
            }
        }
        model.addAttribute("id", id);

        return "/translation/cmtadd";
    }

    @RequestMapping(value = "/translational/cmttrans.hs", method = RequestMethod.POST)
    @ResponseBody
    public String cmtTrans(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody TranslationDTO translationDTO) {

        //them vao translation
        int workId = translationDTO.getTranslationId();
        String langCd = LocaleContextHolder.getLocale().toString();
        String result = "";

        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("workId", workId);
        pra.put("dayWorkId", translationDTO.getTranslationId());
        pra.put("dayWorkCommentId", translationDTO.getTranslationId());

        String str = translationDTO.getType();

        //lay du lieu tu daywork
        EmpDTO emp = empService.getSessionUserLogin(request);

        CommentDTO commentDTO = commentService.getListDayWorkCmtById(pra);

        //songTranslationDTO.setEmpCd(commentDTO.getEmpCd());
        translationDTO.setEmpCd(emp.getEmpCd());
        //songTranslationDTO.setDeptChangeHistoryId(commentDTO.getDeptChangeHistoryId());
        translationDTO.setDeptChangeHistoryId(emp.getDeptChangeHistoryId());
        //songTranslationDTO.setDeptCd(commentDTO.getDeptCd());
        translationDTO.setDeptCd(emp.getDeptCd());
        //songTranslationDTO.setPositionCd(commentDTO.getPositionCd());
        translationDTO.setPositionCd(emp.getPositionCd());
        translationDTO.setTranslationStatus("0");
        translationDTO.setTranslationScore(0);
        translationDTO.setSelectYn("N");
        translationDTO.setUseYn("Y");

        //them vao work_translation
        int kq = translationService.InsertTran(translationDTO);
        int translationId= translationService.getTranId();
        translationDTO.setPositionCd(commentDTO.getPositionCd());
        translationDTO.setDeptCd(commentDTO.getDeptCd());
        translationDTO.setDeptChangeHistoryId(commentDTO.getDeptChangeHistoryId());
        translationDTO.setDeptChangeHistoryId(commentDTO.getDeptChangeHistoryId());
        WorktranslationDTO worktranslationDTO = new WorktranslationDTO();
        worktranslationDTO.setWorkId(workId);
        worktranslationDTO.setDayWorkId(workId);
        worktranslationDTO.setTranslationId(translationId);
        worktranslationDTO.setDayWorkCommentId(translationDTO.getTranslationId());
        kq = translationService.InsertWorkTran(worktranslationDTO, str);
        result = "a";
        return result;
    }

    @TilesDynamic("base")
    @GetMapping("/work/{type}/edittrans.hs")
    public String edit(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("transId") String translationId
                        ,@RequestParam("dayWorkId") String dayWorkId,@RequestParam("loai") String loai, @RequestParam("dayWorkId") int id){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("translationId", translationId);
        pra.put("workId", id);
        pra.put("dayWorkId", id);

        TranslationDTO translationDTO = translationService.getListTransById(pra);
        int number = translationService.checkBelongCmtTrans(pra);
        System.out.println("DDDDDD: "+number);

        if (number!=0){
            if (langCd.equals("ko")){
                translationDTO.setType("disabled");
                translationDTO.setTitle("댓글 번역");
            }if (langCd.equals("en")){
                translationDTO.setType("disabled");
                translationDTO.setTitle("Comment translate");
            }if (langCd.equals("vt")){
                translationDTO.setType("disabled");
                translationDTO.setTitle("Dịch bình luận");
            }
        }

        if (loai.equals("daywork")||loai.equals("mydaywork")){
            DayWorkDTO dayWorkDTO = dayWorkService.getListDayWorkById(pra);
            List<DayWorkDTO> lst = dayWorkService.getListDayWorkChildById(pra);
            String str = "";
            for (int i = 0; i < lst.size(); i ++){
                str += "<p>" + lst.get(i).getContents() + "</p>";
            }
            int countCheck = dayWorkService.getCountNumber(pra);
            if (countCheck == 2){
                List<DayWorkDTO> childWorkList = dayWorkService.getlistMoreWorkChildByID(pra);
                String childWorkStr = "";
                for (int j = 0; j < childWorkList.size(); j++){
                    childWorkStr += "<p>" + childWorkList.get(j).getContents() + "</p>";
                }
                model.addAttribute("childContents", childWorkStr);
            }
            model.addAttribute("countCheck", countCheck);
            dayWorkDTO.setContents(str);
            model.addAttribute("detailDTO", dayWorkDTO);
        }

        model.addAttribute("workDTO", translationDTO);
        model.addAttribute("dayWorkId", dayWorkId);
        model.addAttribute("loai",loai);

        return "/translation/edit";
    }

    @TilesDynamic("base")
    @GetMapping("/work/{type}/editworktrans.hs")
    public String editTransWork(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("transId") String translationId
            ,@RequestParam("WorkId") String workId,@RequestParam("loai") String loai){
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/work/mywork @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("translationId", translationId);
        pra.put("workId", workId);
        model.addAttribute("detailDTO", workService.getlistWorkByID(pra));


        TranslationDTO translationDTO = translationService.getListTransById(pra);

        model.addAttribute("workDTO", translationDTO);
        model.addAttribute("dayWorkId", workId);
        model.addAttribute("loai",loai);
        return "/translation/edit";
    }

    @RequestMapping(value = "/work/edittrans", method = RequestMethod.POST)
    @ResponseBody
    public String doEdit(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody TranslationDTO translationDTO){

        EmpDTO emp = empService.getSessionUserLogin(request);
        translationDTO.setEmpCd(emp.getEmpCd());
        translationDTO.setDeptCd(emp.getDeptCd());
        translationDTO.setPositionCd(emp.getPositionCd());
        translationService.updateTrans(translationDTO);
        return "a";
    }

    @RequestMapping(value = "/work/translation/updatestatus", method = RequestMethod.POST)
    @ResponseBody
    public String updateInfor(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("id") String id,@RequestParam("typeCheck") String typeCheck,@RequestParam("trangthai") String trangthai) {
        //update infor
        HashMap param = new HashMap();
        param.put("id",id);
        param.put("trangthai",trangthai);

        if(typeCheck.equals("status")){
            int a= translationService.updateStatusTrans(param);
        }
        return "A";
    }
}
