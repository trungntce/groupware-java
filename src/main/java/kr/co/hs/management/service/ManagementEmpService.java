package kr.co.hs.management.service;

import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.management.dto.ManagementEmpDTO;
import kr.co.hs.management.dto.ManagementEmpSearchDTO;
import kr.co.hs.management.dto.ManagementOrgDTO;
import kr.co.hs.management.dto.ManagementOrgSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagementEmpService {

    final ICommonDao iCommonDao;

    public int count(ManagementEmpSearchDTO managementEmpSearchDTO) {
        return iCommonDao.selectCount("ManagementEmp.selectCount", managementEmpSearchDTO);
    }

    public List<ManagementEmpDTO> getEmpList(ManagementEmpSearchDTO managementEmpSearchDTO) {
        return iCommonDao.selectList("ManagementEmp.selectEmpList", managementEmpSearchDTO);
    }

    public ManagementEmpDTO getEmpView(ManagementEmpSearchDTO managementEmpSearchDTO) {
        return iCommonDao.selectOne("ManagementEmp.selectEmpView", managementEmpSearchDTO);
    }

    public void empInfoSave(ManagementEmpDTO managementEmpDTO) {

        // 필수값 체크
        if(managementEmpDTO.getEmpId().equals("") || managementEmpDTO.getDeptCd() == 0 || managementEmpDTO.getPositionCd() == 0 || managementEmpDTO.getEmpId().equals("")){

        }

        managementEmpDTO.setEmpPw(SHA256Encryptor.encryptor(managementEmpDTO.getEmpPw()));

        // dateformat 빈값에러 처리
        if(managementEmpDTO.getBirthDay().equals("")){
            managementEmpDTO.setBirthDay(null);
        }

        if(managementEmpDTO.getEnterDate().equals("")){
            managementEmpDTO.setEnterDate(null);
        }

        iCommonDao.insertData("ManagementEmp.insertOne", managementEmpDTO);
    }

    public void empInfoEdit(ManagementEmpDTO managementEmpDTO) {
        // 필수값 체크

        managementEmpDTO.setEmpPw(SHA256Encryptor.encryptor(managementEmpDTO.getEmpPw()));

        // dateformat 빈값에러 처리
        if(managementEmpDTO.getBirthDay().equals("")){
            managementEmpDTO.setBirthDay(null);
        }

        if(managementEmpDTO.getEnterDate().equals("")){
            managementEmpDTO.setEnterDate(null);
        }
        iCommonDao.updateData("ManagementEmp.updateOne", managementEmpDTO);
    }

    public int doCheckEmpId(ManagementEmpDTO managementEmpDTO){
        return iCommonDao.selectCount("ManagementEmp.selectEmpIdCount", managementEmpDTO);
    }
}
