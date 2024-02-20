package kr.co.hs.common.preparer;

import com.google.gson.Gson;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.wait.pending.service.ApprovalWaitPendingService;
import kr.co.hs.approval.wait.process.service.ApprovalWaitProcessService;
import kr.co.hs.approval.wait.waiting.service.ApprovalWaitWaitingService;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.menu.dto.MenuSearchDTO;
import kr.co.hs.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class Preparer implements ViewPreparer {

    final MenuService menuService;
    final EmpService empService;

    @Override
    public void execute(Request request, AttributeContext attributeContext) throws PreparerException {
        log.error("@@@@@@@@@@@@@@@@@@@@@@@@@@prepare@@@@@@@@@@@@@@@@@@@@@@@@@@");
        // get Security Session
        UserDetail user = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // type change UserDetail to EmpDTO
        EmpDTO emp = (EmpDTO)user.getEmp();

        // get login user emp_cd
        Integer empCd = emp.getEmpCd();
        String empId = emp.getEmpId();

        MenuSearchDTO menuSearchDTO = new MenuSearchDTO();
        menuSearchDTO.setEmpCd(empCd);

        menuSearchDTO.setMenuLevel(1);
        attributeContext.putAttribute("firstMenu", new Attribute(menuService.getMenu(menuSearchDTO)), true);

        menuSearchDTO.setMenuLevel(2);
        attributeContext.putAttribute("secondMenu", new Attribute(menuService.getMenu(menuSearchDTO)), true);

        menuSearchDTO.setMenuLevel(3);
        attributeContext.putAttribute("thirdMenu", new Attribute(menuService.getMenu(menuSearchDTO)), true);

        String langCd = LocaleContextHolder.getLocale().toString();

        HashMap pra = new HashMap();
        pra.put("langCd",langCd);
        pra.put("empCd",emp.getEmpCd());
        EmpDTO infor =empService.GetProfile(pra);
        attributeContext.putAttribute("empName", new Attribute(infor.getEmpDeptName()), true);
        attributeContext.putAttribute("empNameId", new Attribute(infor.getEmpId()), true);

        emp.setLangCd(langCd);
        attributeContext.putAttribute("empAuthList", new Attribute(empService.getEmpAuthList(emp)), true);

    }
}
