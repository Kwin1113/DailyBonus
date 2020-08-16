package xyz.kwin.dailybonus.service;

import xyz.kwin.dailybonus.model.CheckinDTO;
import xyz.kwin.dailybonus.model.DailyBonusSearchDTO;
import xyz.kwin.dailybonus.model.vo.CheckinRecord;
import xyz.kwin.dailybonus.model.vo.CheckinStatus;

/**
 * 签到服务接口
 *
 * @author Kwin
 * @since 2020/8/16 20:43
 */
public interface CheckinService {

    /**
     * 查询对应应用的用户签到记录
     *
     * @param condition 签到记录查询条件
     * @return 签到记录
     */
    CheckinRecord getCheckinRecord(DailyBonusSearchDTO condition);

    /**
     * 用户签到
     *
     * @param checkin 签到对象
     * @return 签到状态
     */
    CheckinStatus checkin(CheckinDTO checkin);

}
