package kr.co.hs.menuauthen.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.management.dto.ManagementEmpDTO;
import kr.co.hs.menuauthen.dto.ManagementMenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementMenuService {
    final ICommonDao iCommonDao;

    public List<ManagementEmpDTO> getMenuRoleList () {
        return iCommonDao.selectList("ManagementMenuRole.selectRoleList", null);
    }

    public void menuAuthEdit(ManagementEmpDTO managementEmpDTO){
        iCommonDao.updateData("ManagementMenuRole.updateMenuRoleY", managementEmpDTO);
        iCommonDao.updateData("ManagementMenuRole.updateMenuRoleN", managementEmpDTO);
    }
    public List<ManagementMenuDTO> getRoleList(ManagementMenuDTO managementEmpDTO) {
        return iCommonDao.selectList("ManagementMenuRole.selectMenuRoleList", managementEmpDTO);
    }
    public List<ManagementMenuDTO> getNameMenuList(ManagementMenuDTO managementMenuDTO){
        return iCommonDao.selectList("ManagementMenuRole.selectNameMenuList", managementMenuDTO);
    }

    public void customeUpdate(ManagementMenuDTO managementMenuDTO){
        iCommonDao.updateData("ManagementMenuRole.updateCustom", managementMenuDTO);
        iCommonDao.updateData("ManagementMenuRole.updateCustomN", managementMenuDTO);
    }

    public int updateTransRolse(HashMap param){
        return iCommonDao.updateData("ManagementMenuRole.updateTranslationRole", param);
    }
}
