package kr.co.hs.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class DateUtil {
    /**
     * @param date
     * @param format
     * @return
     */
    public static String getFirstDayOfMonth(Date date, String format){

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String ymd = null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);

        return dateFormat.format(cal.getTime());
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String getToday(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date());
    }

    /**
     * @param date
     * @param delimeter
     * @param format
     * @return
     */
    public static String getYesterday(String date, String delimeter, String format){
        return getAddDay(date, delimeter, format,-1);
    }

    public static String getAddDay(String date, String delimeter, String format,int addDay){

        log.debug("date ==>"+date+", delimeter ==> "+delimeter+", format ==> "+format);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        Calendar cal = Calendar.getInstance();
        String[] arrDate = date.split(delimeter);
        cal.set(Calendar.YEAR, Integer.parseInt(arrDate[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(arrDate[1])-1);
        cal.set(Calendar.DATE, Integer.parseInt(arrDate[2]));

        cal.add(Calendar.DATE, addDay);

        return dateFormat.format(cal.getTime());
    }

    public static String getTomorrow(String date, String delimeter, String format){
        return getAddDay(date, delimeter, format,1);
    }

    public static List<String> getListDate(Date date, String format, int length){
        List<String> list = new ArrayList<String>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(int i=0; i<length; i++){
            list.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
        }

        return list;
    }

    public static List<String> getListMonth(Date date, String format, int length){
        List<String> list = new ArrayList<String>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for(int i=0; i<length; i++){
            list.add(dateFormat.format(cal.getTime()));
            cal.add(Calendar.MONTH , -1);
        }

        return list;
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String getStrDate(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date getStrToDate(String date, String format){
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getDiffDays(Date date1, Date date2){
        long diffDay = (date1.getTime() - date2.getTime()) / (24*60*60*1000);
        return (int)diffDay;
    }

    public static int getDiffMonths(Date date1, Date date2){
        int month1 = date1.getYear() * 12 + date1.getMonth();
        int month2 = date2.getYear() * 12 + date2.getMonth();
        return month1 - month2;
    }

    public static Date addMonth(int value, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, value);
        return cal.getTime();
    }

    /**
     * @format yyyy/MM/dd hh:mm:ss.ms
     * */
    public static String toString(String mFormat){
        Calendar time = Calendar.getInstance();
        String year = time.get(Calendar.YEAR) + "";
        String month = (time.get(Calendar.MONTH) < 9 ? "0" : "") + (time.get(Calendar.MONTH)+1);
        String day = (time.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + time.get(Calendar.DAY_OF_MONTH);
        String hour = (time.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + time.get(Calendar.HOUR_OF_DAY);
        String minutes = (time.get(Calendar.MINUTE) < 10 ? "0" : "") + time.get(Calendar.MINUTE);
        String seconds = (time.get(Calendar.SECOND) < 10 ? "0" : "") + time.get(Calendar.SECOND);
        String miliSecond = time.get(Calendar.MILLISECOND)+"";
        return mFormat.replaceAll("yyyy", year)
                .replaceAll("MM", month)
                .replaceAll("dd", day)
                .replaceAll("hh", hour)
                .replaceAll("mm", minutes)
                .replaceAll("ss", seconds)
                .replaceAll("ms", miliSecond);
    }
}
