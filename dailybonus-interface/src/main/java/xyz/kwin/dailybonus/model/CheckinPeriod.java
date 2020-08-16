package xyz.kwin.dailybonus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
