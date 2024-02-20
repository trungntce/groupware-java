package kr.co.hs.approval.translate.history.service;

import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ApprovalTranslateHistoryService {

    @Autowired
    private ICommonDao commonDao;

    @Autowired
    private EmpService empService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setTranslateEmpCd(empDTO.getEmpCd());
        return searchDTO;
    }

    public Integer count(ApprovalSearchDTO searchDTO){
        return commonDao.selectCount("ApprovalTranslateHistory.selectCount", searchDTO);
    }

    public List<ApprovalDTO> selectList(ApprovalSearchDTO searchDTO){
        return commonDao.selectList("ApprovalTranslateHistory.selectList", searchDTO);
    }
}
