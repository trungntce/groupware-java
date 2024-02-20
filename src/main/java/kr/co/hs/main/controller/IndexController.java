package kr.co.hs.main.controller;

import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.board.service.FreeBoardService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.main.dto.NoteDTO;
import kr.co.hs.main.service.MainsService;
import kr.co.hs.daywork.service.DayWorkService;
import kr.co.hs.work.service.MyWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    final EmpService empService;
    final MainsService mainsService;
    final FreeBoardService freeBoardService;
    final MyWorkService myWorkService;
    final DayWorkService dayWorkService;

    @TilesDynamic("base")
    @GetMapping("/main/index.hs")
    public String goLogin(Model model,HttpServletRequest request,@RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd",langCd);
        pra.put("empCd",empDTO.getEmpCd());
        model.addAttribute("board", freeBoardService.getListBoardMain(pra));
        model.addAttribute("work", myWorkService.getlistMyWorkMain(pra));
        model.addAttribute("daywork", dayWorkService.getListDayMyWorkMain(pra));
        EmpDTO asd =empService.GetProfile(pra);
        model.addAttribute("emp",asd);
        log.error("index @@@@@@@@@");
        request.setAttribute("title", title);
        request.setAttribute("message", message);

        //note
        model.addAttribute("note",mainsService.selectNote(empDTO.getEmpCd()));
        //endnote

        return "main/index";
    }

    @TilesDynamic("base")
    @GetMapping("/main/deleteNote")
    public String deleteNote(Model model, HttpServletRequest request, @RequestParam(name = "id") int id, RedirectAttributes ra) {
        int rs=mainsService.xoaNote(id);
        ra.addAttribute("message", "Delete Successfully !");
        ra.addAttribute("title", "success");
        return "redirect:/main/index.hs";
    }

    @RequestMapping(value = "/main/addNote", method = RequestMethod.POST)
    @ResponseBody
    public String addNote(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestBody NoteDTO noteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        noteDTO.setEmpCd(empDTO.getEmpCd());
        int a = mainsService.addNote(noteDTO);
        return "A";
    }


}
