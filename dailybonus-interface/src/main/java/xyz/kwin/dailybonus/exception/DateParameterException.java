package xyz.kwin.dailybonus.exception;

/**
 * 时间参数异常
 *
 * @author Kwin
 * @since 2020/8/16 16:35
 */
public class DateParameterException extends RuntimeException {
    private Integer code = -1;

    public DateParameterException(String message) {
        super(message);
    }

    public DateParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateParameterException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public DateParameterException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
