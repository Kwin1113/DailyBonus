package xyz.kwin.dailybonus.controller.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Kwin
 * @since 2020/8/16 22:13
 */
@Getter
@Setter
public class Result {
    private Integer code;
    private String message;
    private Object data;

    private Result() {
    }

    private Result(Object data) {
        this.data = data;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(0, "success");
    }

    public static Result success(Object data) {
        return new Result(0, "success", data);
    }

    public static Result error() {
        return new Result(-1, "error");
    }

    public static Result error(Object data) {
        return new Result(-1, "error", data);
    }

    public static Result result(Integer code, String message, Object data) {
        return new Result(code, message, data);
    }

}
