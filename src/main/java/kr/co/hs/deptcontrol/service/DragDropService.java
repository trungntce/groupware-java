package kr.co.hs.deptcontrol.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.deptcontrol.dto.DeptControlDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DragDropService {
    @Autowired
    private ICommonDao iCommonDao;

    public List<DeptControlDTO> getTreeDept(String langCd){
        return iCommonDao.selectList("DatDept.treeDept", langCd);
    }
    public List<DeptControlDTO> getTreeDeptEmpName(DeptControlDTO deptControlDTO){
        return iCommonDao.selectList("DatDept.treeDeptEmpName", deptControlDTO);
    }

    public int upDeptTree( HashMap param) {
        //iCommonDao.updateData("DatDept.upDeptLevelTree",param);
        return iCommonDao.updateData("DatDept.upDeptTree",param);
    }

    public int upEmpTree( HashMap param) {
        return iCommonDao.updateData("DatDept.upEmpTree",param);
    }

    public int getPositionLevelNumber( HashMap param){
        return iCommonDao.selectCount("DatDept.getPositionLevelNumber", param);
    }
}
