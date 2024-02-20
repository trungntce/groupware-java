package kr.co.hs.menu.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.menu.dto.MenuDTO;
import kr.co.hs.menu.dto.MenuSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    final ICommonDao iCommonDao;

    public List<MenuDTO> getMenu(MenuSearchDTO menuSearchDTO) {
        return iCommonDao.selectList("Menu.selectOneLevelMenu", menuSearchDTO);
    }
}
