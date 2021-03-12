package com.lwj.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务器异常"),
    //登陆模块5002XX
    LOGIN_ERROR(500210,"用户名或密码不正确"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常"),
    MOBILE_NOT_EXIST(500213,"手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214,"更新密码错误"),
    SESSION_ERROR(500215,"session错误"),
    //秒杀模块5005XX
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品每人限购一件"),
    //订单模块5003XX
    ORDER_NOT_EXIST(500300,"订单信息不存在"),
    ;

    private final Integer code;
    private final String message;
}
