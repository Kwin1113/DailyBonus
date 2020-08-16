package xyz.kwin.dailybonus.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.kwin.dailybonus.model.entity.Checkin;

/**
 * 签到记录表
 *
 * @author Kwin
 * @since 2020/8/16 16:06
 */
@Repository
public interface CheckinMapper {

    /**
     * 查询对应应用用户签到状态
     *
     * @param appid  appid
     * @param userid 用户id
     * @return 签到记录表
     */
    Checkin getCheckinRecord(@Param("appid") Integer appid, @Param("userid") String userid, @Param("year") Integer year);

    /**
     * 用户签到
     *
     * @param appid  appid
     * @param userid 用户id
     * @param year   年
     * @param record 二进制字符串原始数据
     */
    void checkin(@Param("appid") Integer appid, @Param("userid") String userid, @Param("year") Integer year, @Param("record") String record);
}
