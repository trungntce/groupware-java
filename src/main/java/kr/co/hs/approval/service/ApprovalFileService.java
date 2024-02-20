package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.ApprovalFileDTO;
import kr.co.hs.approval.dto.ApprovalFileSearchDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.files.dto.FilesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalFileService {

    @Autowired
    private ICommonDao commonDao;

    public List<FilesDTO> selectFileByApprovalId(ApprovalFileSearchDTO approvalFileSearchDTO){
        return commonDao.selectList("ApprovalFile.selectFileByApprovalId", approvalFileSearchDTO);
    }

    public void insertOne(ApprovalFileDTO approvalFileDTO){
       commonDao.insertData("ApprovalFile.insertOne", approvalFileDTO);
    }

    public void deleteByApprovalIdAndCheckFileIds(ApprovalFileSearchDTO approvalFileSearchDTO){
        commonDao.deleteData("ApprovalFile.deleteByApprovalIdAndCheckFileIds", approvalFileSearchDTO);
    }

    public void deleteByApprovalId(ApprovalFileSearchDTO approvalFileSearchDTO){
        commonDao.deleteData("ApprovalFile.deleteByApprovalId", approvalFileSearchDTO);
    }
}
