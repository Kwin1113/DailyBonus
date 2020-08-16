package xyz.kwin.dailybonus.model.entity;

import lombok.Getter;
import lombok.Setter;
import xyz.kwin.dailybonus.model.base.BaseDO;

import java.time.LocalDateTime;

/**
 * 签到记录表实体类
 *
 * @author Kwin
 * @since 2020/8/16 16:08
 */
@Getter
@Setter
public class Checkin extends BaseDO {
    private Integer appId;
    private String userid;
    private String period;
    private String record;
    private LocalDateTime lastCheckin;
}
