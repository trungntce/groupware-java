package kr.co.hs.position.controller;

import com.google.gson.Gson;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.position.dto.PositionDTO;
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
import java.util.HashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PositionController {
    final PositionService positionService;

    @TilesDynamic("base")
    @GetMapping("/position/list.hs")
    public String list(Model model, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message, HttpServletRequest request, HttpServletResponse response) {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/position/list @@@@@@@@@");
        String langCd = LocaleContextHolder.getLocale().toString();
        model.addAttribute("lstPo",positionService.getPositionList(langCd));
        request.setAttribute("title", title);
        request.setAttribute("message", message);

        return "/position/list";
    }

    @ResponseBody
    @PostMapping(value = "/position/deletePo", produces = "application/json; charset=UTF-8")
    public int deletePo(String lstChecked) {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/position/delete @@@@@@@@@");

        HashMap param = new HashMap();
        String str = "";
        String[] arrOfStr = lstChecked.split(",");
        for (int i = 0; i < arrOfStr.length; i++) {
            str += " position_cd=" + arrOfStr[i].toString() + " or";
        }
        str = str.substring(0, str.length() - 2);
        param.put("param", str);
        int Del = positionService.DeletePo(param);
        log.error("{}", new Gson().toJson(user));
        return 1;
    }

    @TilesDynamic("base")
    @GetMapping("/position/addPo.hs")
    public String addPo(Model model, HttpServletRequest request, HttpServletResponse response) {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/position/addPo @@@@@@@@@");
        log.error("{}", new Gson().toJson(user));

        return "/position/add";
    }

    @RequestMapping(value = "/position/themPo", method = RequestMethod.POST)
    @ResponseBody
    public String them(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody PositionDTO positionDTO) {
        //them o day
        int result = positionService.InsertPo(positionDTO);
        return "a";

    }

    @TilesDynamic("base")
    @GetMapping("/position/editPo.hs")
    public String editPo(Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "id") String id) {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.error("/position/addboard @@@@@@@@@");
        log.error("{}", new Gson().toJson(user));


        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("langCd",langCd);
        param.put("positionCd",id);
        model.addAttribute("lstEdit",positionService.getlistbyID(param));
        System.out.println("Dxddddd: "+positionService.getlistbyID(param));

        return "/position/edit";
    }

    @RequestMapping(value = "/position/suaPo", method = RequestMethod.POST)
    @ResponseBody
    public String sua(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                      @RequestBody PositionDTO positionDTO) {
        int result = positionService.updatePo(positionDTO);
        return "a";

    }


}
