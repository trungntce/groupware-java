package kr.co.hs.board.service;

import com.google.gson.Gson;
import kr.co.hs.board.dto.BoardControlDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.FileUploadDTO;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardGroupService {
    final ICommonDao iCommonDao;

    public int count(BoardControlDTO boardControlDTO) {
        return iCommonDao.selectCount("GroupBoard.selectCount", boardControlDTO);
    }

    public List<BoardControlDTO> getCompanyNoticeList(BoardControlDTO boardControlDTO){
        return iCommonDao.selectList("GroupBoard.selectListTest", boardControlDTO);
    }
    public List<BoardControlDTO> selectTopByNoticeYn(String langCd){
        return iCommonDao.selectList("GroupBoard.selectTopByNoticeYn", langCd);
    }

    public List<BoardControlDTO> getListBoardMain(HashMap param){
        return iCommonDao.selectList("GroupBoard.selectListMain", param);
    }

    public List<BoardControlDTO> getBoardTypeList (){
        return iCommonDao.selectList("GroupBoard.selectBoardTypeList",null);
    }

    public  List<BoardControlDTO> getLangList(){
        return iCommonDao.selectList("GroupBoard.selectLangList", null);
    }

    public void boardCompanySave(EmpDTO empDTO, BoardControlDTO boardControlDTO){
        boardControlDTO.setEmpCd(empDTO.getEmpCd());
        boardControlDTO.setPositionCd(empDTO.getPositionCd());
        boardControlDTO.setDeptCd(empDTO.getDeptCd());
        boardControlDTO.setDeptChangeHistoryId(empDTO.getDeptCd());

        iCommonDao.insertData("GroupBoard.insertOne", boardControlDTO);
    }

    public BoardControlDTO getBoardView(BoardControlDTO boardControlDTO){
        return iCommonDao.selectOne("GroupBoard.selectBoardView", boardControlDTO);
    }

    public void boardInfoEdit(BoardControlDTO boardControlDTO){
        iCommonDao.updateData("GroupBoard.updateOne", boardControlDTO);
    }
    public void insertTempFile(FileUploadDTO fileUploadDTO){
        iCommonDao.insertData("GroupBoard.insertTempFile", fileUploadDTO);
    }
    public void deleteAllTempFile(Integer empCd){
        iCommonDao.deleteData("GroupBoard.deleteAllTempFile", empCd);
    }
    public void deleteRealFile(Integer fileId){
        iCommonDao.deleteData("GroupBoard.deleteRealFile", fileId);
    }
    public void deleteOneTempFile(HashMap param){
        iCommonDao.deleteData("GroupBoard.deleteOneTempFile", param);
    }
    public void insertRealFile(Integer empCd){
        iCommonDao.insertData("GroupBoard.insertRealFile", empCd);
    }
    public void insertRealFileEdit(HashMap param){
        iCommonDao.insertData("GroupBoard.insertRealFileEdit", param);
    }
    public void updateRealFile(Integer empCd){
        iCommonDao.updateData("GroupBoard.updateRealFile", empCd);
    }
    public  List<FileUploadDTO> selectListFile(Integer boardId){
        return iCommonDao.selectList("GroupBoard.selectListFile", boardId);
    }
    public  FileUploadDTO selectInforFile(Integer fileId){
        return iCommonDao.selectOne("GroupBoard.selectInforFile", fileId);
    }






}

