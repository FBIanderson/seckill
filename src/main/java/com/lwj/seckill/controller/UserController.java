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

//    /**
//     * 测试rabbitmq
//     */
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq(){
//       mqSender.send("hello lwj");
//    }
//
//    /**
//     * Fanout模式
//     */
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public void mq01(){
//        mqSender.send("hello lwj");
//    }
//
//    /**
//     * Direct模式
//     */
//    @RequestMapping("/mq/direct01")
//    @ResponseBody
//    public void mq02(){
//        mqSender.sendDirect01("hello direct");
//    }
//    @RequestMapping("/mq/direct02")
//    @ResponseBody
//    public void mq03(){
//        mqSender.sendDirect02("hello direct");
//    }
//
//    /**
//     * Topic模式
//     */
//    @RequestMapping("/mq/topic01")
//    @ResponseBody
//    public void mq04(){
//        mqSender.sendTopic01("hello topic");
//    }
//    @RequestMapping("/mq/topic02")
//    @ResponseBody
//    public void mq05(){
//        mqSender.sendTopic02("hello topic");
//    }
//
//    /**
//     * Headers模式
//     */
//    @RequestMapping("/mq/header01")
//    @ResponseBody
//    public void mq06(){
//        mqSender.sendHeaders01("hello header01");
//    }
//    @RequestMapping("/mq/header02")
//    @ResponseBody
//    public void mq07(){
//        mqSender.sendHeaders02("hello header02");
//    }
}
