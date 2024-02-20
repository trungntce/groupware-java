package kr.co.hs.code.service;

import kr.co.hs.code.dto.CodeDTO;
import kr.co.hs.common.dao.ICommonDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    final ICommonDao iCommonDao;
    public List<CodeDTO> getCodeList(String type, String langCd) {
        CodeDTO codeDTO = new CodeDTO();
        codeDTO.setCCode(type);
        codeDTO.setLangCd(langCd);
        return iCommonDao.selectList("Code.selectCodeList", codeDTO);
    }
}