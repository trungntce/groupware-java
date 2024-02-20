package kr.co.hs.main.service;


import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.main.dto.NoteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MainsService {
    final ICommonDao iCommonDao;

    public List<NoteDTO> selectNote(int empCd) {
        return iCommonDao.selectList("main.selectNote", empCd);
    }

    public int xoaNote(int id) {
        return iCommonDao.deleteData("main.xoaNote", id);
    }

    public int addNote(NoteDTO noteDTO) {
        return iCommonDao.insertDataInt("main.addNote",noteDTO);
    }


}
