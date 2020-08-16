package xyz.kwin.dailybonus.model.vo.period;

import lombok.Getter;
import lombok.Setter;
import xyz.kwin.dailybonus.exception.DateParameterException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 签到记录 - 年
 *
 * @author Kwin
 * @since 2020/8/16 16:19
 */
@Getter
@Setter
public class YearRecord extends PeriodRecord {
    private Integer year;
    private List<LocalDate> checkinDays;
    private String origin;

    public YearRecord(Integer year, String origin) {
        this.year = year;
        this.origin = origin;
    }

    @Override
    public boolean isChecked(LocalDate date) {
        checkDate(date);
        int dayOfYear = date.getDayOfYear();
        char status = origin.charAt(dayOfYear);
        return status == '1';
    }

    @Override
    public int getNumsInPeriod() {
        if (null != checkinDays) {
            return checkinDays.size();
        }

        countCheckinDays();
        return checkinDays.size();
    }

    @Override
    public Collection<LocalDate> getCheckinDaysInPeriod() {
        if (null != checkinDays) {
            return checkinDays;
        }

        countCheckinDays();
        return checkinDays;
    }

    @Override
    public int getContinuousCheckinWithinToday() {
        return getContinuousDays(LocalDate.now());
    }

    @Override
    public String checkin(LocalDate date) {
        LocalDate today = LocalDate.now();

        int dayOfYear = today.getDayOfYear();
        char[] yearChars = origin.toCharArray();
        yearChars[dayOfYear - 1] = '1';
        origin = Arrays.toString(yearChars);
        checkinDays.add(today);

        return origin;
    }

    @Override
    public int getContinuousCheckin() {
        LocalDate date = LocalDate.now().minusDays(1);
        checkDate(date);

        return getContinuousDays(date);
    }

    /**
     * 检查用户输入日期
     *
     * @param date 日期
     */
    private void checkDate(LocalDate date) {
        int year = date.getYear();
        if (this.year != year) {
            throw new DateParameterException(String.format("日期参数异常，查询年份必须为：%s，当前参数为：%s", this.year, year));
        }
    }

    /**
     * 解析二进制字符串原始数据
     */
    private void countCheckinDays() {
        checkinDays = new ArrayList<>();

        char[] yearChars = origin.toCharArray();
        LocalDate start = LocalDate.of(year, 1, 1);

        int i = 0;
        while (!start.isAfter(LocalDate.now())) {
            if (yearChars[i] == '1') {
                checkinDays.add(start);
            }
            start = start.plusDays(1);
            i++;
        }
    }

    /**
     * 获取连续签到天数
     *
     * @param date 计算起始日期
     * @return 连续签到天数
     */
    private int getContinuousDays(LocalDate date) {
        int dayOfYear = date.getDayOfYear();

        char[] yearChars = origin.toCharArray();
        if (yearChars[dayOfYear - 1] == '0') {
            return 0;
        }

        int count = 1;
        for (int i = dayOfYear - 2; i >= 0; i--) {
            char status = yearChars[i];
            if (status == '0') {
                break;
            }
            count++;
        }

        return count;
    }

}
