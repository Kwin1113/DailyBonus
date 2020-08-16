package xyz.kwin.dailybonus.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 签到DTO
 *
 * @author Kwin
 * @since 2020/8/16 15:57
 */
@Getter
@Setter
public class CheckinDTO {
    private Integer appId;
    private String userid;
    private LocalDate date;
}
