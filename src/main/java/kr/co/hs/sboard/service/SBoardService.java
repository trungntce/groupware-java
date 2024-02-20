package kr.co.hs.sboard.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.deptconfig.dto.DeptDTO;
import kr.co.hs.sboard.dto.SBoardCommentDTO;
import kr.co.hs.sboard.dto.SBoardDmtDTO;
import kr.co.hs.sboard.dto.SBoardMainDTO;
import kr.co.hs.sboard.dto.SBoardTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SBoardService {
    final ICommonDao iCommonDao;

    public List<DeptDTO> getTreeOfBoard(int empCd) {

        return iCommonDao.selectList("sboard.getTreeOfBoard", empCd);
    }

    public List<SBoardMainDTO> getmainboard(HashMap param) {

        return iCommonDao.selectList("sboard.getmainboard", param);
    }

    public List<SBoardDmtDTO> getmainDMT(HashMap param) {
        return iCommonDao.selectList("sboard.getmainDMT", param);
    }
    public List<SBoardTypeDTO> getBoardTypeTopic() {
        return iCommonDao.selectAll("sboard.getBoardTypeTopic");
    }

    public int addTbDmt(SBoardDmtDTO sBoardDmtDTO){
        return iCommonDao.insertDataInt("sboard.addTbDmt",sBoardDmtDTO);

    }

    public int getstyle1(int id){
        return iCommonDao.selectOne("sboard.getstyle",id);

    }

    public String getnamedmt(int id){
        return iCommonDao.selectOne("sboard.getnamedmt",id);

    }
    public int addmain(SBoardMainDTO sBoardMainDTO){
        return iCommonDao.insertDataInt("sboard.addmain",sBoardMainDTO);

    }

    public SBoardMainDTO getDetail(int id) {

        return iCommonDao.selectOne("sboard.getDetail", id);
    }
    public int updateSBoard(SBoardMainDTO sBoardMainDTO){
        return iCommonDao.updateData("sboard.updateSBoard",sBoardMainDTO);

    }

    public int deleteSBoard(int id){
        return iCommonDao.updateData("sboard.deleteSBoard",id);

    }

    public List<SBoardCommentDTO> getComment(HashMap param) {

        return iCommonDao.selectList("sboard.getComment", param);
    }

    public int addComment(SBoardCommentDTO sBoardCommentDTO){
        return iCommonDao.insertDataInt("sboard.addComment",sBoardCommentDTO);

    }

    public int deleteComment(int id){
        return iCommonDao.deleteData("sboard.deleteComment",id);

    }


}
