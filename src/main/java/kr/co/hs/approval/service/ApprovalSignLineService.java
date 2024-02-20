package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.ApprovalDTO;
import kr.co.hs.approval.dto.ApprovalSearchDTO;
import kr.co.hs.approval.dto.ApprovalSignLineDTO;
import kr.co.hs.approval.dto.ApprovalSignLineSearchDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.service.NoticeService;
import kr.co.hs.emp.dto.EmpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalSignLineService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private NoticeService noticeService;

    private final ICommonDao commonDao;

    public List<ApprovalSignLineDTO> selectList(ApprovalSignLineSearchDTO searchDTO) {
        return commonDao.selectList("ApprovalSignLine.selectList", searchDTO);
    }

    public ApprovalSignLineDTO selectOne(int signLineId) {
        return commonDao.selectOne("ApprovalSignLine.selectOne", signLineId);
    }

    public void insert(ApprovalSignLineDTO approvalSignLineDTO) {
        commonDao.insertData("ApprovalSignLine.insertOne", approvalSignLineDTO);
    }

    public void deleteByApprovalId(ApprovalSignLineSearchDTO approvalSignLineSearchDTO){
        commonDao.deleteData("ApprovalSignLine.deleteByApprovalId", approvalSignLineSearchDTO);
    }

    public void updateOne(ApprovalSignLineDTO approvalSignLineDTO) {
        commonDao.updateData("ApprovalSignLine.updateOne", approvalSignLineDTO);

    }

    public void sign(ApprovalSignLineDTO approvalSignLineDTO) {
        commonDao.updateData("ApprovalSignLine.sign", approvalSignLineDTO);
    }

    public void updateApprovalStatus(EmpDTO empDTO, ApprovalSignLineDTO approvalSignLineDTO) {

        ApprovalSignLineDTO signLine = selectOne(approvalSignLineDTO.getSignLineId());
        approvalSignLineDTO.setApprovalType(signLine.getApprovalType());

        ApprovalSearchDTO searchDTO = new ApprovalSearchDTO();

        searchDTO.setApprovalId(signLine.getApprovalId());
        ApprovalDTO approvalDTO = commonDao.selectOne("Approval.selectOne", searchDTO);

        signLine.setApprovalStatus(approvalSignLineDTO.getApprovalStatus());
        signLine.setMemo(approvalSignLineDTO.getMemo());

        switch (approvalSignLineDTO.getApprovalStatus()) {

            case 3:

                ApprovalSignLineDTO targetSign = new ApprovalSignLineDTO();
                ApprovalSignLineDTO targetSignSupporter = new ApprovalSignLineDTO();
                //Check step approval
                ApprovalSignLineSearchDTO signSearch = new ApprovalSignLineSearchDTO();
                signSearch.setApprovalId(approvalSignLineDTO.getApprovalId());
                List<Integer> lstRole = new ArrayList<>();
                lstRole.add(1);
                lstRole.add(3);

                signSearch.setLstApprovalRole(lstRole);
                signSearch.setOrderByColumn("step");
                signSearch.setOrderByType("desc");
                List<ApprovalSignLineDTO> lst = selectList(signSearch);
                lst.forEach((e) -> {
                    if (e.getStep() >= signLine.getStep()
                            && !e.getSignLineId().equals(signLine.getSignLineId())
                            && e.getApprovalStatus() == 2) {
                        targetSign.setApprovalId(e.getApprovalId());
                        targetSign.setApprovalType(signLine.getApprovalType());
                        targetSign.setEmpId(e.getEmpId());
                        targetSign.setEmpCd(e.getEmpCd());
                        targetSign.setApprovalStatus(1);
                    }
                });

                // When target is null then Finish approval
                if (targetSign.getApprovalId() == null) {
                    approvalDTO.setApprovalStatus(5);
                    noticeService.register(new NoticeDTO(99, approvalDTO.getApprovalId(), Collections.singletonList(approvalDTO.getEmpCd()), "Đơn phê duyệt của bạn được thông qua: "+approvalDTO.getTitle(), "APPROVAL_FINISH"));
                } else {
                    approvalDTO.setApprovalStatus(2);
                    commonDao.updateData("ApprovalSignLine.sign", targetSign);
                    noticeService.register(new NoticeDTO(99, approvalDTO.getApprovalId(), Collections.singletonList(targetSign.getEmpCd()), "Xác nhận đơn phê duyệt: "+approvalDTO.getTitle(), "APPROVAL_CONFIRM"));
                }
                break;
            case 4:
                // When cancel status = 4
                // Update Approval status = 4
                approvalDTO.setApprovalStatus(4);
                noticeService.register(new NoticeDTO(99, approvalDTO.getApprovalId(), Collections.singletonList(approvalDTO.getEmpCd()), "Đơn phê duyệt của bạn bị từ chối: "+approvalDTO.getTitle(), "APPROVAL_CANCEL"));
                break;
        }
        commonDao.updateData("Approval.updateOne", approvalDTO);
        commonDao.updateData("ApprovalSignLine.sign", signLine);

    }

    public void updateViewerSeenApproval(ApprovalSignLineSearchDTO searchDTO) {
        try {

            List<ApprovalSignLineDTO> viewDTO = this.selectList(searchDTO);
            if (viewDTO.size() > 0 && viewDTO.get(0).getApprovalStatus() == 7) {
                // Update Seen
                viewDTO.get(0).setApprovalStatus(8);
                this.sign(viewDTO.get(0));
            }
        } catch (Exception ex) {

        }
    }

}
