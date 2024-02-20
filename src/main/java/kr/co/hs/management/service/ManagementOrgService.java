package kr.co.hs.management.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.management.dto.ManagementOrgDTO;
import kr.co.hs.management.dto.ManagementOrgSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementOrgService {
    final ICommonDao iCommonDao;

    public int count(ManagementOrgSearchDTO managementOrgSearchDTO) {
        return iCommonDao.selectCount("ManagementOrg.selectCount", managementOrgSearchDTO);
    }

    public List<ManagementOrgDTO> getDeptList(ManagementOrgSearchDTO managementOrgSearchDTO) {
        return iCommonDao.selectList("ManagementOrg.selectDeptList",managementOrgSearchDTO);
    }

    public ManagementOrgDTO getDeptView(ManagementOrgSearchDTO managementOrgSearchDTO) {
        return iCommonDao.selectOne("ManagementOrg.selectDeptView",managementOrgSearchDTO);
    }

    public List<ManagementOrgDTO> getLevelDept(int deptLevel) {
        return iCommonDao.selectList("ManagementOrg.selectOneLevelDept", deptLevel);
    }

    public void orgInfoSave(ManagementOrgDTO managementOrgDTO) {
        managementOrgDTO.setDeptLevel(managementOrgDTO.getDeptLevel()+1);
        iCommonDao.insertData("ManagementOrg.insertOne", managementOrgDTO);
    }

    public void orgInfoEdit(ManagementOrgDTO managementOrgDTO) {
        managementOrgDTO.setDeptLevel(managementOrgDTO.getDeptLevel()+1);
        iCommonDao.updateData("ManagementOrg.updateOne", managementOrgDTO);
    }

}



