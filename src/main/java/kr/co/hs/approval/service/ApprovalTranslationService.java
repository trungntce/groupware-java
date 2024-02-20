package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.ApprovalTranslationDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalTranslationService {
    final ICommonDao iCommonDao;
    final private TranslationService translationService;
    final private ApprovalCommentTranslationService approvalCommentTranslationService;

    public int insertOne(ApprovalTranslationDTO approvalTranslationDTO) {
        return iCommonDao.insertDataInt("ApprovalTranslation.insertOne", approvalTranslationDTO);
    }

    public boolean addTranslationApproval(EmpDTO empDTO, ApprovalTranslationDTO approvalTranslationDTO) {

        // Insert data to translation table
        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setLangCd("ko");
        translationDTO.setEmpCd(empDTO.getEmpCd());
        translationDTO.setDeptCd(empDTO.getDeptCd());
        translationDTO.setPositionCd(empDTO.getPositionCd());
        translationDTO.setTitle(approvalTranslationDTO.getTitle());
        translationDTO.setContents(approvalTranslationDTO.getContents());
        translationDTO.setTranslationScore(0);
        translationDTO.setSelectYn("N");
        translationDTO.setUseYn("Y");
        translationService.insertOne(translationDTO);

        // Insert data to Approval_translation table
        approvalTranslationDTO.setTranslationId(translationDTO.getTranslationId());
        this.insertOne(approvalTranslationDTO);
        return true;
    }

    public boolean addCommentTranslationApproval(EmpDTO empDTO, ApprovalTranslationDTO approvalTranslationDTO) {
        String[] langCd = null;
        String[] signLineId = null;
        // Check contents input
        if (approvalTranslationDTO.getContents() == null) {
            return false;
        }
        String[] contentsInput = approvalTranslationDTO.getContents().split(",");
        langCd = approvalTranslationDTO.getLangCd().split(",");
        signLineId = approvalTranslationDTO.getArrSignLineId().split(",");
        for (int i = 0; i < contentsInput.length; i++) {
            if (null != contentsInput[i] && !"".equals(contentsInput[i])){
                // Insert data to translation table
                TranslationDTO translationDTO = new TranslationDTO();
                translationDTO.setLangCd(langCd[i]);
                translationDTO.setEmpCd(empDTO.getEmpCd());
                translationDTO.setDeptCd(empDTO.getDeptCd());
                translationDTO.setPositionCd(empDTO.getPositionCd());
                translationDTO.setTitle(approvalTranslationDTO.getTitle());
                translationDTO.setContents(contentsInput[i]);
                translationDTO.setTranslationScore(0);
                translationDTO.setSelectYn("N");
                translationDTO.setUseYn("Y");
                translationService.insertOne(translationDTO);

                // Insert data to Approval_translation table
                approvalTranslationDTO.setTranslationId(translationDTO.getTranslationId());
                approvalTranslationDTO.setSignLineId(Integer.parseInt(signLineId[i]));
                approvalCommentTranslationService.insertOne(approvalTranslationDTO);
            }

        }
        return true;
    }

    public TranslationDTO selectDataTranslateLastedByIdApproval(ApprovalTranslationDTO approvalTranslationDTO){
        return iCommonDao.selectOne("ApprovalTranslation.selectDataTranslateLastedByIdApproval", approvalTranslationDTO);
    }

    public List<TranslationDTO> selectDataTranslateByIdApproval(ApprovalTranslationDTO approvalTranslationDTO){
        return iCommonDao.selectList("ApprovalTranslation.selectDataTranslateByIdApproval", approvalTranslationDTO);
    }
}
