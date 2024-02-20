package kr.co.hs.daywork.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.security.UserDetail;
import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DayWorkService {
    @Autowired
    private ICommonDao iCommonDao;

    public List<DayWorkDTO> getListDayWork(HashMap param) {
//        log.error("{}", new Gson().toJson(param));
        List<DayWorkDTO> lst = iCommonDao.selectList("SongDayWork.getListDayWork", param);
        return lst;
    }

    public int getSumRowDaywork(HashMap param){
        return iCommonDao.selectOne("SongDayWork.getSumRowDaywork",param);
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

    public DayWorkDTO getListDayWorkById(HashMap param ){
        DayWorkDTO swd = iCommonDao.selectOne("SongDayWork.getlistWorkByID", param);
        String str = swd.getContents();
        str = str.replace("\n", " ");
        str = str.replace("  ", "");

        String lastStr = subSpaceStr(str);

        swd.setContents(lastStr);
        return swd;
    }
    public int getTypeMainWork(HashMap param){
        return iCommonDao.selectCount("SongDayWork.getTypeMainWork", param);
    }

    public int getTypeSubWork(HashMap param){
        return iCommonDao.selectCount("SongDayWork.getTypeSubWork", param);
    }

    public DayWorkDTO getListMoreDayWorkById(HashMap param ){
        DayWorkDTO swd = iCommonDao.selectOne("SongDayWork.getlistMoreWorkByID", param);
        String str = swd.getContents();
        str = str.replace("\n", " ");
        str = str.replace("  ", "");

        String lastStr = subSpaceStr(str);

        swd.setContents(lastStr);
        return swd;
    }

    public int deleteWorkChild(int num){
        return iCommonDao.updateData("SongDayWork.deleteDayWorkChild", num);
    }
    public int updateWorkChildstatus(DayWorkDTO dayWorkDTO){
        return iCommonDao.updateData("SongDayWork.updateStatusWorkChild", dayWorkDTO);
    }

    public List<DayWorkDTO> getListDayWorkChildById(HashMap param){
        return iCommonDao.selectList("SongDayWork.getlistWorkChildByID", param);
    }

    public DayWorkDTO getChildDayWorkId(DayWorkDTO dayWorkDTO){
        return iCommonDao.selectOne("SongDayWork.getChildDayWorkId", dayWorkDTO);
    }

    public List<DayWorkDTO> getListMoreDayWorkChildById(HashMap param){
        return iCommonDao.selectList("SongDayWork.getlistMoreWorkChildByID", param);
    }

    public List<DayWorkDTO> getlistMoreWorkChildByID(HashMap param){
        return iCommonDao.selectList("SongDayWork.getlistMoreWorkChildByID", param);
    }

    public int getCountNumber(HashMap param){
        return iCommonDao.selectCount("SongDayWork.getCountMoreDayWork", param);
    }

    public String getListDayWorkChildByIdChild(DayWorkDTO dayWorkDTO){
        return iCommonDao.selectOne("SongDayWork.getlistWorkChildByIDChild", dayWorkDTO);
    }

    public String getListDayWorkChildByIdTime(DayWorkDTO dayWorkDTO){
        return iCommonDao.selectOne("SongDayWork.getlistWorkChildByIDTime", dayWorkDTO);
    }

    public int updateWork (DayWorkDTO dayWorkDTO){
        return iCommonDao.updateData("SongDayWork.updateDayWork", dayWorkDTO);
    }

    public int updateWorkChild (DayWorkDTO dayWorkDTO){
        return iCommonDao.updateData("SongDayWork.updateDayWorkChild", dayWorkDTO);
    }

    public int addWork(DayWorkDTO dayWorkDTO){
        return iCommonDao.insertDataInt("SongDayWork.addWork", dayWorkDTO);
    }

    public int addWorkChild(DayWorkDTO dayWorkDTO){
        return iCommonDao.insertDataInt("SongDayWork.addWorkChild", dayWorkDTO);
    }

    public int addChildDayWorkUpdate(DayWorkDTO dayWorkDTO){
        return iCommonDao.insertDataInt("SongDayWork.addChildDayWorkUpdate", dayWorkDTO);
    }

    public int addMoreWorkChild(DayWorkDTO dayWorkDTO){
        return iCommonDao.insertDataInt("SongDayWork.addMoreWorkChild", dayWorkDTO);
    }

    public int addMoreWorkChildUpdate(DayWorkDTO dayWorkDTO){
        return iCommonDao.insertDataInt("SongDayWork.addMoreWorkChildUpdate", dayWorkDTO);
    }

    public void updateDayWorkStatus (DayWorkDTO dayWorkDTO){
        iCommonDao.updateData("SongDayWork.updateDayWorkStatus", dayWorkDTO);
    }

    public List<DayWorkDTO> getListDayMyWork(HashMap param) {
        List<DayWorkDTO> lst = null;
        if (param.get("type") == "excel"){
            System.out.println("DXD: " + param);
            lst = iCommonDao.selectList("SongDayWork.getListDayMyWorkExcelExport", param);
        }else {
             lst = iCommonDao.selectList("SongDayWork.getListDayMyWork", param);
        }
        return lst;
    }
    public int getSumRowMyDaywork(HashMap param){
        return iCommonDao.selectOne("SongDayWork.getSumRowMyDaywork",param);
    }

    public List<DayWorkDTO> getListDayMyWorkMain(HashMap param) {
        List<DayWorkDTO> lst = iCommonDao.selectList("SongDayWork.getListDayMyWorkMain", param);
        return lst;
    }

    public int xoaW(HashMap map) {
        return iCommonDao.updateData("SongDayWork.xoaDayWork",map);
    }

    public int updateTransStatus(HashMap hashMap){
        return iCommonDao.updateData("SongDayWork.updateTransStatus", hashMap);
    }
}
