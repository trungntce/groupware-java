package kr.co.hs.board.service;

import com.google.gson.Gson;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardCompanyService {

    final ICommonDao iCommonDao;

    public int count(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("CompanyBoard.selectCount", boardControlDTO);
    }

    public int countDirecter(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("CompanyBoard.selectCountDirecter", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeList(BoardControlDTO boardControlDTO){
        return iCommonDao.selectList("CompanyBoard.selectListTest", boardControlDTO);
    }

    public List<BoardControlDTO> selectTopByNoticeYn(String langCd){
        return iCommonDao.selectList("CompanyBoard.selectTopByNoticeYn", langCd);
    }

    public List<BoardControlDTO> getCompanyNoticeListDirecter(BoardControlDTO boardControlDTO){
        return iCommonDao.selectList("CompanyBoard.selectListTestDirecter", boardControlDTO);
    }

    public List<BoardControlDTO> getListBoardMain(HashMap param){
        return iCommonDao.selectList("CompanyBoard.selectListMain", param);
    }

    public List<BoardControlDTO> getBoardTypeList (){
        return iCommonDao.selectList("CompanyBoard.selectBoardTypeList",null);
    }

    public  List<BoardControlDTO> selectCompanyList(HashMap param){
        return iCommonDao.selectList("CompanyBoard.selectCompanyList", param);
    }

    public  List<BoardControlDTO> getLangList(){
        return iCommonDao.selectList("CompanyBoard.selectLangList", null);
    }

    public  List<BoardControlDTO> selectDepartmentList(HashMap param){
        return iCommonDao.selectList("CompanyBoard.selectDepartmentList", param);
    }

    public void boardCompanySave(EmpDTO empDTO, BoardControlDTO boardControlDTO){
        boardControlDTO.setEmpCd(empDTO.getEmpCd());
        boardControlDTO.setPositionCd(empDTO.getPositionCd());
        if (empDTO.getPositionCd() > 7){
            boardControlDTO.setDeptCd(empDTO.getDeptCd());
        }

        boardControlDTO.setDeptChangeHistoryId(empDTO.getDeptCd());

        iCommonDao.insertData("CompanyBoard.insertOne", boardControlDTO);
    }

    public BoardControlDTO getBoardView(BoardControlDTO boardControlDTO){
        return iCommonDao.selectOne("CompanyBoard.selectBoardView", boardControlDTO);
    }

    public void boardInfoEdit(BoardControlDTO boardControlDTO){
        iCommonDao.updateData("CompanyBoard.updateOne", boardControlDTO);
    }

    public int deleteBoard(HashMap map) {
        return iCommonDao.updateData("CompanyBoard.deleteBoard",map);
    }
}

