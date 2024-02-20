package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.ApprovalFormDTO;
import kr.co.hs.approval.dto.ApprovalFormSearchDTO;
import kr.co.hs.common.dao.ICommonDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalFormService {
    final ICommonDao iCommonDao;

    public List<ApprovalFormDTO> getListApprovalForm(ApprovalFormSearchDTO approvalFormSearchDTO) {
        return iCommonDao.selectList("ApprovalForm.selectApprovalForm", approvalFormSearchDTO);
    }

    public List<ApprovalFormDTO> selectAll(){
        return iCommonDao.selectAll("ApprovalForm.selectAll");
    }

    public Integer count(ApprovalFormSearchDTO approvalFormSearchDTO){
        return iCommonDao.selectCount("ApprovalForm.count", approvalFormSearchDTO);
    }

    public List<ApprovalFormDTO> selectList(ApprovalFormSearchDTO approvalFormSearchDTO) {
        return iCommonDao.selectList("ApprovalForm.selectList", approvalFormSearchDTO);
    }

    public ApprovalFormDTO selectOne(ApprovalFormSearchDTO approvalFormSearchDTO){
        return iCommonDao.selectOne("ApprovalForm.selectOne", approvalFormSearchDTO);
    }

    public void insertApprovalForm(ApprovalFormDTO approvalFormDTO) {

        iCommonDao.insertData("ApprovalForm.insertOne", approvalFormDTO);
    }

    public void updateApprovalForm(ApprovalFormDTO approvalFormDTO){

        iCommonDao.updateData("ApprovalForm.updateOne", approvalFormDTO);
    }
}
