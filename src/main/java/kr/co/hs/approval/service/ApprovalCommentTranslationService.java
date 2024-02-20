package kr.co.hs.approval.service;


import kr.co.hs.approval.dto.ApprovalTranslationDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.translation.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalCommentTranslationService {
    final ICommonDao iCommonDao;
    final private TranslationService translationService;

    public int insertOne(ApprovalTranslationDTO approvalTranslationDTO) {
        return iCommonDao.insertDataInt("ApprovalCommentTranslation.insertOne", approvalTranslationDTO);
    }
}
