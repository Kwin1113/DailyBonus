package xyz.kwin.dailybonus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Kwin
 * @since 2020/8/16 15:56
 */
@Getter
@Setter
@Accessors(chain = true)
public class DailyBonusSearchDTO {
    private Integer appid;
    private String userid;
    private Integer year;
    private Integer month;
    private Integer week;
    private CheckinPeriod period;

    public void ofYear(Integer year) {
        this.setYear(year).setPeriod(CheckinPeriod.YEAR);
    }

    public void ofMonth(Integer year, Integer month) {
        this.setYear(year).setMonth(month).setPeriod(CheckinPeriod.MONTH);
    }

    public void ofWeek(Integer year, Integer week) {
        this.setYear(year).setWeek(week).setPeriod(CheckinPeriod.WEEK);
    }

    /**
     * 签到范围
     *
     * @author Kwin
     * @since 2020/8/16 16:10
     */
    @Getter
    @AllArgsConstructor
    public enum CheckinPeriod {
        YEAR,
        MONTH,
        WEEK;
    }
}
