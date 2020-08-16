package xyz.kwin.dailybonus.model.redis;

import lombok.Getter;
import lombok.Setter;
import xyz.kwin.dailybonus.model.CheckinDTO;
import xyz.kwin.dailybonus.model.DailyBonusSearchDTO;

/**
 * redis缓存签到记录
 * key: dailybonus:checkin:${APPID}:${USERID}:${YEAR}
 * data structure: hash
 * field: timestamp / origin / last_checkin
 * ttl: 3 days
 *
 * @author Kwin
 * @since 2020/8/16 20:38
 */
@Getter
@Setter
public class CheckinRecordInRedis {
    private Long timestamp;
    private String origin;

    private static final String KEY_FORM = "dailybonus:checkin:%s:%s:%s";
    public static final String TIMESTAMP = "timestamp";
    public static final String ORIGIN = "origin";
    public static final String LAST_CHECKIN = "last_checkin";

    public static String getKey(DailyBonusSearchDTO condition) {
        return String.format(KEY_FORM, condition.getAppid(), condition.getUserid(), condition.getYear());
    }

    public static String getKey(CheckinDTO checkin) {
        return String.format(KEY_FORM, checkin.getAppId(), checkin.getUserid(), checkin.getDate().getYear());
    }
}
