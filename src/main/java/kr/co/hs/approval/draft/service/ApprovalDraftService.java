package kr.co.hs.approval.draft.service;

import kr.co.hs.approval.dto.*;
import kr.co.hs.approval.service.ApprovalFileService;
import kr.co.hs.approval.service.ApprovalService;
import kr.co.hs.approval.service.ApprovalSignLineService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ApprovalDraftService {

    @Autowired
    private EmpService empService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ApprovalSignLineService approvalSignLineService;

    @Autowired
    private ApprovalFileService approvalFileService;

    public ApprovalSearchDTO initParam(HttpServletRequest request, ApprovalSearchDTO searchDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        searchDTO.setEmpCd(empDTO.getEmpCd());
        searchDTO.setApprovalStatus(6);
        searchDTO.setMyApproval("Y");
        return searchDTO;
    }

    public boolean update(HttpServletRequest request, ApprovalDTO approvalDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        //Delete sign sequence
        //Delete file
        //Add sign sequence
        //Add file
        approvalSignLineService.deleteByApprovalId(new ApprovalSignLineSearchDTO(approvalDTO.getApprovalId()));
        approvalFileService.deleteByApprovalId(new ApprovalFileSearchDTO(approvalDTO.getApprovalId()));

        approvalService.updateContent(approvalDTO);

        int lineFirst = 1;
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

        //Attach file
        if(approvalDTO.getIdFiles().size() > 0){
            ApprovalFileDTO approvalFileDTO = new ApprovalFileDTO();
            approvalFileDTO.setApprovalId(approvalDTO.getApprovalId());
            for(int i = 0; i < approvalDTO.getIdFiles().size(); i++){
                approvalFileDTO.setFileId(approvalDTO.getIdFiles().get(i));
                approvalFileService.insertOne(approvalFileDTO);
            }
        }
        return true;
    }
}
