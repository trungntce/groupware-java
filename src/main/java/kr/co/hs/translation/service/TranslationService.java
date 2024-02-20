package kr.co.hs.translation.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.project.dto.*;
import kr.co.hs.work.dto.WorktranslationDTO;
import kr.co.hs.translation.dto.TranslationDTO;
import kr.co.hs.translation.dto.Translation_location;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {
    final ICommonDao iCommonDao;

    public int InsertTran(TranslationDTO translationDTO) {
        return iCommonDao.insertDataInt("songtranslation.InsertTran", translationDTO);
    }

    public void insertOne(TranslationDTO translationDTO) {
        iCommonDao.insertData("songtranslation.InsertTran", translationDTO);
    }

    public int getTranId() {
        return iCommonDao.selectOne("songtranslation.getTranId", "a");
    }

    public int InsertWorkTran(WorktranslationDTO worktranslationDTO, String type) {
        int rs = 0;
        if (type.equals("work") || type.equals("mywork")) {
            //week
            rs = iCommonDao.insertDataInt("songtranslation.InsertWorkTran", worktranslationDTO);
        }
        if (type.equals("daywork") || type.equals("mydaywork")) {
            //days
            rs = iCommonDao.insertDataInt("songtranslation.InsertDayWorkTran", worktranslationDTO);
        }
        if (type.equals("dayworkcmt")) {

            rs = iCommonDao.insertDataInt("songtranslation.InsertDayWorkCmtTran", worktranslationDTO);
        }
        if (type.equals("gboardtrans") || type.equals("dboardtrans") || type.equals("fboardtrans") || type.equals("cboardtrans")) {
            rs = iCommonDao.insertDataInt("songtranslation.InsertBoardTrans", worktranslationDTO);
        }
        return rs;
    }

    public int InsertDayProjectItemTrans(DayProjectItemTranslationDTO dayProjectItemTranslationDTO) {
        return iCommonDao.insertDataInt("songtranslation.InsertDayProjectItemTrans", dayProjectItemTranslationDTO);
    }

    public int InsertDayProjectTrans(DayProjectTranslationDTO dayProjectTranslationDTO) {
        return iCommonDao.insertDataInt("songtranslation.InsertDayProjectTrans", dayProjectTranslationDTO);
    }

    public int InsertProjectTrans(ProjectTranslationDTO projectTranslationDTO) {
        return iCommonDao.insertDataInt("songtranslation.InsertProjectTrans", projectTranslationDTO);
    }

    public List<TranslationDTO> getTranKork(HashMap pram) {

        return iCommonDao.selectList("songtranslation.getTranKork", pram);
    }

    public List<TranslationDTO> getTransBoard(HashMap pram) {

        return iCommonDao.selectList("songtranslation.getTransBoard", pram);
    }

    public List<TranslationDTO> getTranDayProjectItem(DayProjectItemDTO dayProjectItemDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dpiId", dayProjectItemDTO.getDpiId());
        return iCommonDao.selectList("songtranslation.getTranDayProjectItem", pra);
    }

    public List<TranslationDTO> getTranDayProject(DayProjectDTO dayProjectDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("dpId", dayProjectDTO.getDpId());
        return iCommonDao.selectList("songtranslation.getTranDayProject", pra);
    }

    public List<TranslationDTO> getTranProject(ProjectMainDTO projectMainDTO) {
        String langCd = LocaleContextHolder.getLocale().toString();
        HashMap pra = new HashMap();
        pra.put("langCd", langCd);
        pra.put("pjId", projectMainDTO.getPjId());
        return iCommonDao.selectList("songtranslation.getTranProject", pra);
    }

    public List<Translation_location> getLocationTran(String langCd) {

        return iCommonDao.selectList("songtranslation.getLocationTran", langCd);
    }

    public int themTranLoca(Translation_location translation_location) {
        return iCommonDao.insertDataInt("songtranslation.themTranLoca", translation_location);
    }

    public int xoalocationTran(HashMap param) {
        return iCommonDao.updateData("songtranslation.xoalocationTran", param);
    }

    public List<TranslationDTO> getDayWorkTranko(HashMap pram) {
        return iCommonDao.selectList("songtranslation.getDayWorkTranKork", pram);
    }

    public List<TranslationDTO> getCommentTrans(HashMap pram) {
        return iCommonDao.selectList("songtranslation.getCommentTrans", pram);
    }

    public TranslationDTO getListTransById(HashMap param) {
        return iCommonDao.selectOne("songtranslation.getTransByID", param);
    }

    public int updateTrans(TranslationDTO translationDTO) {
        return iCommonDao.updateData("songtranslation.updateTranslation", translationDTO);
    }

    public int updateTranlo(EmpDTO empDTO) {
        return iCommonDao.updateData("songtranslation.updateTranlocation", empDTO);
    }

    public int updateStatusTrans(HashMap param) {
        return iCommonDao.updateData("songtranslation.updateStatusTrans", param);
    }

    public int checkBelongCmtTrans(HashMap param) {
        return iCommonDao.selectCount("songtranslation.checkIdBelongCmtTrans", param);
    }

    public List<TranslationDTO> getListTransByDayWorkId(HashMap param) {
        return iCommonDao.selectList("songtranslation.getListTransByDayWorkId", param);
    }

    public List<TranslationDTO> getListTransByApprovalId(HashMap param){
        return iCommonDao.selectList("songtranslation.getListTransByApprovalId", param);
    }
    public List<TranslationDTO> getListTransCommentByApprovalId(HashMap param){
        return iCommonDao.selectList("songtranslation.getListTransCommentByApprovalId", param);
    }

}
