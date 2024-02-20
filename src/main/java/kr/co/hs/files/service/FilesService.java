package kr.co.hs.files.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.FileDTO;
import kr.co.hs.common.file.FileService;
import kr.co.hs.common.file.IFileUpload;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import kr.co.hs.files.dto.FilesDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class FilesService {

    @Autowired
    private ICommonDao commonDao;

    @Autowired
    private EmpService empService;

    @Autowired
    private FileService fileService;

    public void insertOne(FilesDTO filesDTO){
        commonDao.insertData("Files.insertOne", filesDTO);
    }

    public FilesDTO upload(HttpServletRequest request, MultipartFile file){
        EmpDTO empDTO = empService.getSessionUserLogin(request);
        FilesDTO filesDTO = new FilesDTO(empDTO);

        fileService.uploadFile(file, new IFileUpload() {
            @Override
            public void processUpload(MultipartFile file, String realName, String path) {
                filesDTO.setFileType("file");
                filesDTO.setFilePath(path);
                filesDTO.setFileName(file.getOriginalFilename());
                filesDTO.setFileHashName(realName);
                filesDTO.setFileExt(FilenameUtils.getExtension(realName));
                filesDTO.setFileSize(file.getSize()+"");
                insertOne(filesDTO);
            }
        });

        return filesDTO;
    }
}
