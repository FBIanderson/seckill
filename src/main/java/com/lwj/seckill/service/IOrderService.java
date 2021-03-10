package com.lwj.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwj.seckill.pojo.Order;
import com.lwj.seckill.pojo.User;
import com.lwj.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anderson
 * @since 2021-02-16
 */
public interface IOrderService extends IService<Order> {
    Order seckill(User user, GoodsVo goodsVo);
}
