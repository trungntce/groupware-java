package kr.co.hs.approval.service;

import kr.co.hs.approval.dto.ApprovalSettingsDTO;
import kr.co.hs.approval.dto.ApprovalSettingsSearchDTO;
import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.file.FileService;
import kr.co.hs.common.util.DateUtil;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalSettingService {

    @Autowired
    private FileService fileService;

    @Autowired
    private EmpService empService;

    private final ICommonDao commonDao;

    public void createSignatureSource(EmpDTO empDTO, String base64Str){
        String[] str = base64Str.split(",");
        byte[] data = DatatypeConverter.parseBase64Binary(str[1]);
        String subFolder = String.format("yyyy%sMM%sdd", File.separator, File.separator);
        subFolder = DateUtil.toString(subFolder);
        String baseFolder = fileService.preparePath(subFolder);
        String nameFile = DateUtil.toString("yyyyMMddhhmmssms.png");
        System.out.println("Path: "+String.format("%s%s%s", baseFolder, File.separator, nameFile));
        File file = new File(String.format("%s%s%s", baseFolder, File.separator, nameFile));
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApprovalSettingsDTO approvalSettingsDTO = new ApprovalSettingsDTO();
        approvalSettingsDTO.setCreateId(empDTO.getEmpId());
        approvalSettingsDTO.setSignatureImagePath(String.format("%s%s%s%s%s", FileService.uploadPath, File.separator, subFolder, File.separator, nameFile));
        updateDisableForUser(approvalSettingsDTO);
        insert(approvalSettingsDTO);
    }

    public List<ApprovalSettingsDTO> selectList(ApprovalSettingsSearchDTO searchDTO){
        return commonDao.selectList("ApprovalSettings.selectList", searchDTO);
    }

    public ApprovalSettingsDTO selectOneByUserEnable(ApprovalSettingsSearchDTO searchDTO){
        List<ApprovalSettingsDTO> list = selectList(searchDTO);
        if(list.size() > 0){
            return list.get(0);
        }else{
            return new ApprovalSettingsDTO();
        }
    }

    public void insert(ApprovalSettingsDTO approvalSettingsDTO){
        commonDao.insertData("ApprovalSettings.insertOne", approvalSettingsDTO);
    }

    public void delete(ApprovalSettingsDTO approvalSettingsDTO){
        commonDao.deleteData("ApprovalSettings.deleteOne", approvalSettingsDTO);
    }

    public void enableSignSettings(HttpServletRequest request, ApprovalSettingsDTO approvalSettingsDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalSettingsDTO.setCreateId(empDTO.getEmpId());
        updateDisableForUser(approvalSettingsDTO);
        updateEnable(approvalSettingsDTO);
    }

    public void removeSignSettings(HttpServletRequest request, ApprovalSettingsDTO approvalSettingsDTO){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        approvalSettingsDTO.setCreateId(empDTO.getEmpId());
        delete(approvalSettingsDTO);
    }

    public void updateDisableForUser(ApprovalSettingsDTO approvalSettingsDTO){
        commonDao.updateData("ApprovalSettings.updateDisableForUser", approvalSettingsDTO);
    }

    public void updateEnable(ApprovalSettingsDTO approvalSettingsDTO){
        commonDao.updateData("ApprovalSettings.updateEnable", approvalSettingsDTO);
    }
}
