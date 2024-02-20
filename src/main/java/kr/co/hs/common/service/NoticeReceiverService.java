package kr.co.hs.common.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.NoticeReceiverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeReceiverService {

    @Autowired
    private ICommonDao commonDao;

    public void insertOne(NoticeReceiverDTO noticeReceiverDTO){
        commonDao.insertData("EventNoticeReceiver.insertOne", noticeReceiverDTO);
    }
}
