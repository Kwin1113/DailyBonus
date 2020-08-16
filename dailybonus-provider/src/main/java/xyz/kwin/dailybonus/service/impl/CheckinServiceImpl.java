package xyz.kwin.dailybonus.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kwin.dailybonus.executor.PersistenceExecutor;
import xyz.kwin.dailybonus.mapper.CheckinMapper;
import xyz.kwin.dailybonus.model.CheckinDTO;
import xyz.kwin.dailybonus.model.DailyBonusSearchDTO;
import xyz.kwin.dailybonus.model.entity.Checkin;
import xyz.kwin.dailybonus.model.redis.CheckinRecordInRedis;
import xyz.kwin.dailybonus.model.vo.CheckinRecord;
import xyz.kwin.dailybonus.model.vo.CheckinStatus;
import xyz.kwin.dailybonus.model.vo.period.MonthRecord;
import xyz.kwin.dailybonus.model.vo.period.PeriodRecord;
import xyz.kwin.dailybonus.model.vo.period.WeekRecord;
import xyz.kwin.dailybonus.model.vo.period.YearRecord;
import xyz.kwin.dailybonus.service.CheckinService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 签到服务
 *
 * @author Kwin
 * @since 2020/8/16 20:46
 */
@Service
public class CheckinServiceImpl implements CheckinService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CheckinMapper checkinMapper;
    private final PersistenceExecutor persistenceExecutor;

    public CheckinServiceImpl(RedisTemplate<String, Object> redisTemplate, CheckinMapper checkinMapper, PersistenceExecutor persistenceExecutor) {
        this.redisTemplate = redisTemplate;
        this.checkinMapper = checkinMapper;
        this.persistenceExecutor = persistenceExecutor;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CheckinRecord getCheckinRecord(DailyBonusSearchDTO condition) {
        String key = CheckinRecordInRedis.getKey(condition);
        Long timestamp = (Long) redisTemplate.opsForHash().get(key, CheckinRecordInRedis.TIMESTAMP);
        if (null != timestamp) {
            long intervalMills = System.currentTimeMillis() - timestamp;
            // 如果当前用户签到状态为一天内，则直接返回
            if (intervalMills < TimeUnit.DAYS.toMillis(1)) {
                String origin = (String) redisTemplate.opsForHash().get(key, CheckinRecordInRedis.ORIGIN);
                LocalDateTime lastCheckin = (LocalDateTime) redisTemplate.opsForHash().get(key, CheckinRecordInRedis.LAST_CHECKIN);
                if (null != origin && null != lastCheckin) {
                    return createCheckinRecord(condition, lastCheckin, origin);
                }
            }
        }

        // 否则查询数据库更新redis
        // 查询用户签到状态
        Checkin userCheckin = checkinMapper.getCheckinRecord(condition.getAppid(), condition.getUserid(), condition.getYear());
        CheckinRecord checkinRecord = createCheckinRecord(condition, userCheckin.getLastCheckin(), userCheckin.getRecord());

        // 更新redis缓存
        Map<String, Object> checkinRecordInRedis = new HashMap<>();
        checkinRecordInRedis.put(CheckinRecordInRedis.ORIGIN, userCheckin.getRecord());
        checkinRecordInRedis.put(CheckinRecordInRedis.LAST_CHECKIN, userCheckin.getLastCheckin());
        checkinRecordInRedis.put(CheckinRecordInRedis.TIMESTAMP, System.currentTimeMillis());
        redisTemplate.opsForHash().putAll(key, checkinRecordInRedis);

        return checkinRecord;
    }

    @Override
    public CheckinStatus checkin(CheckinDTO checkin) {
        String key = CheckinRecordInRedis.getKey(checkin);
        LocalDate today = LocalDate.now();

        // 获取当前签到状态
        DailyBonusSearchDTO condition = new DailyBonusSearchDTO()
                .setAppid(checkin.getAppId())
                .setUserid(checkin.getUserid());
        condition.ofYear(today.getYear());
        CheckinRecord checkinRecord = this.getCheckinRecord(condition);
        // 当天已签到
        if (checkinRecord.getLastCheckin().toLocalDate().isEqual(today)) {
            return CheckinStatus.alreadyCheckin(checkinRecord);
        }

        // 修改签到状态
        String origin = checkinRecord.checkin();
        Map<String, Object> checkinRecordInRedis = new HashMap<>();
        checkinRecordInRedis.put(CheckinRecordInRedis.ORIGIN, origin);
        checkinRecordInRedis.put(CheckinRecordInRedis.LAST_CHECKIN, today);
        checkinRecordInRedis.put(CheckinRecordInRedis.TIMESTAMP, System.currentTimeMillis());
        redisTemplate.opsForHash().putAll(key, checkinRecordInRedis);

        // 提交持久化任务
        persistenceExecutor.submitTask(checkin.getAppId(), checkin.getUserid(), today.getYear(), origin);

        return CheckinStatus.checkinSuccess(checkinRecord);
    }

    /**
     * 创建返回对象
     *
     * @param condition   查询条件
     * @param lastCheckin 最近签到时间（查询结果）
     * @param origin      原始记录（查询结果）
     * @return 返回结果
     */
    private CheckinRecord createCheckinRecord(DailyBonusSearchDTO condition, LocalDateTime lastCheckin, String origin) {
        CheckinRecord checkinRecord = new CheckinRecord();
        checkinRecord.setUserid(condition.getUserid())
                .setLastCheckin(lastCheckin);

        // 创建用户记录信息
        PeriodRecord record = null;
        DailyBonusSearchDTO.CheckinPeriod period = condition.getPeriod();
        switch (period) {
            case YEAR:
                record = new YearRecord(condition.getYear(), origin);
                break;
            case MONTH:
                record = new MonthRecord(condition.getYear(), condition.getMonth(), origin);
                break;
            case WEEK:
                record = new WeekRecord(condition.getYear(), condition.getWeek(), origin);
                break;
            default:
                break;
        }
        checkinRecord.setRecord(record);

        return checkinRecord;
    }
}
