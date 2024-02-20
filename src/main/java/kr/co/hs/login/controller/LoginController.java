package kr.co.hs.login.controller;

import com.google.gson.Gson;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.common.tiles.TilesDynamic;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.management.dto.ManagementEmpDTO;
import kr.co.hs.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController{

    @Autowired private SessionRegistry sessionRegistry = null;

    final MenuService menuService;
    final EmpService empService;

    @Autowired
    AuthenticationManager authenticationManager;

    @TilesDynamic("none")
    @GetMapping("/login.hs")
    public String goLogin() {
        log.error("@@@@@@@@@@@@@@@@TEST LOGIN!!!!@@@@@@@@@@@@@@@@@");
//        empService.getEmp("test");
        //model.addAttribute("login",login);
        return "login";
    }

    @TilesDynamic("base")
    @GetMapping("/editAuth.hs" )
    public String editAuth(@ModelAttribute("emp") EmpDTO empDTO, HttpServletRequest request, HttpServletResponse response){
        System.out.println("hello to change name function");
        String langCd = empDTO.getSelectLangCd();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Em khong biet: "+((UserDetail) authentication.getPrincipal()).getEmp());
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        String changeId = empDTO.getEmpId();
        List<EmpDTO> list = empService.getPassWord(empDTO);
        String getPassWord = list.get(0).getEmpPw();

        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(changeId, getPassWord)
        );
        //set data to Userdetails
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication != null){
            System.out.println("Do Xuan Dat 111111111111111111111");
            EmpDTO emp = ((UserDetail)authentication.getPrincipal() ).getEmp();
            HttpSession session = request.getSession(true);
            log.info("sessionId ===> {}", session.getId());

            sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());
            session.setAttribute("_user", emp);
        }
        System.out.println("Em khong biet2: "+((UserDetail) authentication.getPrincipal()).getEmp());

        return "redirect:/main/index.hs?lang="+langCd;
    }

    @ResponseBody
    @PostMapping("/editPassword.hs")
    public String doEidtPassword(Model model, @ModelAttribute("emp") EmpDTO empDTO) {
        log.error("{}", new Gson().toJson(empDTO));
        return empService.updateEmpPassword(empDTO);
    }

}
