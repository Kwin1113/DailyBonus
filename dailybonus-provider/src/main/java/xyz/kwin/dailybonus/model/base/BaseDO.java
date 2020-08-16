package xyz.kwin.dailybonus.model.base;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 基础DO
 * 所有DO都应继承该抽象类
 *
 * @author Kwin
 * @since 2020/8/16 16:08
 */
@Getter
@Setter
public abstract class BaseDO {
    protected Integer id;
    protected LocalDateTime ctime;
    protected LocalDateTime mtime;
    protected Boolean isvalid;
}
