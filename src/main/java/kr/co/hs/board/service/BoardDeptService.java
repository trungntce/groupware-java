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
public class BoardDeptService {
    final ICommonDao iCommonDao;

    public int count(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("DeptBoard.selectCount", boardControlDTO);
    }

    public int countAdmin(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("DeptBoard.selectCountAdmin", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeList(BoardControlDTO boardControlDTO){
        log.error("{}", new Gson().toJson(boardControlDTO));
        return iCommonDao.selectList("DeptBoard.selectListTest", boardControlDTO);
    }
    public List<BoardControlDTO> selectTopByNoticeYn(String langCd){
        return iCommonDao.selectList("DeptBoard.selectTopByNoticeYn", langCd);
    }

    public int countTranslation(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("DeptBoard.selectCountTranslation", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeListAdmin(BoardControlDTO boardControlDTO){
        log.error("{}", new Gson().toJson(boardControlDTO));
        return iCommonDao.selectList("DeptBoard.selectListTestAdmin", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeListTranslation(BoardControlDTO boardControlDTO){
        log.error("{}", new Gson().toJson(boardControlDTO));
        return iCommonDao.selectList("DeptBoard.selectListTestTranslation", boardControlDTO);
    }

    public List<BoardControlDTO> getListBoardMain(HashMap param){
        return iCommonDao.selectList("DeptBoard.selectListMain", param);
    }

    public List<BoardControlDTO> getBoardTypeList (){
        return iCommonDao.selectList("DeptBoard.selectBoardTypeList",null);
    }

    public  List<BoardControlDTO> getLangList(){
        return iCommonDao.selectList("DeptBoard.selectLangList", null);
    }

    public void boardCompanySave(EmpDTO empDTO, BoardControlDTO boardControlDTO){
        boardControlDTO.setEmpCd(empDTO.getEmpCd());
        boardControlDTO.setPositionCd(empDTO.getPositionCd());
        if (empDTO.getPositionCd() > 7){
            boardControlDTO.setDeptCd(empDTO.getDeptCd());
        }
        boardControlDTO.setDeptChangeHistoryId(empDTO.getDeptCd());

        iCommonDao.insertData("DeptBoard.insertOne", boardControlDTO);
    }

    public BoardControlDTO getBoardView(BoardControlDTO boardControlDTO){
        return iCommonDao.selectOne("DeptBoard.selectBoardView", boardControlDTO);
    }

    public void boardInfoEdit(BoardControlDTO boardControlDTO){
        iCommonDao.updateData("DeptBoard.updateOne", boardControlDTO);
    }
}
