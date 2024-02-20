package kr.co.hs.lang.service;


import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.lang.dto.LangDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LangService {
    final ICommonDao iCommonDao;
    public List<LangDTO> getLangList() {
        return iCommonDao.selectAll("Lang.selectListLang");
    }

}
