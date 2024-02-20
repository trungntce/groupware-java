package kr.co.hs.board.service;


import com.google.gson.Gson;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardService {
    final ICommonDao iCommonDao;

    public int count(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("DatBoard.selectCount", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeList(BoardControlDTO boardControlDTO){
        log.error("{}", new Gson().toJson(boardControlDTO));
        return iCommonDao.selectList("DatBoard.selectListTest", boardControlDTO);
    }
    public List<BoardControlDTO> selectTopByNoticeYn(String langCd){
        return iCommonDao.selectList("DatBoard.selectTopByNoticeYn", langCd);
    }

    public List<BoardControlDTO> getListBoardMain(HashMap param){

        return iCommonDao.selectList("DatBoard.selectListMain", param);
    }

    public List<BoardControlDTO> getBoardTypeList (){
        return iCommonDao.selectList("DatBoard.selectBoardTypeList",null);
    }

    public  List<BoardControlDTO> getLangList(){
        return iCommonDao.selectList("DatBoard.selectLangList", null);
    }

    public void boardCompanySave(EmpDTO empDTO, BoardControlDTO boardControlDTO){
        boardControlDTO.setEmpCd(empDTO.getEmpCd());
        boardControlDTO.setPositionCd(empDTO.getPositionCd());
        boardControlDTO.setDeptCd(empDTO.getDeptCd());
        boardControlDTO.setDeptChangeHistoryId(empDTO.getDeptCd());

        iCommonDao.insertData("DatBoard.insertOne", boardControlDTO);
    }

    public BoardControlDTO getBoardView(BoardControlDTO boardControlDTO){
        return iCommonDao.selectOne("DatBoard.selectBoardView", boardControlDTO);
    }

    public void boardInfoEdit(BoardControlDTO boardControlDTO){
        iCommonDao.updateData("DatBoard.updateOne", boardControlDTO);
    }
}
