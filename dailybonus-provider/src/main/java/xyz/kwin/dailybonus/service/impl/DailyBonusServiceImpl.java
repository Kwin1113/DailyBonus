package xyz.kwin.dailybonus.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.kwin.dailybonus.model.CheckinDTO;
import xyz.kwin.dailybonus.model.DailyBonusSearchDTO;
import xyz.kwin.dailybonus.model.vo.CheckinRecord;
import xyz.kwin.dailybonus.model.vo.CheckinStatus;
import xyz.kwin.dailybonus.service.CheckinService;
import xyz.kwin.dailybonus.service.DailyBonusService;

/**
 * 每日签到Dubbo暴露服务
 *
 * @author Kwin
 * @since 2020/8/16 15:59
 */
@Service
@Component
public class DailyBonusServiceImpl implements DailyBonusService {

    private final CheckinService checkinService;

    public DailyBonusServiceImpl(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @Override
    public CheckinRecord getCheckinRecord(DailyBonusSearchDTO condition) {
        return checkinService.getCheckinRecord(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckinStatus checkin(CheckinDTO checkin) {
        return checkinService.checkin(checkin);
    }

}
