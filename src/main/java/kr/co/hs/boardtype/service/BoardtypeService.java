package kr.co.hs.boardtype.service;


import kr.co.hs.boardtype.dto.BoardTypeDTO;
import kr.co.hs.common.dao.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardtypeService {

    final ICommonDao iCommonDao;

    public List<BoardTypeDTO> getBoardTypeList() {
        return iCommonDao.selectAll("Boardtype.selectListBoardype");
    }

}
