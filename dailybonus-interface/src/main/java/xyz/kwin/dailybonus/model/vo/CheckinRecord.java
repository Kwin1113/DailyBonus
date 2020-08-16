package xyz.kwin.dailybonus.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import xyz.kwin.dailybonus.model.vo.period.PeriodRecord;

import java.time.LocalDateTime;

/**
 * 签到记录
 *
 * @author Kwin
 * @since 2020/8/16 16:17
 */
@Getter
@Setter
@Accessors(chain = true)
public class CheckinRecord {
    private String userid;
    private LocalDateTime lastCheckin;
    private PeriodRecord record;

    public String checkin() {
        lastCheckin = LocalDateTime.now();
        return record.checkin(lastCheckin.toLocalDate());
    }
}
