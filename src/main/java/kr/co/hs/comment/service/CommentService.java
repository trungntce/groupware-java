package kr.co.hs.comment.service;

import kr.co.hs.comment.dto.CommentDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private ICommonDao iCommonDao;
    @Autowired
    private TranslationService translationService;

    public List<CommentDTO> getListCMT(HashMap param, String id,String langCd) {
        List<CommentDTO> cmtList = iCommonDao.selectList("Comment.selectCommentList", param);
        String nameEdit = null;
        if (langCd.equals("ko")){
            nameEdit = "수정";
        }
        if (langCd.equals("en")){
            nameEdit = "edit";
        }
        if (langCd.equals("vt")){
            nameEdit = "Sửa";
        }
        for (int i = 0; i < cmtList.size(); i++){
            HashMap par = new HashMap();
            par.put("dayWorkCommentId", cmtList.get(i).getDayWorkCommentId());
            String getStr = "";
            List<TranslationDTO> cmtTransList = translationService.getCommentTrans(par);

            for ( int j = 0; j < cmtTransList.size(); j++){
                getStr += "<div class='content-comment'> <div class='cmt-content-left'><p>["+cmtTransList.get(j).getEmpName()+"]"+ cmtTransList.get(j).getContents()+"</p></div>";
                getStr += "<div class='cmt-button-right'><button type=\"button\" value=\"\"\n" +
                        "style=\"width: 80px; text-align: center;\"\n" +
                        "class=\"btn btn-danger\"\n" +
                        "onclick=\"editTrans("+cmtTransList.get(j).getTranslationId()+"" +
                        ","+id+")\">\n" + nameEdit +
                        "</button> </div></div>";
            }
            cmtList.get(i).setCmtTranslation(getStr);
        }
        return cmtList;
    }
    
    public CommentDTO getCmtById(HashMap hashMap){
        return iCommonDao.selectOne("Comment.getCommentById", hashMap);
    }

    public CommentDTO getListDayWorkCmtById(HashMap param ){
        return iCommonDao.selectOne("Comment.getlistDayWorkCmtByID", param);
    }

    public int insertComment( CommentDTO commentDTO) {
        return iCommonDao.insertDataInt("Comment.insertComment",commentDTO);
    }

    public int updateComment(CommentDTO commentDTO){
        return iCommonDao.updateData("Comment.updateComment",commentDTO);
    }
}
