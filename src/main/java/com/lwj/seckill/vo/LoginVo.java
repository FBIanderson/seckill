package com.lwj.seckill.vo;

import com.lwj.seckill.validator.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo{
    @NotNull
//    @IsMobile
    private String mobile;
    @NotNull
//    @Length(min=32)
    private String password;
}
