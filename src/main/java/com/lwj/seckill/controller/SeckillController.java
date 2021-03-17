package com.lwj.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwj.seckill.pojo.Order;
import com.lwj.seckill.pojo.SeckillMessage;
import com.lwj.seckill.pojo.SeckillOrder;
import com.lwj.seckill.pojo.User;
import com.lwj.seckill.rabbitmq.MQSender;
import com.lwj.seckill.service.IGoodsService;
import com.lwj.seckill.service.IOrderService;
import com.lwj.seckill.service.ISeckillOrderService;
import com.lwj.seckill.utils.JsonUtil;
import com.lwj.seckill.vo.GoodsVo;
import com.lwj.seckill.vo.RespBeanEnum;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.graalvm.util.CollectionsUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MQSender mqSender;
    private Map<Long, Boolean> emptyStockMap = new HashMap<>();
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
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder)redisTemplate.opsForValue().get("order:"+user.getId()+":"+goodsId);
        if(seckillOrder!=null){
            return "orderDetail";
        }
        //预减库存
        Long stock = valueOperations.decrement("seckillGoods:"+goodsId);
        //通过内存标记，减少Redis的访问
        if(emptyStockMap.get(goodsId)){
            return "orderDetail";
        }
        if(stock<0){
            emptyStockMap.put(goodsId,true);
            valueOperations.increment("seckillGoods:"+goodsId);
            return "orderDetail";
        }
        SeckillMessage seckillMessage = new SeckillMessage(user,goodsId);
        mqSender.sendSeckillMessage(JsonUtil.object2JsonStr(seckillMessage));


//        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        //判断库存
//        if(goodsVo.getStockCount()<1){
//            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
//            return "secKillFail";
//        }
//        //判断是否重复抢购
//        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().
//                eq("user_id", user.getId()).
//                eq("goods_id", goodsId));
//        if(seckillOrder!=null){
//            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR);
//            return "secKillFail";
//        }
//        Order order = orderService.seckill(user,goodsVo);
//        model.addAttribute("order",order);
//        model.addAttribute("goods",goodsVo);
        return "orderDetail";
    }

    /**
     * 系统初始化，把商品库存数量加载到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = goodsService.findGoodsVo();
        if(list==null ||list.size()==0 ){
           return;
        }
        list.forEach(goodsVo ->{
                     redisTemplate.opsForValue().set("seckillGoods:"+goodsVo.getId(),goodsVo.getStockCount());
                     emptyStockMap.put(goodsVo.getId(),false);
                }
        );

    }
}
