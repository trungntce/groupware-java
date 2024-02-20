package kr.co.hs.work.service;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.work.dto.WorkDTO;
import kr.co.hs.oldwork.dto.CoperationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {

    final ICommonDao iCommonDao;

    public List<WorkDTO> getlistWork(HashMap param) {
        return iCommonDao.selectList("songwork.getlistWork", param);
    }
    public int getSumRowWork(HashMap param){
        return iCommonDao.selectOne("songwork.getSumRowWork",param);
    }


    public String subSpaceStr(String str){
        String str_ = str.substring((str.length()-1), str.length());

        if (str_.equals(" ")){
            str = str.substring(0, str.length()-1);
            subSpaceStr(str);
        }
        else
            return str;
        return str;
    }

    public WorkDTO getlistWorkByID(HashMap param) {
        WorkDTO swd = iCommonDao.selectOne("songwork.getlistWorkByID", param);
        String str = swd.getContents();
        str = str.replace("\n", " ");
        str = str.replace("  ", "");

        String lastStr = subSpaceStr(str);

        swd.setContents(lastStr);

        return swd;
    }

    public List<EmpDTO> getEmpByDept(HashMap param) {
        return iCommonDao.selectList("songwork.getEmpByDept", param);
    }


    public List<CoperationDTO> getCoporation(HashMap param) {
        return iCommonDao.selectList("songwork.getCoporation", param);
    }

    public EmpDTO getEmpForWork(int empCd) {
        return iCommonDao.selectOne("songwork.getEmpForWork", empCd);
    }

    public int insertCoForWork(CoperationDTO coperationDTO){
        return iCommonDao.insertDataInt("songwork.insertCoForWork",coperationDTO);
    }

    public int insertCoForWorkNewWork(CoperationDTO coperationDTO){
        return iCommonDao.insertDataInt("songwork.insertCoForWorkNewWork",coperationDTO);
    }

    public int xoaCo (String id){
        return iCommonDao.deleteData("songwork.xoaCo",id);
    }

    public int updateWork (WorkDTO workDTO){
        return iCommonDao.updateData("songwork.updateWork", workDTO);
    }

    public int xoaW(HashMap map) {
        return iCommonDao.updateData("songwork.xoaWork",map);
    }

    public int xoaTran (String id){
        return iCommonDao.updateData("songwork.xoaTran",id);
    }
    public int addWork(WorkDTO workDTO){
        return iCommonDao.insertDataInt("songwork.addWork", workDTO);
    }
    public int updateInTran (HashMap param){
        return iCommonDao.updateData("songwork.updateInTran",param);
    }
    public int updateInCo (HashMap param){
        return iCommonDao.updateData("songwork.updateInCo",param);
    }

    public void updateWorkStatus (WorkDTO workDTO){
        iCommonDao.updateData("songwork.updateWorkStatus", workDTO);
    }
    public void deleteCop(HashMap param){
        iCommonDao.deleteData("songwork.deleteCop", param);
    }

    public void deleteCopEmpty(HashMap param){
        iCommonDao.deleteData("songwork.deleteCopEmpty", param);
    }

    public int empCheckExitCop(WorkDTO workDTO){
        return iCommonDao.selectCount("songwork.empCheckExitCop", workDTO);
    }
}
