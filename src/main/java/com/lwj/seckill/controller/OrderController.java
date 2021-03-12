package com.lwj.seckill.controller;


import com.lwj.seckill.pojo.User;
import com.lwj.seckill.service.IOrderService;
import com.lwj.seckill.vo.OrderDetailVo;
import com.lwj.seckill.vo.RespBean;
import com.lwj.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Anderson
 * @since 2021-02-16
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    /**
     * 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public RespBean detail(User user,Long orderId){
       if(user==null){
           return RespBean.error(RespBeanEnum.SESSION_ERROR);
       }
       OrderDetailVo detail = orderService.detail(orderId);
       return RespBean.success(detail);
    }

}
