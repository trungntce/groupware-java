package kr.co.hs.common.api;

import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.file.FileService;
import kr.co.hs.common.file.IFileUpload;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.files.dto.FilesDTO;
import kr.co.hs.files.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class APIController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EmpService empService;

    @Autowired
    private FilesService filesService;

    @GetMapping(value = "/api/bellnotice", produces = "application/json; charset=UTF-8")
    public Object getBellNotice(HttpServletRequest request){

        EmpDTO emp = empService.getSessionUserLogin(request);
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap param = new HashMap();
        param.put("empCd",emp.getEmpCd());
        String str = emp.getEmpCd().toString();
        param.put("empCd_", str);
        param.put("adminYn", emp.getAdminYn());
        param.put("translationYn", emp.getTranslationYn());
        param.put("langCd",langCd);

        Map<String, Object> mResult = new LinkedHashMap<>();

        mResult.put("data1", noticeService.bellNotice(param, emp.getAdminYn(), emp.getTranslationYn()));
        mResult.put("data2", noticeService.sumNotice(param, emp.getAdminYn(), emp.getTranslationYn()));

        return mResult;
    }

    @PostMapping("/api/readnotice/trans")
    public String readNoticeTrans(@RequestBody NoticeDTO noticeDTO, HttpServletRequest request){

        EmpDTO emp = empService.getSessionUserLogin(request);
        HashMap param = new HashMap();
        String str = emp.getEmpCd().toString();

        param.put("noticeCd", noticeDTO.getNoticeCd());
        param.put("type", noticeDTO.getType());
        param.put("empCd", emp.getEmpCd());
        noticeService.readNotificationReceiver(param);
        return "ok";
    }

    @PostMapping(value = "/api/files/upload", produces = "application/json; charset=UTF-8")
    public FilesDTO uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        return filesService.upload(request, file);
    }
}
