package com.lwj.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwj.seckill.pojo.Goods;
import com.lwj.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anderson
 * @since 2021-02-16
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
