package xyz.kwin.dailybonus.executor;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import xyz.kwin.dailybonus.mapper.CheckinMapper;

import java.util.concurrent.*;

/**
 * 持久化服务
 *
 * @author Kwin
 * @since 2020/8/16 20:45
 */
@Component
public class PersistenceExecutor {

    private final BlockingQueue<Runnable> errorQueue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(5000), new CustomizableThreadFactory("PERSIST-CHECKIN-"), new KeepingAndNotifyingHandler());

    private final CheckinMapper checkinMapper;

    public PersistenceExecutor(CheckinMapper checkinMapper) {
        this.checkinMapper = checkinMapper;
    }

    /**
     * 提交新的持久化任务
     *
     * @param appid  appid
     * @param userid 用户id
     * @param year   年
     * @param origin 二进制字符串原始数据
     */
    public void submitTask(Integer appid, String userid, Integer year, String origin) {
        PersistenceTask task = new PersistenceTask(appid, userid, year, origin);
        executor.submit(task);
    }

    /**
     * 异常处理
     *
     * @param appid  appid
     * @param userid 用户id
     * @param year   年
     * @param origin 二进制字符串原始数据
     */
    public static void handleException(Integer appid, String userid, Integer year, String origin) {
        // TODO: 2020/8/16 异常处理
    }

    /**
     * 处理异常队列
     *
     * @param type 0-清空 1-重试
     */
    public void handleErrorQueue(Integer type) {
        switch (type) {
            case 0:
                errorQueue.clear();
                break;
            case 1:
                for (Runnable errorTask : errorQueue) {
                    executor.submit(errorTask);
                }
        }
    }

    /**
     * 持久化任务
     */
    public class PersistenceTask implements Runnable {
        private final Integer appid;
        private final String userid;
        private final Integer year;
        private final String origin;

        public PersistenceTask(Integer appid, String userid, Integer year, String origin) {
            this.appid = appid;
            this.userid = userid;
            this.year = year;
            this.origin = origin;
        }

        @Override
        public void run() {
            try {
                checkinMapper.checkin(appid, userid, year, origin);
            } catch (Exception e) {
                errorQueue.offer(this);
                PersistenceExecutor.handleException(appid, userid, year, origin);
            }
        }
    }

    /**
     * 用户签到信息保存 + 预警异常处理
     */
    public class KeepingAndNotifyingHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // TODO: 2020/8/16 保存 + 预警

            errorQueue.offer(r);
        }
    }

}
