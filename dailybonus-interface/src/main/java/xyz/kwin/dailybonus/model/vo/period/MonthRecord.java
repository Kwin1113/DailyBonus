package xyz.kwin.dailybonus.model.vo.period;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * 签到记录 - 月
 * @author Kwin
 * @since 2020/8/16 16:20
 */
@Getter
@Setter
public class MonthRecord extends PeriodRecord {
    private Integer year;
    private Integer month;
    private String origin;
    private List<LocalDate> checkinDays;

    public MonthRecord(Integer year, Integer month, String origin) {
        this.year = year;
        this.month = month;
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
