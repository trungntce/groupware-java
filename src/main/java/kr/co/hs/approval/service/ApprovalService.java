package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.*;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.translation.dto.TranslationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {

    @Autowired
    private ICommonDao iCommonDao;

    @Autowired
    private EmpService empService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalTranslationService approvalTranslationService;

    public List<ApprovalDTO> selectList(ApprovalSearchDTO searchDTO) {
        return iCommonDao.selectList("Approval.selectList", searchDTO);
    }

    public ApprovalDTO selectOne(ApprovalSearchDTO searchDTO) {
        ApprovalDTO approvalDTO = iCommonDao.selectOne("Approval.selectOne", searchDTO);
        TranslationDTO translationDTO = approvalTranslationService.selectDataTranslateLastedByIdApproval(new ApprovalTranslationDTO(approvalDTO.getApprovalId()));
        if(translationDTO != null){
            approvalDTO.setContents(translationDTO.getContents());
            approvalDTO.setTranslationId(translationDTO.getTranslationId());
            approvalDTO.setTranslatorEmpCd(translationDTO.getEmpCd());
        }
        return approvalDTO;
    }

    public void write(EmpDTO empDTO, ApprovalDTO approvalDTO) {
        approvalDTO.setEmpCd(empDTO.getEmpCd());
        approvalDTO.setDeptChangeHistoryId(empDTO.getDeptChangeHistoryId());
        approvalDTO.setDeptCd(empDTO.getDeptCd());
        approvalDTO.setPositionCd(empDTO.getPositionCd());
        approvalDTO.setCreateId(empDTO.getEmpId());
        insert(approvalDTO);

        int lineFirst = 0;
        for (int i = 0; i < approvalDTO.getEmpSign().size(); i++) {

            // Get Emp by empCd
            EmpDTO emp = empService.getEmpByEmpCd(approvalDTO.getEmpSign().get(i));

            ApprovalSignLineDTO signLineDTO = new ApprovalSignLineDTO();
            signLineDTO.setApprovalId(approvalDTO.getApprovalId());
            signLineDTO.setApprovalType(1);
            signLineDTO.setApprovalStatus(1);
            signLineDTO.setEmpCd(approvalDTO.getEmpSign().get(i));
            signLineDTO.setEmpId(emp.getEmpId());

            // Approval role
            int empSignRole = approvalDTO.getEmpSignRole().get(i);
            signLineDTO.setApprovalRole(empSignRole);
            switch (empSignRole) {
                case 1: // Approver
                case 3: // Supporter
                    signLineDTO.setStep(lineFirst);
                    if (lineFirst == 0) {
                        signLineDTO.setApprovalStatus(1); // ApprovalStatus = 1 Pending
                    } else {
                        // Approver: ApproverRole = 2 and ApprovalStatus = 3 or ApprovalStatus =  1 when first step
                        signLineDTO.setApprovalStatus(2); // ApprovalStatus = 2 waiting
                    }
                    lineFirst++;
                    break;
                case 2:
                    // Viewer: ApprovalRole = 2 and ApprovalStatus = 7
                    signLineDTO.setApprovalStatus(7);
                    signLineDTO.setStep(0);
                    break;
            }
            signLineDTO.setMemo("");
            signLineDTO.setCreateId(empDTO.getEmpId());
            approvalSignLineService.insert(signLineDTO);
        }
    }

    public void insert(ApprovalDTO approvalDTO) {
        iCommonDao.insertData("Approval.insertOne", approvalDTO);
    }

    public void update(ApprovalDTO approvalDTO) {
        iCommonDao.updateData("Approval.updateOne", approvalDTO);
    }

    public void updateContent(ApprovalDTO approvalDTO){
        iCommonDao.updateData("Approval.updateContent", approvalDTO);
    }

    public void disable(ApprovalDTO approvalDTO) {
        iCommonDao.updateData("Approval.disable", approvalDTO);
    }

    public List<ApprovalDTO> selectRequestApproveByEmpCd(ApprovalSearchDTO searchDTO) {
        return iCommonDao.selectList("Approval.selectRequestApproveByEmpCd", searchDTO);
    }

    public Integer countRequestApproveByEmpCd(ApprovalSearchDTO searchDTO){
        return iCommonDao.selectCount("Approval.countRequestApproveByEmpCd", searchDTO);
    }
}
