package kr.co.hs.work.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.work.dto.WorkDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyWorkService {
    final ICommonDao iCommonDao;

    public List<WorkDTO> getlistMyWork(HashMap param) {
        return iCommonDao.selectList("songmywork.getlistMyWork", param);
    }
    public int getSumRowMyWork(HashMap param){
        return iCommonDao.selectOne("songmywork.getSumRowMyWork",param);
    }

    public List<WorkDTO> getlistMyWorkMain(HashMap param) {
        return iCommonDao.selectList("songmywork.getlistMyWorkMain", param);
    }

}
