package com.lwj.seckill.controller;


import com.lwj.seckill.pojo.User;
import com.lwj.seckill.vo.RespBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anderson
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
    @RequestMapping("info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }
}
