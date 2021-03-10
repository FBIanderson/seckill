package com.lwj.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwj.seckill.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anderson
 * @since 2021-01-31
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
