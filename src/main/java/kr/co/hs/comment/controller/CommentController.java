package kr.co.hs.comment.controller;

import kr.co.hs.comment.dto.CommentDTO;
import kr.co.hs.comment.service.CommentService;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.lang.dto.LangDTO;
import kr.co.hs.lang.service.LangService;
import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.daywork.service.DayWorkService;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class CommentController {
    final TranslationService translationService;
    final LangService langService;
    @Autowired
    private EmpService empService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DayWorkService dayWorkService;

    @TilesDynamic("base")
    @GetMapping("/work/{type}/addcomment.hs")
    public String addTran(Model model, @RequestParam(name = "id") String id, @RequestParam(name = "loai") String loai, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String langCd = LocaleContextHolder.getLocale().toString();
        log.error("/emp/profile @@@@@@@@@");
        request.setAttribute("id", id);
        request.setAttribute("loai", loai);
        model.addAttribute("lstLang", langService.getLangList());
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dayWorkId", id);

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

        //get detail translation
        List<TranslationDTO> transList = translationService.getListTransByDayWorkId(pra);
        model.addAttribute("transDetail", transList);

        return "/comment/add";
    }

    @TilesDynamic("base")
    @GetMapping("/work/{type}/editcomment.hs")
    public String edtComment(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("dayWorkId") String dayWorkId
            ,@RequestParam("dayWorkCommentId") String id,@RequestParam("loai") String loai) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/emp/profile @@@@@@@@@");
        HashMap param = new HashMap();
        request.setAttribute("id", id);
        request.setAttribute("loai", loai);

        param.put("dayWorkCommentId", id);

        CommentDTO commentDTO = commentService.getCmtById(param);
        List<LangDTO> lst = langService.getLangList();
        for (int i = 0; i < lst.size(); i++){
            if (lst.get(i).getLangCd().equals(commentDTO.getLangCd())){
                lst.get(i).setStr("selected");
            }
        }
        model.addAttribute("commentDTO", commentDTO);
        model.addAttribute("dayWorkId", dayWorkId);
        model.addAttribute("lstLang", lst);

        return "/comment/edit";
    }

    @RequestMapping(value = "/translational/addcmt.hs", method = RequestMethod.POST)
    @ResponseBody
    public String cmtTrans(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                           @RequestBody CommentDTO commentDTO) {
        EmpDTO emp = empService.getSessionUserLogin(request);

        commentDTO.setEmpCd(emp.getEmpCd());
        commentDTO.setDeptCd(emp.getDeptCd());
        commentDTO.setPositionCd(emp.getPositionCd());
        commentDTO.setUseYn("Y");
        commentService.insertComment(commentDTO);

        return "a";
    }

    @RequestMapping(value = "/translational/doeditcomment.hs", method = RequestMethod.POST)
    @ResponseBody
    public String doEditComment(Model model, HttpServletRequest request, HttpServletResponse response, @RequestBody CommentDTO commentDTO){
        EmpDTO emp = empService.getSessionUserLogin(request);
        commentDTO.setEmpCd(emp.getEmpCd());
        commentDTO.setDeptCd(emp.getDeptCd());
        commentDTO.setPositionCd(emp.getPositionCd());
        commentService.updateComment(commentDTO);

        return commentDTO.getType();
    }
}
