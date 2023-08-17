package com.lantu.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * lombok 的注解是基于javac 编译时动态添加的方式 添加的方法 ， 但 这中方式是被java所禁止的（非正规切入）
 *
 */

@Data    //生成get和set方法
@NoArgsConstructor    //构造
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    //成功/重载
    public static <T> Result<T> success() {
        return new Result<>(20000, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(20000, "success", data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(20000, message, null);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(20000, message, data);
    }

    //失败/重载
    public static <T> Result<T> fail() {
        return new Result<>(20001, "fail", null);
    }

    public static <T> Result<T> fail(Integer code) {
        return new Result<>(code, "fail", null);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(20001, message, null);
    }
}