package com.lwj.seckill.controller;


import com.lwj.seckill.pojo.User;
import com.lwj.seckill.rabbitmq.MQSender;
import com.lwj.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MQSender mqSender;


    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

    /**
     * 测试rabbitmq
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq(){
       mqSender.send("hello lwj");
    }
}
