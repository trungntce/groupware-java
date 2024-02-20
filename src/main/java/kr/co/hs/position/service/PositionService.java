package kr.co.hs.position.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.menu.dto.MenuDTO;
import kr.co.hs.position.dto.PositionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    final ICommonDao iCommonDao;

    public List<PositionDTO> getPositionList(String langCd) {

        return iCommonDao.selectList("Position.selectPositionList", langCd);
    }
    public int DeletePo(HashMap map) {
        return iCommonDao.updateData("Position.deletePo",map);
    }

    public int InsertPo( PositionDTO positionDTO) {
        return iCommonDao.insertDataInt("Position.InsertPo",positionDTO);
    }

    public int updatePo( PositionDTO positionDTO) {
        return iCommonDao.updateData("Position.updatePo",positionDTO);
    }

    public PositionDTO getlistbyID(HashMap map) {
        return iCommonDao.selectOne("Position.getlistByid", map);
    }
}
