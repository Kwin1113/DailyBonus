package xyz.kwin.dailybonus.model.vo.period;

import java.time.LocalDate;
import java.util.Collection;

/**
 * 查询返回记录抽象类
 *
 * @author Kwin
 * @since 2020/8/16 16:18
 */
public abstract class PeriodRecord {

    /**
     * 获取签到状态原始数据
     *
     * @return 二进制字符串原始数据
     */
    protected abstract Object getOrigin();

    /**
     * 查询用户指定日期是否已签到
     *
     * @param date 日期
     * @return 是否已签到
     */
    protected abstract boolean isChecked(LocalDate date);

    /**
     * 统计用户在统计区间内总签到次数/天数
     *
     * @return 总签到次数
     */
    protected abstract int getNumsInPeriod();

    /**
     * 获取用户在统计区间内签到的日期
     *
     * @return 统计区间内签到的日期
     */
    protected abstract Collection<LocalDate> getCheckinDaysInPeriod();

    /**
     * 获取用户当前连续签到次数（不包括今天）
     *
     * @return 当前连续签到次数
     */
    protected abstract int getContinuousCheckin();

    /**
     * 获取用户当前连续签到次数（包括今天）
     *
     * @return 当前连续签到次数
     */
    protected abstract int getContinuousCheckinWithinToday();

    /**
     * 当天签到
     *
     * @return 二进制字符串原始数据
     */
    public abstract String checkin(LocalDate date);

}
