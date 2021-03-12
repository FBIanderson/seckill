package com.lwj.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwj.seckill.pojo.Order;
import com.lwj.seckill.pojo.SeckillOrder;
import com.lwj.seckill.pojo.User;
import com.lwj.seckill.service.IGoodsService;
import com.lwj.seckill.service.IOrderService;
import com.lwj.seckill.service.ISeckillOrderService;
import com.lwj.seckill.vo.GoodsVo;
import com.lwj.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    /**
     * Windows 优化前 QPS 1036
     * @param model
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    public String deSeckill(Model model, User user, @Param("goodsId")Long goodsId){
        if(user==null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if(goodsVo.getStockCount()<1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().
                eq("user_id", user.getId()).
                eq("goods_id", goodsId));
        if(seckillOrder!=null){
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR);
            return "secKillFail";
        }
        Order order = orderService.seckill(user,goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "orderDetail";
    }
}
