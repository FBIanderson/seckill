package com.lwj.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lwj.seckill.pojo.User;
import com.lwj.seckill.vo.LoginVo;
import com.lwj.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anderson
 * @since 2021-01-31
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据Cookie获取用户
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

    /**
     * 更新密码
      * @param userTicket
     * @param password
     * @param request
     * @param response
     * @return
     */
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);

}
