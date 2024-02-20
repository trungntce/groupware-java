package kr.co.hs.attendace.service;

import kr.co.hs.attendace.dto.AttendanceDTO;
import kr.co.hs.attendace.dto.AttendanceSumDTO;
import kr.co.hs.common.dao.ICommonDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendaceService {
    final ICommonDao iCommonDao;

    public List<AttendanceDTO> selectListAtt(HashMap param) {
        return iCommonDao.selectList("Attendance.selectListAtt",param);
    }
    public int getSumRowAtt(HashMap param){
        return iCommonDao.selectOne("Attendance.getSumRowAtt",param);
    }
    public int SumrowSummaryAttAdmin(HashMap param){
        return iCommonDao.selectOne("Attendance.SumrowSummaryAttAdmin",param);
    }

    public List<AttendanceSumDTO> summaryAttAdmin(HashMap param) {
        return iCommonDao.selectList("Attendance.summaryAttAdmin",param);
    }

    public List<AttendanceSumDTO> summaryAttUser(HashMap param) {
        return iCommonDao.selectList("Attendance.summaryAttUser",param);
    }

    public int checkExist(int empCd) {
        return iCommonDao.selectOne("Attendance.checkExist",empCd);
    }
    public int checkExistCheckIn(int empCd) {
        return iCommonDao.selectOne("Attendance.checkExistCheckIn",empCd);
    }

    public int updateCheckin(HashMap param) {
        return iCommonDao.updateData("Attendance.updateCheckin",param);
    }
    public int updateCheckOut(HashMap param) {
        return iCommonDao.updateData("Attendance.updateCheckOut",param);
    }

    public int insertAttendance(AttendanceDTO attendanceDTO) {
        return iCommonDao.insertDataInt("Attendance.insertAttendance",attendanceDTO);
    }

    public List<AttendanceDTO> getMyAttendanceList(HashMap map){return iCommonDao.selectList("Attendance.selectMyAttendanceList",map);}

    public List<AttendanceDTO> loadWorkingDay(HashMap param) {
        return iCommonDao.selectList("Attendance.loadWorkingDay",param);
    }
    public List<AttendanceDTO> loadEarlyDay(HashMap param) {
        return iCommonDao.selectList("Attendance.loadEarlyDay",param);
    }
    public List<AttendanceDTO> loadOntimeDay(HashMap param) {
        return iCommonDao.selectList("Attendance.loadOntimeDay",param);
    }
    public List<AttendanceDTO> loadLateDay(HashMap param) {
        return iCommonDao.selectList("Attendance.loadLateDay",param);
    }
    public List<AttendanceDTO> loadLeaveEarly(HashMap param) {
        return iCommonDao.selectList("Attendance.loadLeaveEarly",param);
    }

}
