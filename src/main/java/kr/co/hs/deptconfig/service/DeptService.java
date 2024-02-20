package kr.co.hs.deptconfig.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.deptconfig.dto.DeptDTO;
import kr.co.hs.deptconfig.dto.DeptSearchDTO;
import kr.co.hs.deptconfig.dto.DeptTreeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeptService {

    final ICommonDao iCommonDao;

    public List<DeptDTO> getDept(DeptSearchDTO deptSearchDTO) {
        return iCommonDao.selectList("Dept.selectOneLevelDept", deptSearchDTO);
    }

    public List<DeptDTO> getDeptLevelList(String langCd) {
        return iCommonDao.selectList("Dept.selectDeptLevelList", langCd);
    }

    public List<DeptTreeDTO> getDeptTreeList(String langCd){
        return iCommonDao.selectList("Dept.selectDeptTreeList", langCd);
    }
    public List<DeptDTO> getDeptLang(String langCd) {
        return iCommonDao.selectList("Dept.getDeptLang",langCd);
    }

    public int getDepthis(int deptCd) {
        return iCommonDao.selectOne("Dept.getdepthis", deptCd);
    }

    public List<DeptDTO> selectDeptLevel(String langCd) {
        return iCommonDao.selectList("Dept.selectDeptLevel", langCd);
    }

    public List<DeptDTO> selectDeptLowLevel(DeptSearchDTO deptSearchDTO) {
        return iCommonDao.selectList("Dept.selectDeptLowLevel", deptSearchDTO);
    }

    public List<DeptDTO> selectCompany(String langCd) {
        return iCommonDao.selectList("Dept.selectCompany", langCd);
    }

    public List<DeptDTO> selectDeptBoard(String langCd) {
        return iCommonDao.selectList("Dept.selectDeptBoard", langCd);
    }
    public String selectDeptLevelbyDeptCd(HashMap param) {
        return iCommonDao.selectOne("Dept.selectDeptLevelbyDeptCd", param);
    }
    public List<DeptDTO> selectListDeptId(HashMap param) {
        return iCommonDao.selectList("Dept.selectListDeptId", param);
    }
}
