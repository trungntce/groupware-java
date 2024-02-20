package kr.co.hs.deptcontrol.controller;

import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.deptcontrol.dto.DeptControlDTO;
import kr.co.hs.deptcontrol.service.DragDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class DragDropController {

    @Autowired
    private DragDropService dragDropService;

    @TilesDynamic("base")
    @GetMapping("/board/dragdrop/dragdrop.hs")
    public String index(Model model, DeptControlDTO deptControlDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        return "/hrga/dept/dragdrop";
    }
}