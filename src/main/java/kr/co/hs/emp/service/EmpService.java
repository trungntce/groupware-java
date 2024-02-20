package kr.co.hs.emp.service;

import kr.co.hs.common.crypto.SHA256Encryptor;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.menu.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpService {

    final ICommonDao iCommonDao;

    public EmpDTO getEmp(String empId) {
        return iCommonDao.selectOne("Emp.selectEmp", empId);
    }
    public EmpDTO getEmpByEmpCd(int empCd) {
        return iCommonDao.selectOne("Emp.selectEmpByEmpCd", empCd);
    }

    public List<EmpDTO> getEmpList(HashMap param) { return iCommonDao.selectList("Emp.selectEmpList", param); }
    public int sumRowsEmp(HashMap param){
        return iCommonDao.selectOne("Emp.sumRowsEmp",param);
    }

    public List<EmpDTO> getEmpUserList(String empId) { return iCommonDao.selectList("Emp.selectEmpUserList", empId); }

    public List<MenuDTO> getMenu() {
        return iCommonDao.selectList("Emp.getMenuList", null);
    }

    public List<EmpDTO> getEmpAuthList(EmpDTO empDTO) {
        return iCommonDao.selectList("Emp.selectEmpAuthList", empDTO);
    }

    public int getDeptChangeHistoryId(EmpDTO empDTO) { return iCommonDao.selectOne("Emp.selectDeptChangeHistoryId", empDTO); }

    public EmpDTO GetProfile(HashMap pra) {
        return iCommonDao.selectOne("Emp.GetProfile", pra);
    }

    public List<EmpDTO> getAllProfile(HashMap param){
        return iCommonDao.selectList("Emp.GetAllProfile", param);
    }

    public EmpDTO GetSubProfile(HashMap pra) {
        return iCommonDao.selectOne("Emp.GetSubProfile", pra);
    }

    public List<MenuDTO>  GetEmpHistory(HashMap pra) { return iCommonDao.selectList("Emp.GetEmpHistory", pra); }

    public int deleteEmp( int empCd) {
        return iCommonDao.updateData("Emp.deleteEmp",empCd);
    }

    public int InsertEmp( EmpDTO empDTO) {
        return iCommonDao.insertDataInt("Emp.InsertEmp",empDTO);
    }

    public int checkExist(String empId){
        return iCommonDao.selectOne("Emp.checkExist",empId);
    }

    public int checkEmpExist(EmpDTO empDTO){
        return iCommonDao.selectOne("Emp.checkEmpExist",empDTO);
    }

    public List<EmpDTO> selectList(EmpDTO empDTO){
        return iCommonDao.selectList("Emp.selectList", empDTO);
    }

    public EmpDTO getEdit(String empCd) { return iCommonDao.selectOne("Emp.getEdit", empCd); }

    public int getCountParent(String empCd)
    {
        return iCommonDao.selectOne("Emp.getCountParent", empCd);
    }

    public List<EmpDTO> getPassWord(EmpDTO empDTO) { return iCommonDao.selectList("Emp.selectPW", empDTO); }

    public void getEmpInformation(EmpDTO empDTO) {
        iCommonDao.selectList("Emp.selectInformation", empDTO);
    }

    public int updateEmp( EmpDTO empDTO) {
        return iCommonDao.updateData("Emp.updateEmp",empDTO);
    }

    public EmpDTO checkExitParentEmp(EmpDTO empDTO) {
        return iCommonDao.selectOne("Emp.checkExitParentEmp", empDTO);
    }

    public EmpDTO getSessionUserLogin(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        EmpDTO dataEmp = (EmpDTO) session.getAttribute("_user");
        return dataEmp;
    }

    public int deleteEmp(HashMap map) {
        return iCommonDao.updateData("Emp.deleteEmpByRequest",map);
    }

    public int checkEmpPassword(String oldEmpPw){
        return iCommonDao.selectOne("Emp.checkEmpPassword",oldEmpPw);
    }

    public String updateMyinfo(EmpDTO empDTO, String OldEmpPw, String newEmpPw) {
        String returnCode = null;
        if(checkEmpPassword(SHA256Encryptor.encryptor(OldEmpPw)) > 0) {
            try {
                empDTO.setEmpPw(SHA256Encryptor.encryptor(newEmpPw));
                iCommonDao.updateData("Emp.updateEmpPassword", empDTO);
                returnCode = "S";
            } catch (Exception e) {
                e.printStackTrace();
                returnCode = "F";
            }
        } else {
            returnCode = "F";
        }
        return returnCode;
    }

    public String updateEmpPassword( EmpDTO empDTO) {
        String returnCode = null;

        log.info("empCount ===> {}", checkEmpExist(empDTO));
        if(checkEmpExist(empDTO) > 0) {
            try {
                returnCode = getRamdomPassword(10);
                empDTO.setEmpPw(SHA256Encryptor.encryptor(returnCode));
                iCommonDao.updateData("Emp.updateEmpPassword", empDTO);
            } catch (Exception e) {
                e.printStackTrace();
                returnCode = "F";
            }
        } else {
            returnCode = "F";
        }
        return returnCode;
    }

    public String getRamdomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
    //getemplist by custom id

    public List<EmpDTO> getEmpListByCustomID(HashMap param){
        return iCommonDao.selectList("Emp.getemplistbycustomid", param);
    }

    public List<String> selectEmpWithDept (){
        EmpDTO emp = new EmpDTO();
        emp.setLangCd(LocaleContextHolder.getLocale().toString());

        List<String> lst = new ArrayList<String>();
        List<EmpDTO> empDTOList = iCommonDao.selectList("Emp.selectEmpWithDept", emp);

        for (int i = 0; i < empDTOList.size(); i++){
            lst.add(empDTOList.get(i).getEmpName());
        }
        return lst;
    }

    public List<EmpDTO> selectLstEmpWithDept(){
        EmpDTO emp = new EmpDTO();
        emp.setLangCd(LocaleContextHolder.getLocale().toString());

        List<String> lst = new ArrayList<String>();
        return iCommonDao.selectList("Emp.selectEmpWithDept", emp);
    }

    public List<String> selectEmpWithDeptAccounting (){
        EmpDTO emp = new EmpDTO();
        emp.setLangCd(LocaleContextHolder.getLocale().toString());

        List<String> lst = new ArrayList<String>();
        List<EmpDTO> empDTOList = iCommonDao.selectList("Emp.selectEmpWithDeptAccounting", emp);

        for (int i = 0; i < empDTOList.size(); i++){
            lst.add(empDTOList.get(i).getEmpName());
        }
        return lst;
    }

    public boolean checkExitEmpDept(String leaderName){
        List<String> lst = selectEmpWithDept();
        for (int i = 0; i < lst.size(); i++){
            if (lst.get(i).equals(leaderName)){
                return true;
            }
        }
        return false;
    }

    public int findLeaderName(String leaderName){
        EmpDTO empDTO = new EmpDTO();
        empDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        empDTO.setEmpName(leaderName);
        return iCommonDao.selectCount("Emp.findLeaderName", empDTO);
    }

    public EmpDTO selectEmpDeptName(int id){
        EmpDTO empDTO = new EmpDTO();
        empDTO.setEmpCd(id);
        empDTO.setLangCd(LocaleContextHolder.getLocale().toString());
        return iCommonDao.selectOne("Emp.selectEmpDeptName", empDTO);
    }
}
