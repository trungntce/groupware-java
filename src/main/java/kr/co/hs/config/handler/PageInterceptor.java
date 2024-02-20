package kr.co.hs.config.handler;

import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalTranslationService;
import kr.co.hs.approval.translate.confirm.service.ApprovalTranslateConfirmService;
import kr.co.hs.approval.translate.waiting.service.ApprovalTranslateWaitService;
import kr.co.hs.approval.wait.pending.service.ApprovalWaitPendingService;
import kr.co.hs.approval.wait.process.service.ApprovalWaitProcessService;
import kr.co.hs.approval.wait.waiting.service.ApprovalWaitWaitingService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.apache.tiles.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PageInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ApprovalWaitPendingService approvalWaitPendingService;

    @Autowired
    private ApprovalWaitWaitingService approvalWaitWaitingService;

    @Autowired
    private ApprovalWaitProcessService approvalWaitProcessService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ApprovalTranslateWaitService approvalTranslateWaitService;

    @Autowired
    private ApprovalTranslateConfirmService approvalTranslateConfirmService;

    @Autowired
    private EmpService empService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        if(empDTO != null){
            request.setAttribute("countApprovalPending", approvalService.countRequestApproveByEmpCd(approvalWaitPendingService.initParam(request, new ApprovalSearchDTO())));
            request.setAttribute("countApprovalWaiting", approvalService.countRequestApproveByEmpCd(approvalWaitWaitingService.initParam(request, new ApprovalSearchDTO())));
            request.setAttribute("countApprovalProcess", approvalService.countRequestApproveByEmpCd(approvalWaitProcessService.initParam(request, new ApprovalSearchDTO())));

//            if("Y".equals(empDTO.getTranslationAdminYn())){
                request.setAttribute("countApprovalTranslateConfirm", approvalService.countRequestApproveByEmpCd(approvalTranslateConfirmService.initParam(request, new ApprovalSearchDTO())));
//            }
//            if("Y".equals(empDTO.getTranslationYn())){
                request.setAttribute("countApprovalTranslateWait", approvalService.countRequestApproveByEmpCd(approvalTranslateWaitService.initParam(request, new ApprovalSearchDTO())));
//            }
        }
        return super.preHandle(request, response, handler);
    }
}
