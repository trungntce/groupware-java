package kr.co.hs.deptcontrol.service;

import kr.co.hs.deptcontrol.dto.DeptControlDTO;
import kr.co.hs.deptcontrol.dto.SearchDeptControlDTO;
import kr.co.hs.common.dao.ICommonDao;
import lombok.RequiredArgsConstructor;
import org.eclipse.core.internal.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptControlService {
    @Autowired
    private ICommonDao iCommonDao;

    public int count(SearchDeptControlDTO searchDeptControlDTO) {
        return iCommonDao.selectCount("DatDept.selectDeptCount", searchDeptControlDTO);
    }

    public List<DeptControlDTO> getDeptList(SearchDeptControlDTO searchDeptControlDTO) {
        return iCommonDao.selectList("DatDept.selectDeptList", searchDeptControlDTO);
    }

    public DeptControlDTO getDeptView(DeptControlDTO deptControlDTO) {
        return iCommonDao.selectOne("DatDept.selectDeptView", deptControlDTO);
    }

    public DeptControlDTO getDeptViewEdit(DeptControlDTO deptControlDTO) {
        return iCommonDao.selectOne("DatDept.selectDeptViewEdit", deptControlDTO);
    }

    public List<DeptControlDTO> getDeptNameList() {
        return iCommonDao.selectList("DatDept.selectDeptNameList", null);
    }

    public void deptSave(DeptControlDTO deptControlDTO) {
        iCommonDao.insertData("DatDept.insertDept", deptControlDTO);
    }

    public void deptUpdate(DeptControlDTO deptControlDTO) {
        iCommonDao.updateData("DatDept.updateDept", deptControlDTO);
    }
    public List<DeptControlDTO> getDeptByDeptParentCd(SearchDeptControlDTO searchDeptControlDTO) {
        return iCommonDao.selectList("DatDept.getDeptByDeptParentCd", searchDeptControlDTO);
    }

    public List<Integer> getListParent(int deptCd) {
        if (deptCd == 1 || deptCd == 0) {
            return null;
        }
        List<Integer> lstTreeReturn = new ArrayList<>();
        DeptControlDTO deptControlDTO = new DeptControlDTO();
        deptControlDTO.setDeptCd(deptCd);

        DeptControlDTO currentDeptCd = this.getDeptView(deptControlDTO);

        List<String> lstParent = new ArrayList<>();
        lstParent.add(String.valueOf(currentDeptCd.getDeptCd()) + "-" + currentDeptCd.getDeptLevel());

        List<Integer> parentsCd = new ArrayList<>();
        deptControlDTO.setDeptCd(1);
        parentsCd.add(deptControlDTO.getDeptCd());

        SearchDeptControlDTO searchDeptControlDTO = new SearchDeptControlDTO();
        searchDeptControlDTO.setListParentCd(parentsCd);
        List<DeptControlDTO> parentMenu = this.getDeptByDeptParentCd(searchDeptControlDTO);
        StringBuilder strTree = new StringBuilder();

        for (int i = 0; i < parentMenu.size(); i++) {

            parentsCd = new ArrayList<>();
            parentsCd.add(parentMenu.get(i).getDeptCd());
            String tempParent = parentMenu.get(i).getDeptCd() + "-" + parentMenu.get(i).getDeptLevel();
            strTree.append(tempParent + loopTree(parentsCd));

            lstParent.add(strTree.toString());
            strTree = new StringBuilder();
            rsTree = new StringBuilder();
        }

        for (int j = 0; j < lstParent.size(); j++) {

            if (lstParent.get(j).contains(String.valueOf(currentDeptCd.getDeptCd()))) {

                String[] arrCompare = lstParent.get(j).split(",");
                for (int jj = 0; jj < arrCompare.length; jj++) {

                    String[] tempCompare = arrCompare[jj].split("-");
                    String tempDeptCd = tempCompare[0];
                    String tempDeptLevel = tempCompare[1];
                    if (currentDeptCd.getDeptCd() == Integer.parseInt(tempDeptCd)
                            || Integer.parseInt(tempDeptLevel) > currentDeptCd.getDeptLevel()) {

                        lstTreeReturn.add(Integer.parseInt(tempDeptCd));
                    }
                }
            }
        }
        return lstTreeReturn;
    }

    private StringBuilder rsTree = new StringBuilder();
    private String loopTree(List<Integer> parentsCd) {

        SearchDeptControlDTO searchDeptControlDTO = new SearchDeptControlDTO();
        searchDeptControlDTO.setListParentCd(parentsCd);
        List<DeptControlDTO> list = this.getDeptByDeptParentCd(searchDeptControlDTO);
        for (int j = 0; j < list.size(); j++) {

            rsTree.append("," + list.get(j).getDeptCd() + "-" + list.get(j).getDeptLevel());
            parentsCd = new ArrayList<>();
            parentsCd.add(list.get(j).getDeptCd());
            loopTree(parentsCd);
        }
        return rsTree.toString();
    }


}
