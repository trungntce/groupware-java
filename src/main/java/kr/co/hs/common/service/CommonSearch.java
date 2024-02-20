package kr.co.hs.common.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.dto.SearchDTO;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonSearch {

    @Autowired
    private ICommonDao iCommonDao;

    private HashMap mapSearchObject(String key){
        key = "'%" + key + "%'";
        HashMap param = new HashMap();
        param.put("key", key);
        return param;
    }

    public String empCommonSearch(String key){
        List<SearchDTO> empCdList = iCommonDao.selectList("CommonSearch.empCommonSearch", mapSearchObject(key));
        System.out.println("DXD: =>> "+empCdList);
        String result = "(";
        for (int i = 0; i < empCdList.size(); i++){
            if (i == (empCdList.size() -1)){
                result += empCdList.get(i).getEmpCd() + ")";
            }else {
                result += empCdList.get(i).getEmpCd() + ",";
            }
        }
        return result;
    }

    public String positionCommonSearch(String key){
        List<SearchDTO> empCdList = iCommonDao.selectList("CommonSearch.positionCommonSearch", mapSearchObject(key));
        String result = "(";
        for (int i = 0; i < empCdList.size(); i++){
            if (i == (empCdList.size() -1)){
                result += empCdList.get(i).getEmpCd() + ")";
            }else {
                result += empCdList.get(i).getEmpCd() + ",";
            }
        }
        return result;
    }

    public String deptCommonSearch(String key){
        List<SearchDTO> empCdList = iCommonDao.selectList("CommonSearch.deptCommonSearch", mapSearchObject(key));
        String result = "(";
        for (int i = 0; i < empCdList.size(); i++){
            if (i == (empCdList.size() -1)){
                result += empCdList.get(i).getEmpCd() + ")";
            }else {
                result += empCdList.get(i).getEmpCd() + ",";
            }
        }
        return result;
    }

    public DeptSearchDTO deptSearchDTO(){
        String langCd = LocaleContextHolder.getLocale().toString();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmpDTO empDTO = ((UserDetail) authentication.getPrincipal()).getEmp();
        DeptSearchDTO ds = new DeptSearchDTO();
        ds.setDeptCd(empDTO.getDeptCd());
        ds.setLangCd(langCd);
        ds.setTranslationSpecific(empDTO.getTranslationYn());
        ds.setAdminSpecitfic(empDTO.getAdminYn());
        return ds;
    }
    
    /*
    * Dept for Accounting person of the Project function
    * */

}
