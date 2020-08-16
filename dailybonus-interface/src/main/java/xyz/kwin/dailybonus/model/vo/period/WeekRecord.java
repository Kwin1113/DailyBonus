package xyz.kwin.dailybonus.model.vo.period;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

/**
 * 签到记录 - 周
 * @author Kwin
 * @since 2020/8/16 16:20
 */
@Getter
@Setter
public class WeekRecord extends PeriodRecord {
    private Integer year;
    private Integer week;
    private String origin;
    private List<LocalDate> checkinDays;

    public WeekRecord(Integer year, Integer week, String origin) {
        this.year = year;
        this.week = week;
        this.origin = origin;
    }

    @Override
    protected boolean isChecked(LocalDate date) {
        return false;
    }

    @Override
    protected int getNumsInPeriod() {
        return 0;
    }

    @Override
    protected Collection<LocalDate> getCheckinDaysInPeriod() {
        return null;
    }

    @Override
    protected int getContinuousCheckin() {
        return 0;
    }

    @Override
    protected int getContinuousCheckinWithinToday() {
        return 0;
    }

    @Override
    public String checkin(LocalDate date) {
        return null;
    }
}
