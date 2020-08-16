package xyz.kwin.dailybonus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.kwin.dailybonus.controller.model.Result;
import xyz.kwin.dailybonus.model.CheckinDTO;
import xyz.kwin.dailybonus.model.DailyBonusSearchDTO;
import xyz.kwin.dailybonus.model.vo.CheckinRecord;
import xyz.kwin.dailybonus.model.vo.CheckinStatus;
import xyz.kwin.dailybonus.service.CheckinService;

/**
 * 签到服务 - 提供HTTP接口
 *
 * @author Kwin
 * @since 2020/8/16 22:12
 */
@RestController
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping("/getRecord")
    public Result getRecord(DailyBonusSearchDTO condition) {
        CheckinRecord checkinRecord = checkinService.getCheckinRecord(condition);
        return Result.success(checkinRecord);
    }

    @GetMapping("/checkin")
    public Result checkin(CheckinDTO checkin) {
        CheckinStatus checkinStatus = checkinService.checkin(checkin);
        return Result.success(checkinStatus);
    }

}
