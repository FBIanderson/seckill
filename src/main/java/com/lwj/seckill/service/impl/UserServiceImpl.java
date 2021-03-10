package com.lwj.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwj.seckill.exception.GlobalException;
import com.lwj.seckill.mapper.UserMapper;
import com.lwj.seckill.pojo.User;
import com.lwj.seckill.service.IUserService;
import com.lwj.seckill.utils.CookieUtil;
import com.lwj.seckill.utils.MD5Util;
import com.lwj.seckill.utils.UUIDUtil;
import com.lwj.seckill.vo.LoginVo;
import com.lwj.seckill.vo.RespBean;
import com.lwj.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anderson
 * @since 2021-01-31
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        log.info("doLogin");
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        log.info("mobile"+mobile);
        log.info("password"+password);
        //此处应该为前端加密，暂时放到这里
        password = MD5Util.inputPassToFromPass(password);
        log.info("after password :"+password);
        User user =null;
        try{
            user = userMapper.selectById(mobile);
        }catch(Exception e){
            log.debug("user exception");
            e.printStackTrace();
        }
        log.info("user password : "+user.getPassword());
        if(user ==null){
            log.info("user==null");
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        log.info("final password:"+MD5Util.fromPassToDBPass(password,user.getSalt()));
        if(!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            log.info("MD5Util");
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //将用户信息存到redis中
        redisTemplate.opsForValue().set("user:"+ticket,user);
//        request.getSession().setAttribute(ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        log.info("before return success");
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        log.info("getUserByCookie"+user);
        if(user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    /**
     * 更新密码
     * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     */
    @Override
    public RespBean updatePassword(String userTicket, String password,
                                   HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if(user == null){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int result = userMapper.updateById(user);
        if(1==result){
            //删除Redis
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }


}
