package kr.co.hs.hrm.service;

import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.hrm.dto.HrmEmpDTO;
import kr.co.hs.hrm.dto.HrmEmpSearchDTO;
import kr.co.hs.management.dto.ManagementEmpDTO;
import kr.co.hs.management.dto.ManagementEmpSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HrmEmpService {
    final ICommonDao iCommonDao;

    public int count(HrmEmpSearchDTO hrmEmpSearchDTO) {
        return iCommonDao.selectCount("HrmEmp.selectCount", hrmEmpSearchDTO);
    }

    public List<HrmEmpDTO> getEmpList(HrmEmpSearchDTO hrmEmpSearchDTO) {
        return iCommonDao.selectList("HrmEmp.selectEmpList", hrmEmpSearchDTO);
    }

    public HrmEmpDTO getEmpView(HrmEmpSearchDTO hrmEmpSearchDTO) {
        return iCommonDao.selectOne("HrmEmp.selectEmpView", hrmEmpSearchDTO);
    }

    public void empInfoSave(HrmEmpDTO hrmEmpDTO) {

        // 필수값 체크
        if(hrmEmpDTO.getEmpId().equals("") || hrmEmpDTO.getDeptCd() == 0 || hrmEmpDTO.getPositionCd() == 0 || hrmEmpDTO.getEmpId().equals("")){

        }

        hrmEmpDTO.setEmpPw(SHA256Encryptor.encryptor(hrmEmpDTO.getEmpPw()));

        // dateformat 빈값에러 처리
        if(hrmEmpDTO.getBirthDay().equals("")){
            hrmEmpDTO.setBirthDay(null);
        }

        if(hrmEmpDTO.getEnterDate().equals("")){
            hrmEmpDTO.setEnterDate(null);
        }

        iCommonDao.insertData("ManagementEmp.insertOne", hrmEmpDTO);
    }

    public void empInfoEdit(HrmEmpDTO hrmEmpDTO) {
        // 필수값 체크

        hrmEmpDTO.setEmpPw(SHA256Encryptor.encryptor(hrmEmpDTO.getEmpPw()));

        // dateformat 빈값에러 처리
        if(hrmEmpDTO.getBirthDay().equals("")){
            hrmEmpDTO.setBirthDay(null);
        }

        if(hrmEmpDTO.getEnterDate().equals("")){
            hrmEmpDTO.setEnterDate(null);
        }
        iCommonDao.updateData("ManagementEmp.updateOne", hrmEmpDTO);
    }

    public int doCheckEmpId(HrmEmpDTO hrmEmpDTO){
        return iCommonDao.selectCount("ManagementEmp.selectEmpIdCount", hrmEmpDTO);
    }
}
