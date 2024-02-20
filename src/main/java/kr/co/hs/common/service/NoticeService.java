package kr.co.hs.common.service;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.NoticeDTO;
import kr.co.hs.common.dto.NoticeReceiverDTO;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    @Autowired
    private ICommonDao iCommonDao;

    @Autowired
    private CommonSearch commonSearch;

    @Autowired
    private NoticeReceiverService noticeReceiverService;

    @Autowired
    private EmpService empService;

    public List<NoticeDTO> bellNotice(HashMap param, String adminYn, String translationYn){
        List<NoticeDTO> lst;
        lst = iCommonDao.selectList("EventNotice.selectBellNotice", param);
        return lst;
    }

    public List<NoticeDTO> allNotice(HashMap param, String adminYn, String translationYn){
        List<NoticeDTO> lst;
        lst =  iCommonDao.selectList("EventNotice.selectAllNotice", param);
        return lst;
    }

    public int getSumRowNoticeAll(HashMap param, String adminYn, String translationYn){
        int sum = iCommonDao.selectOne("EventNotice.selectCountAllNotice",param);
        return sum;
    }

    public int sumNotice(HashMap param, String adminYn, String translationYn){
        int sum = iCommonDao.selectCount("EventNotice.sumNotice", param);
        return sum;
    }

    public void insertOne(NoticeDTO noticeDTO){
        iCommonDao.insertData("EventNotice.insertOne", noticeDTO);
    }

    public String replaceUptoLow(String orderColumn){
        String str="";
        for(int i = 0; i < orderColumn.length(); i++)
        {
            if( Character.isUpperCase(orderColumn.charAt(i))){

                str+="_"+orderColumn.charAt(i);
                str=str.toLowerCase();

            }else{
                str+=orderColumn.charAt(i);
            }
        }
        return str;
    }
    public int getCountParam(HashMap param){
        return iCommonDao.selectCount("EventNotice.selectCountNotice", param);
    }

    public void readNotificationReceiver(HashMap para){
        iCommonDao.updateData("EventNotice.readNotificationReceiver", para);
    }

    public void readPageNotificationReceiver(HashMap param) {
        iCommonDao.updateData("EventNotice.readPageNotificationReceiver", param);
    }

    public void readAllNotificationReceiver(String empCd){
        iCommonDao.updateData("EventNotice.readAllNotificationReceiver", empCd);
    }

    public void register(NoticeDTO noticeDTO) {
        EmpDTO emp = empService.getEmpByEmpCd(noticeDTO.getEmpCd());
        noticeDTO.setPositionCd(emp.getPositionCd());
        noticeDTO.setDeptCd(emp.getDeptCd());
        noticeDTO.setDeptChangeHistoryId(emp.getDeptChangeHistoryId());
        this.insertOne(noticeDTO);
        if(noticeDTO.getToEmpCd().size() > 0){
            for(Integer empCd : noticeDTO.getToEmpCd()){
                noticeReceiverService.insertOne(new NoticeReceiverDTO(noticeDTO.getEventNoticeId(), empCd));
            }
        }
    }
}

