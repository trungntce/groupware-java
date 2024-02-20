package kr.co.hs.sboard.controller;

import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.deptconfig.dto.DeptDTO;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.sboard.dto.SBoardCommentDTO;
import kr.co.hs.sboard.dto.SBoardDmtDTO;
import kr.co.hs.sboard.dto.SBoardMainDTO;
import kr.co.hs.sboard.service.SBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SBoardController {
    final SBoardService sBoardService;
    final EmpService empService;

    @Autowired
    ServletContext context;

    @TilesDynamic("base")
    @GetMapping("/board/{boardType}/main.hs")
    public String smain(@PathVariable("boardType") String boardType, Model model, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        HashMap param = new HashMap();
        log.error("{}", boardType);

        param.put("param", timchuoi(boardType));

        model.addAttribute("mainlst", sBoardService.getmainboard(param));
        model.addAttribute("mainDMT", sBoardService.getmainDMT(param));
        model.addAttribute("boardType", boardType);

        return "/sboard/main";
    }

    @TilesDynamic("base")
    @GetMapping("/board/gallery.hs")
    public String gallery(Model model, @RequestParam(name = "idDmt") int idDmt, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        model.addAttribute("idDmt", idDmt);
        model.addAttribute("namedmt", sBoardService.getnamedmt(idDmt));
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        return "/sboard/gallery";
    }

    @TilesDynamic("base")
    @GetMapping("/board/event.hs")
    public String event(Model model, @RequestParam(name = "idDmt") int idDmt, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        model.addAttribute("idDmt", idDmt);
        model.addAttribute("namedmt", sBoardService.getnamedmt(idDmt));
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        return "/sboard/event";
    }

    @TilesDynamic("base")
    @GetMapping("/board/freestyle.hs")
    public String freestyle(Model model, @RequestParam(name = "idDmt") int idDmt) {
        model.addAttribute("idDmt", idDmt);
        model.addAttribute("namedmt", sBoardService.getnamedmt(idDmt));
        return "/sboard/freestyle";
    }

    @RequestMapping(value = "/board/addFreeStyle", method = RequestMethod.POST)
    @ResponseBody
    public String addFreeStyle(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                               @RequestBody SBoardMainDTO sBoardMainDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        HashMap pra = new HashMap();
        pra.put("langCd", LocaleContextHolder.getLocale().toString());
        pra.put("empCd", empDTO.getEmpCd());

        EmpDTO getten = empService.GetProfile(pra);
        //insert here

        sBoardMainDTO.setViewCount(0);
        sBoardMainDTO.setIdComment(null);
        sBoardMainDTO.setCreateBy(getten.getEmpName());
        int rs = sBoardService.addmain(sBoardMainDTO);
        return "a";

    }

    @RequestMapping(value = "/addGalleryStyle", method = RequestMethod.POST)
    public String addGalleryStyle(SBoardMainDTO sBoardMainDTO, Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        HashMap pra = new HashMap();
        pra.put("langCd", LocaleContextHolder.getLocale().toString());
        pra.put("empCd", empDTO.getEmpCd());
        EmpDTO getten = empService.GetProfile(pra);

        //insert here

        sBoardMainDTO.setViewCount(0);
        sBoardMainDTO.setIdComment(null);
        sBoardMainDTO.setCreateBy(getten.getEmpName());

        String dan1 = "";
        String dan2 = "";


        try {
            //coverimage
            MultipartFile multipartFileCover = sBoardMainDTO.getMultipartCover();
            String fileName = multipartFileCover.getOriginalFilename();
            File file = new File(context.getRealPath("resources/Upload/images"), fileName);
            multipartFileCover.transferTo(file);
            dan1 = "/resources/Upload/images/" + fileName;

            //file
            MultipartFile multipartFile = sBoardMainDTO.getMultipartFile();
            String fileName1 = multipartFile.getOriginalFilename();
            File file1 = new File(context.getRealPath("resources/Upload/files"), fileName1);
            multipartFile.transferTo(file1);
            dan2 = "/resources/Upload/files/" + fileName1;

        } catch (Exception e) {

        }

        sBoardMainDTO.setImgCover(dan1);
        sBoardMainDTO.setContent(sBoardMainDTO.getNoidung());
        sBoardMainDTO.setAttachFile(dan2);
        int rs = sBoardService.addmain(sBoardMainDTO);

        return "redirect:/board/gallery.hs?idDmt=" + sBoardMainDTO.getIdDmt() + "&&title=success&&message=success!";

    }

    @RequestMapping(value = "/addEventStyle", method = RequestMethod.POST)
    public String addEventStyle(SBoardMainDTO sBoardMainDTO, Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        HashMap pra = new HashMap();
        pra.put("langCd", LocaleContextHolder.getLocale().toString());
        pra.put("empCd", empDTO.getEmpCd());
        EmpDTO getten = empService.GetProfile(pra);
        //insert here
        sBoardMainDTO.setViewCount(0);
        sBoardMainDTO.setIdComment(null);
        sBoardMainDTO.setCreateBy(getten.getEmpName());

        String dan1 = "";
        String dan2 = "";

        try {
            //coverimage
            MultipartFile multipartFileCover = sBoardMainDTO.getMultipartCover();
            String fileName = multipartFileCover.getOriginalFilename();
            File file = new File(context.getRealPath("resources/Upload/images"), fileName);
            multipartFileCover.transferTo(file);
            dan1 = "/resources/Upload/images/" + fileName;

            //file
            MultipartFile multipartFile = sBoardMainDTO.getMultipartFile();
            String fileName1 = multipartFile.getOriginalFilename();
            File file1 = new File(context.getRealPath("resources/Upload/files"), fileName1);
            multipartFile.transferTo(file1);
            dan2 = "/resources/Upload/files/" + fileName1;

        } catch (Exception e) {

        }

        sBoardMainDTO.setImgCover(dan1);
        sBoardMainDTO.setContent(sBoardMainDTO.getNoidung());
        sBoardMainDTO.setAttachFile(dan2);
        int rs = sBoardService.addmain(sBoardMainDTO);

        return "redirect:/board/event.hs?idDmt=" + sBoardMainDTO.getIdDmt() + "&&title=success&&message=success!";

    }

    @TilesDynamic("base")
    @GetMapping("/board/editBoardPublic.hs")
    public String editBoardPublic(Model model, @RequestParam(name = "id") int id, @RequestParam(name = "idstyle") int idstyle) {


        model.addAttribute("lstDetail", sBoardService.getDetail(id));
        if (idstyle == 1) {
            return "/sboard/editfreestyle";
        } else if (idstyle == 2) {
            return "/sboard/editgallery";
        } else {
            return "/sboard/editevent";
        }

    }

    @RequestMapping(value = "/board/updateFreeStyle", method = RequestMethod.POST)
    @ResponseBody
    public String updateFreeStyle(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody SBoardMainDTO sBoardMainDTO) {
        //update here
        int rs = sBoardService.updateSBoard(sBoardMainDTO);
        return "a";

    }

    @RequestMapping(value = "/updategallery", method = RequestMethod.POST)
    public String updategallery(SBoardMainDTO sBoardMainDTO, Model model, HttpServletRequest request) {
        sBoardMainDTO.setId(sBoardMainDTO.getIdTest());
        //update here
        String dan2 = "";

        try {

            //file
            MultipartFile multipartFile = sBoardMainDTO.getMultipartFile();
            String fileName1 = multipartFile.getOriginalFilename();
            File file1 = new File(context.getRealPath("resources/Upload/files"), fileName1);
            multipartFile.transferTo(file1);
            dan2 = "/resources/Upload/files/" + fileName1;

        } catch (Exception e) {

        }

        sBoardMainDTO.setContent(sBoardMainDTO.getNoidung());
        sBoardMainDTO.setAttachFile(dan2);
        int rs = sBoardService.updateSBoard(sBoardMainDTO);

        return "redirect:/board/detail.hs?id=" + sBoardMainDTO.getId();

    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public String updateEvent(SBoardMainDTO sBoardMainDTO, Model model, HttpServletRequest request) {
        sBoardMainDTO.setId(sBoardMainDTO.getIdTest());
        //update here
        String dan2 = "";

        try {

            //file
            MultipartFile multipartFile = sBoardMainDTO.getMultipartFile();
            String fileName1 = multipartFile.getOriginalFilename();
            File file1 = new File(context.getRealPath("resources/Upload/files"), fileName1);
            multipartFile.transferTo(file1);
            dan2 = "/resources/Upload/files/" + fileName1;

        } catch (Exception e) {

        }

        sBoardMainDTO.setContent(sBoardMainDTO.getNoidung());
        sBoardMainDTO.setAttachFile(dan2);
        int rs = sBoardService.updateSBoard(sBoardMainDTO);

        return "redirect:/board/detail.hs?id=" + sBoardMainDTO.getId();

    }


    @TilesDynamic("base")
    @GetMapping("/board/topic.hs")
    public String topic(Model model, @RequestParam(name = "boardType") String boardType, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        model.addAttribute("str", timchuoi(boardType));
        model.addAttribute("lstBoardType", sBoardService.getBoardTypeTopic());

        return "/sboard/topic";
    }

    @RequestMapping(value = "/board/addBoardType", method = RequestMethod.POST)
    @ResponseBody
    public String them(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                       @RequestBody SBoardDmtDTO sBoardDmtDTO) {
        String ten = sBoardDmtDTO.getStr().split("=")[0];
        int giatri = Integer.parseInt(sBoardDmtDTO.getStr().split("=")[1]);

        if (ten.equals("team")) {
            sBoardDmtDTO.setTeam(giatri);
        }
        if (ten.equals("dept")) {
            sBoardDmtDTO.setDept(giatri);
        }
        if (ten.equals("company")) {
            sBoardDmtDTO.setCompany(giatri);
        }
        if (ten.equals("general")) {
            sBoardDmtDTO.setGeneral(giatri);
        }


        int kq = sBoardService.addTbDmt(sBoardDmtDTO);
        return "a";

    }

    public String timchuoi(String boardType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        List<DeptDTO> lst = sBoardService.getTreeOfBoard(empDTO.getEmpCd());
        Integer team = null;
        Integer dept = null;
        Integer company = null;
        Integer general = null;
        try {
            team = lst.get(3).getDeptCd();
        } catch (Exception e) {
        }
        try {
            dept = lst.get(2).getDeptCd();
        } catch (Exception e) {
        }
        try {
            company = lst.get(1).getDeptCd();
        } catch (Exception e) {
        }
        try {
            general = lst.get(0).getDeptCd();
        } catch (Exception e) {
        }

        String str = boardType;
        if (boardType.equals("team")) {
            str += "=" + team;
        } else if (boardType.equals("dept")) {
            str += "=" + dept;
        } else if (boardType.equals("company")) {
            str += "=" + company;
        } else {
            str += "=" + general;
        }
        return str;
    }

    @TilesDynamic("base")
    @GetMapping("/board/list.hs")
    public String list(Model model, @RequestParam(name = "boardType") String boardType, @RequestParam(name = "idDmt") int idDmt, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);

        HashMap param = new HashMap();
        log.error("{}", boardType);

        param.put("param", timchuoi(boardType));

        model.addAttribute("mainlst", sBoardService.getmainboard(param));
        model.addAttribute("boardType", boardType);
        model.addAttribute("idDmtPass", idDmt);
        model.addAttribute("getstyle", sBoardService.getstyle1(idDmt));

        return "/sboard/list";
    }

    @TilesDynamic("base")
    @GetMapping("/board/detail.hs")
    public String detail(Model model, @RequestParam(name = "id") int id, HttpServletRequest request, @RequestParam(required = false, name = "title") String title, @RequestParam(required = false, name = "message") String message) {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("langCd",langCd);
        param.put("id",id);
        model.addAttribute("lstCm",sBoardService.getComment(param));
        model.addAttribute("lstDetail", sBoardService.getDetail(id));
        return "/sboard/detail";
    }
    @TilesDynamic("base")
    @GetMapping("/board/Delete.hs")
    public String DeleteBoard(Model model, @RequestParam(name = "id") int id) {

        int rs= sBoardService.deleteSBoard(id);
        return "redirect:/board/general/main.hs";
    }

    @RequestMapping(value = "/board/addComment", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(ModelMap model, HttpServletRequest request, HttpServletResponse response,
                             @RequestBody SBoardCommentDTO sBoardCommentDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        sBoardCommentDTO.setIdEmp(empDTO.getEmpCd());
        //insert here
        int rs = sBoardService.addComment(sBoardCommentDTO);
        return "a";

    }

    @TilesDynamic("base")
    @GetMapping("/board/DeleteComment.hs")
    public String DeleteComment(Model model, @RequestParam(name = "id") int id,@RequestParam(name = "idbaiviet") int idbaiviet) {

        int rs= sBoardService.deleteComment(id);
        return "redirect:/board/detail.hs?id="+idbaiviet+"&title=success&message=delete comment sucess";
    }

}
